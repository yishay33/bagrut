package com.example.bagrutproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class Player extends Entity{


    private static final double MAX_SPEED = 5;
    String playerName;
    Context context;
    private float futureX;
    private float futureY;
    final int MAX_HP = 5;
    private int currentHp = MAX_HP;
    private HealthBar healthBar;



    public Player(float cordX, float cordY, Bitmap bitmap, String playerName, Context context) {
        super(cordX, cordY, bitmap);
        this.playerName = playerName;
        setBitmap(resizeBitmap(bitmap,40,60));
        this.context = context;

        this.healthBar = new HealthBar(this);
    }


    public void setCords(float x,float y){
        setPosX(x);
        setPosY(y);
    }

    public void update(JoyStick joyStick,List<Wall> walls){
        velocityX = joyStick.getActuatorX()*MAX_SPEED;
        velocityY = joyStick.getActuatorY()*MAX_SPEED;
        futureX = getPosX()+(float) velocityX;
        futureY = getPosY()+(float) velocityY;


        Rect futurePlayer = new Rect();
        futurePlayer.set((int) futureX,(int) futureY,(int) futureX+this.getBitmap().getWidth(),(int)futureY + this.getBitmap().getHeight());
        Rect futurePlayerX = new Rect();
        futurePlayerX.set((int) futureX,(int) getPosY(),(int) futureX+this.getBitmap().getWidth(),(int)getPosY() + this.getBitmap().getHeight());
        Rect futurePlayerY = new Rect();
        futurePlayerY.set((int) getPosX(),(int) futureY,(int) getPosX()+this.getBitmap().getWidth(),(int)futureY + this.getBitmap().getHeight());

        boolean intersectsWall = false;
        Wall tempWall = null;


        for (Wall wall : walls) {
               //check for X axis
             if (wall.getCollisions().intersect(futurePlayerX) && !wall.getCollisions().intersect(futurePlayerY)) {
                   super.setPosY(getPosY() + (float) velocityY);
                   intersectsWall  = true;
                   tempWall = wall;
                 Log.d("case 3","move y");
             }
             //check for Y axis
             else if (!wall.getCollisions().intersect(futurePlayerX) && wall.getCollisions().intersect(futurePlayerY)) {
                 super.setPosX(getPosX() + (float) velocityX);
                 intersectsWall = true;
                 tempWall = wall;
                 Log.d("case 3","move x");
             }
             //can run without conditions
            else if (wall.getCollisions().intersect(futurePlayer)){
                intersectsWall = true;
                tempWall = wall;
             }
        }

        if (!intersectsWall){
            move();
        }
//        if (tempWall != null)
//            tempWall.update();
        for (Wall wall: walls){
            wall.update();
        }
    }

    public void move() {
        super.setPosX(getPosX() + (float) velocityX);
        super.setPosY(getPosY() + (float) velocityY);
        Log.d("case 3","move");
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        healthBar.draw(canvas);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCurrentHp(int hp){
        if (currentHp > 0) {
            currentHp = hp;
        }
    }

    public int getCurrentHp(){
        return currentHp;
    }

}
