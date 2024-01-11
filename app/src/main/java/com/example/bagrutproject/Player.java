package com.example.bagrutproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends Entity{

    private static final double MAX_SPEED = 5;
    String playerName;
    Paint p = new Paint();

    private double velocityX;
    private double velocityY;

    public Player(float cordX, float cordY, Bitmap bitmap,String playerName) {
        super(cordX, cordY, bitmap);
        this.playerName = playerName;
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        p.setColor(Color.green(1));
        c.drawText(playerName, getPosX()+200, getPosY()-100,p);
    }

    public void setCords(float x,float y){
        setPosX(x);
        setPosY(y);
    }

    public void update(JoyStick joyStick){
        velocityX = joyStick.getActuatorX()*MAX_SPEED;
        velocityY = joyStick.getActuatorY()*MAX_SPEED;
        super.setPosX(getPosX()+(float) velocityX);
        super.setPosY(getPosY()+(float) velocityY);
    }
}
