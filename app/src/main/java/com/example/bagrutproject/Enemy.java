package com.example.bagrutproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy extends Entity{

    Context context;
    boolean isAllowedToMove = false;
    Player player;

    final double MAX_SPEED = 5*0.6;

    float vx;
    float vy;

    public Enemy(float cordX, float cordY, Bitmap bitmap,Context context,Player player) {
        super(cordX, cordY, bitmap);
        setBitmap(resizeBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy),60,60));
        this.context = context;
        this.player = player;

    }

    public void update(){

        if (true) {
            updateVectors();
            setPosX(getPosX() - vx/400);
            setPosY(getPosY() - vy/400);
        }
        updateCollisions();
    }

//    public void update(){
//        updateVectors();
//        if (isAllowedToMove) {
//            //vector from enemy(this) to player
//
//
//            //distance between both
//            double distanceToPlayer = getDistanceBetweenObjects(this,player);
//
//            //direction for X & Y
//            double directionX = vx/distanceToPlayer;
//            double directionY = vy/distanceToPlayer;
//
//            //velocity which the enemy moves in
//            if (distanceToPlayer > 0 ){//the enemy isn't near the player and avoids dividing by 0
//                velocityX = directionX*MAX_SPEED;
//                velocityY = directionY*MAX_SPEED;
//            }else{
//                velocityX = 0;
//                velocityY = 0;
//            }
//
//            setPosX(getPosX() +(float) velocityX);
//            setPosX(getPosY() +(float) velocityY);
//
//        }
//        updateCollisions();
//    }

    private void updateVectors() {
        vx = getPosX() - player.getPosX();
        vy = getPosY() - player.getPosY();
    }


}
