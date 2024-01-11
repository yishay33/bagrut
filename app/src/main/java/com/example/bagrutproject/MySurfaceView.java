package com.example.bagrutproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Runnable{

    Context context;
    SurfaceHolder holder;
    boolean threadRunning = true;
    boolean isRunning = true;
    private final Player player;
    private final JoyStick movementJoyStick;

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        this.holder = getHolder();
        player = new Player(this.getWidth()/2,this.getHeight()/2,BitmapFactory.decodeResource(getResources(), R.drawable.okaygebusiness),"yishay");
        movementJoyStick = new JoyStick(250,800,100,65);
    }

    @Override
    public void run() {
        while (threadRunning) {
            if (isRunning) {

                if (!holder.getSurface().isValid())
                    continue;
                Canvas c = null;

                try {
                    c = this.getHolder().lockCanvas();
                    synchronized (this.getHolder()) {
                        c.drawRGB(8, 85, 201);
                        player.draw(c);
                        movementJoyStick.draw(c);
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
        player.update(movementJoyStick);
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
