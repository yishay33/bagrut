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
    private final List<Enemy> enemyList = new ArrayList<Enemy>();
    Wall wall;
    private Canvas c;
    boolean isPressing = true;
    Paint p;
    Paint wallPaint;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean firstTime = true;

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        this.holder = getHolder();
        p = new Paint();
        p.setAlpha(255/2);
        wallPaint = new Paint();
        wallPaint.setColor(Color.RED);
        wall = new Wall(200,100,200,100,wallPaint);
        player = new Player(this.getWidth()/2,this.getHeight()/2,
                BitmapFactory.decodeResource(getResources()
                        , R.drawable.okaygebusiness),"yishay",getContext());
        movementJoyStick = new JoyStick(250,800,100,65);


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
                        c.drawRGB(8, 85, 201);
                        if (firstTime) {
                            player.setCords((this.getWidth() / 2)-player.getBitmap().getWidth(), (this.getHeight() / 2)-player.getBitmap().getWidth());
                            firstTime = false;
                        }

                        player.draw(c, null);

                        movementJoyStick.draw(c);
                        wall.draw(c);

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
        player.update(movementJoyStick,wall);

        wall = new Wall(200,100,800,100,wallPaint);
        updateEnemyList();

        checkForIntersects();

    }

    private void checkForIntersects() {
        for (Enemy enemy: enemyList){
            if(player.doesIntersects(enemy)){
                enemyList.remove(enemy);
            }
        }
    }

    private void updateEnemyList() {
        for (Enemy enemy: enemyList){
            enemy.draw(c,p);
            enemy.update();
        }
        if (enemyList.isEmpty()){
            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.enemy),getContext(),player));
            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.enemy),getContext(),player));
            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.enemy),getContext(),player));
            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.enemy),getContext(),player));
            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.enemy),getContext(),player));
            enemyList.add(new Enemy(randomX(),randomY(),BitmapFactory.decodeResource(getResources(),R.drawable.enemy),getContext(),player));
        }
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
