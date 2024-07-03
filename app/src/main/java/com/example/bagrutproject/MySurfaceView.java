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
    private boolean firstTime = true;
    List<Map> levels = new ArrayList<Map>();
    List<Shuriken> shurikens = new ArrayList<Shuriken>();

    int score = 0;
    Paint p;
    private int joyStickPointerId = 0;

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        this.holder = getHolder();

        p  = new Paint();
        p.setColor(Color.GREEN);
        p.setTextSize(100);


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

                        player.draw(c);

                        movementJoyStick.draw(c);

                        levels.get(0).drawMap(c);

                        c.drawText(""+score,100,100,p);
                        for (Shuriken shuriken: shurikens){
                            shuriken.draw(c);
                        }

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
        updateShurikenlList();
        checkForIntersects();

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
                    }
                }
            }
        }


    }

    private void checkForIntersects() {
        for (Enemy enemy: levels.get(0).getEnemies()){
            if(player.doesIntersects(enemy)){
                levels.get(0).getEnemies().remove(enemy);
//                score++;
                player.setCurrentHp(player.getCurrentHp()-1);
            }
        }
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

    public boolean isPointInside(double pointX, double pointY) {
        // Calculate the distance between the point and the circle's center
        double distance = Math.sqrt(Math.pow(pointX - movementJoyStick.getOuterCircleCenterPositionX(),
                2) + Math.pow(pointY - movementJoyStick.getOuterCircleCenterPositionY(), 2));

        // Check the position based on distance and radius
        return distance < movementJoyStick.getOuterCircleRadius(); // Inside the circle
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (movementJoyStick.getIsPressed()){
                    //joystick is pressed and another action comes in,
                    // meaning a shuriken is meant to be thrown

                    if (!isPointInside(event.getX(),event.getY())){
                        shurikens.add(new Shuriken(player,event.getX(),event.getY()));
                    }

                }
                else if (movementJoyStick.isPressed((double) event.getX(), (double) event.getY())) {
                    //the joystick is being pressed
                    joyStickPointerId = event.getPointerId(event.getActionIndex());
                    movementJoyStick.setIsPressed(true);
                }else {
                    //joystick is not pressed and hasn't been pressed
                    //meaning a shuriken is meant to be thrown
                    if (!isPointInside(event.getX(),event.getY())){
                        shurikens.add(new Shuriken(player,event.getX(),event.getY()));
                    }
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
}
