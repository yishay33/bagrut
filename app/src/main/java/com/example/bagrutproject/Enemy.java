package com.example.bagrutproject;

import android.graphics.Bitmap;

public class Enemy extends Entity{

    boolean isAllowedToMove = false;

    final double MAX_SPEED = 5*0.2;

    float vx;
    float vy;

    public Enemy(float cordX, float cordY, Bitmap bitmap) {
        super(cordX, cordY, bitmap);
        setBitmap(resizeBitmap(bitmap,30,30));

    }

//    public void update(Player player){
//
//        if (true) {
//            updateVectors(player);
//            setPosX(getPosX() - vx/400);
//            setPosY(getPosY() - vy/400);
//        }
//        updateCollisions();
//    }

    public void update(Player player){
// Calculate the difference in x and y between enemy and player
        float dx = player.getPosX() - this.getPosX();
        float dy = player.getPosY() - this.getPosY();

        // Calculate the normalized direction vector (avoiding division by zero)
        double distance = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = (distance == 0) ? 0 : dx / distance;
        double normalizedDy = (distance == 0) ? 0 : dy / distance;

        // Move the enemy towards the player by the speed
        setPosX((float) (getPosX() +MAX_SPEED * normalizedDx)) ;
        setPosY((float) (getPosY() +MAX_SPEED * normalizedDy)) ;


        updateCollisions();
    }

    private void updateVectors(Player player) {
        vx = getPosX() - player.getPosX();
        vy = getPosY() - player.getPosY();
    }


}
