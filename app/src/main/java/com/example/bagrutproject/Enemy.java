package com.example.bagrutproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy extends Entity{

    boolean isAllowedToMove = false;

    final double MAX_SPEED = 5*0.6;

    float vx;
    float vy;

    public Enemy(float cordX, float cordY, Bitmap bitmap) {
        super(cordX, cordY, bitmap);
        setBitmap(resizeBitmap(bitmap,60,60));

    }

    public void update(Player player){

        if (true) {
            updateVectors(player);
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

    private void updateVectors(Player player) {
        vx = getPosX() - player.getPosX();
        vy = getPosY() - player.getPosY();
    }


}
