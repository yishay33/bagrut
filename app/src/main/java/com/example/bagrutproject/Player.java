package com.example.bagrutproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.Toast;

public class Player extends Entity{

    private static final double MAX_SPEED = 5;
    String playerName;

    Context context;


    public Player(float cordX, float cordY, Bitmap bitmap, String playerName, Context context) {
        super(cordX, cordY, bitmap);
        this.playerName = playerName;
        setBitmap(resizeBitmap(bitmap,60,60));
        this.context = context;
    }


    @Override
    public void draw(Canvas c,Paint p) {
        super.draw(c,p);
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
