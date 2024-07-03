package com.example.bagrutproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Shuriken {


    private Player player;
    float posX;
    float posY;
    float targetX, targetY;
    Bitmap bitmap;
    private Rect collisions;
    float dx,dy;
    double normalizedDx;
    double normalizedDy;

    public Shuriken(Player player,float targetX,float targetY) {
        this.player = player;
        this.posX = player.getPosX();
        this.posY = player.getPosY();
        this.targetX = targetX;
        this.targetY = targetY;

        bitmap = resizeBitmap(BitmapFactory.decodeResource(player.getContext().getResources(),R.drawable.shuriken),20,20);

        collisions = new Rect((int) posX,(int) posY,(int) posX+this.bitmap.getWidth(),(int)posY + this.bitmap.getHeight());

        float dx = targetX - this.posX;
        float dy = targetY - this.posY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        normalizedDx = dx / distance;
        normalizedDy = dy / distance;
    }


    public void draw(Canvas c){
        c.drawBitmap(this.bitmap,this.posX,this.posY,null);
        updateCollisions();
    }
    public void updateCollisions() {
        collisions.set((int) posX,(int) posY,(int) posX+this.bitmap.getWidth(),(int)posY + this.bitmap.getHeight());
    }

    public void update() {
        this.posX += 5 * normalizedDx;
        this.posY += 5 * normalizedDy;

        updateCollisions();
    }



    public float getPosX() {
        return posX;
    }
    public void setPosX(float posX) {
        this.posX = posX;
    }
    public float getPosY() {
        return posY;
    }
    public void setPosY(float posY) {
        this.posY = posY;
    }
    public float getTargetX() {
        return targetX;
    }
    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public Rect getCollisions() {
        return collisions;
    }

    public void setCollisions(Rect collisions) {
        this.collisions = collisions;
    }

    public int convertDpToPixels(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public Bitmap resizeBitmap(Bitmap bitmap,int toWidth,int toHeight){
        return Bitmap.createScaledBitmap(bitmap, convertDpToPixels(toWidth),convertDpToPixels(toHeight),false);
    }
}
