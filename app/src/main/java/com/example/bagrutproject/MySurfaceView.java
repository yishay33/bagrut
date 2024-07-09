package com.example.bagrutproject;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements Runnable{

    private GameActivity gameActivity;
    Context context;
    SurfaceHolder holder;
    boolean threadRunning = true;
    boolean isRunning = true;
    private final Player player;
    private final JoyStick movementJoyStick;
    private Canvas c;
    private boolean firstTime = true;
    List<Map> levels = new ArrayList<Map>();
    List<Shuriken> shurikens = new ArrayList<Shuriken>();
    Bitmap background;
    SharedPreferences highestScore;
    int score = 0;
    Paint p;
    private int joyStickPointerId = 0;
    MediaPlayer mediaPlayer;

    public MySurfaceView(Context context,GameActivity gameActivity) {
        super(context);
        this.context = context;
        this.holder = getHolder();

        this.gameActivity = gameActivity;

        p  = new Paint();
        p.setColor(Color.GREEN);
        p.setTextSize(100);

        player = new Player(this.getWidth()/2,this.getHeight()/2,
                BitmapFactory.decodeResource(getResources()
                        , R.drawable.glayer),"yishay",getContext());

        movementJoyStick = new JoyStick(250,800,100,65);

        levels.add(new Level_1(this,context));
        mediaPlayer = MediaPlayer.create(getContext(),R.raw.balls);
        setMediaPlayer();
    }

    @Override
    public void run() {
        while (threadRunning) {
            if (isRunning) {

                if (!holder.getSurface().isValid())
                    continue;
                c = null;

                try {
                    c = this.getHolder().lockCanvas();
                    synchronized (this.getHolder()) {
                        if (firstTime) {

                            player.setCords((this.getWidth() / 2)-player.getBitmap().getWidth(), (this.getHeight() / 2)-player.getBitmap().getWidth());

                            levels.get(0).createWalls();
                            levels.get(0).createEnemies();

                            background = resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.background),this.getWidth(),this.getHeight());
                            firstTime = false;
                        }

                        c.drawBitmap(background,0,0,null);

                        player.draw(c);

                        movementJoyStick.draw(c);

                        levels.get(0).drawMap(c);

                        c.drawText(""+score,100,100,p);
                        for (Shuriken shuriken: shurikens){
                            shuriken.draw(c);
                        }
                        levels.get(0).drawEnemies(c);
                        update();


                    }

                } catch (Exception e) {
                } finally {
                    if (c != null) {
                        this.getHolder().unlockCanvasAndPost(c);

                    }

                }
            }
        }
    }

    public void update(){
        movementJoyStick.update();
        levels.get(0).updateWalls();
        player.update(movementJoyStick, levels.get(0).getWalls());
        updateShurikenlList();
        levels.get(0).updateEnemies(player);
        checkForIntersects();

    }
    public void setMediaPlayer(){
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getContext(),R.raw.balls);
        }
        mediaPlayer.start();
    }

    public void updateShurikenlList(){
        for (Shuriken shuriken : shurikens){
            shuriken.update();

            if (shuriken.posX > this.getWidth() ||shuriken.posX < 0 || shuriken.posY > this.getHeight() ||shuriken.posY < 0 ){
                shurikens.remove(shuriken);
            }else {
                for (Enemy enemy : levels.get(0).getEnemies()) {
                    if (shuriken.getCollisions().intersect(enemy.getCollisions())){
                        shurikens.remove(shuriken);
                        levels.get(0).getEnemies().remove(enemy);
                        score++;
                        highestScore = context.getSharedPreferences("highest_score",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = highestScore.edit();
                        if (score > highestScore.getInt("score", 0)) {
                            loadSharedPreferences();
                        }
                        else
                            editor.apply();
                    }
                }
            }
        }


    }

    public void loadSharedPreferences() {
        highestScore = context.getSharedPreferences("highest_score",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = highestScore.edit();
        editor.putInt("score",score);
        editor.apply();
    }

    private void checkForIntersects() {
        for (Enemy enemy: levels.get(0).getEnemies()){
            if(player.doesIntersects(enemy)){
                levels.get(0).getEnemies().remove(enemy);
                player.setCurrentHp(player.getCurrentHp()-1);
                if (player.getCurrentHp() <= 0){
                    Log.d("balls","first test");
//                  gameActivity.openDialog();
                    destroy();
                }
            }
            
        }
    }



    public void pause(){
        isRunning = false;
        mediaPlayer.pause();
    }

    public void resume(){
        isRunning = true;
        mediaPlayer.start();
    }

    public void destroy(){
        isRunning = false;
        stopPlaying();
        ((GameActivity)context).finish();
    }


    public int convertDpToPixels(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public Bitmap resizeBitmap(Bitmap bitmap,int toWidth,int toHeight){
        return Bitmap.createScaledBitmap(bitmap, convertDpToPixels(toWidth),convertDpToPixels(toHeight),false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (movementJoyStick.getIsPressed()){
                    //joystick is pressed and another action comes in,
                    // meaning a shuriken is meant to be thrown
                    shurikens.add(new Shuriken(player,event.getX(),event.getY()));
                }
                else if (movementJoyStick.isPressed((double) event.getX(), (double) event.getY())) {
                    //the joystick is being pressed
                    joyStickPointerId = event.getPointerId(event.getActionIndex());
                    movementJoyStick.setIsPressed(true);
                }else {
                    //joystick is not pressed and hasn't been pressed
                    //meaning a shuriken is meant to be thrown
                    shurikens.add(new Shuriken(player, event.getX(),event.getY()));

                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (movementJoyStick.getIsPressed()){
                    //joystick was pressed before and is now being dragged
                    movementJoyStick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joyStickPointerId == event.getPointerId(event.getActionIndex())){
                    //joystick was let go of resetting the joystick and setting is pressed to false
                    movementJoyStick.setIsPressed(false);
                    movementJoyStick.resetActuator();

                }
                return true;
        }

        return true;
    }

    public void stopPlaying() {
        mediaPlayer.stop();
    }
}
