package com.example.bagrutproject;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MySurfaceView extends SurfaceView implements Runnable{

    Context context;
    SurfaceHolder holder;
    boolean threadRunning = true;
    boolean isRunning = true;
    private final Player player;
    private final JoyStick movementJoyStick;
    private Canvas c;
    boolean isPressing = true;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean firstTime = true;
    List<Map> levels = new ArrayList<Map>();


    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        this.holder = getHolder();

        player = new Player(this.getWidth()/2,this.getHeight()/2,
                BitmapFactory.decodeResource(getResources()
                        , R.drawable.glayer),"yishay",getContext());

        movementJoyStick = new JoyStick(250,800,100,65);

        levels.add(new Level_1(0, 0, 255,this,context));
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
                        c.drawRGB(levels.get(0).getR(), levels.get(0).getG(), levels.get(0).getB());
                        if (firstTime) {


                            player.setCords((this.getWidth() / 2)-player.getBitmap().getWidth(), (this.getHeight() / 2)-player.getBitmap().getWidth());

                            levels.get(0).createWalls();
                            levels.get(0).createEnemies();

                            firstTime = false;

                        }

                        player.draw(c, null);

                        movementJoyStick.draw(c);

                        levels.get(0).drawMap(c);

                        update();
                        levels.get(0).drawEnemies(c);

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
        levels.get(0).updateEnemies(player);

        checkForIntersects();
//        updateEnemyList();

    }

    private void checkForIntersects() {
        for (Enemy enemy: levels.get(0).getEnemies()){
            if(player.doesIntersects(enemy)){
                levels.get(0).getEnemies().remove(enemy);
            }
        }
    }

    private void updateEnemyList() {
//        for (Enemy enemy: enemyList){
//            enemy.draw(c,null);
//            enemy.update(player);
//        }
//        if (enemyList.isEmpty()){
//            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.ghost3)));
//            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.ghost3)));
//            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.ghost3)));
//            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.ghost3)));
//            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.ghost3)));
//            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.ghost3)));
//        }
    }

    private float randomY() {
        float result;
        Random random = new Random();
        result = random.nextInt(this.getHeight())+1;
        return result;
    }

    private float randomX() {
        float result;
        Random random = new Random();
        result = random.nextInt(this.getWidth())+1;
        return result;
    }

    public void pause(){
        isRunning = false;
    }

    public void resume(){
        isRunning = true;
    }

    public void destroy(){
        isRunning = false;
        ((GameActivity)context).finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        enemy.isAllowedToMove = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (movementJoyStick.isPressed((double) event.getX(), (double) event.getY())) {
                    movementJoyStick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (movementJoyStick.getIsPressed()){
                    movementJoyStick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                movementJoyStick.setIsPressed(false);
                movementJoyStick.resetActuator();
                return true;
        }

        return true;
    }
}
