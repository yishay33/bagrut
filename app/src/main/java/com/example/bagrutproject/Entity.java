package com.example.bagrutproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Entity {

    private float posX;
    private float posY;
    private Bitmap bitmap;
    private Rect collisions;

    protected double velocityX;
    protected double velocityY;

    public Entity(float cordX, float cordY, Bitmap bitmap) {
        this.posX = cordX;
        this.posY = cordY;
        this.bitmap = bitmap;

        collisions = new Rect((int) cordX,(int) cordY,(int) cordX+this.bitmap.getWidth(),(int)cordY + this.bitmap.getHeight());
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean doesIntersects(Entity entity2){
        return collisions.intersect(entity2.getCollisions());
    }

    public void draw(Canvas c, Paint p){
        c.drawBitmap(this.bitmap,this.posX,this.posY,p);
        updateCollisions();
    }

    public void updateCollisions() {
        collisions.set((int) posX,(int) posY,(int) posX+this.bitmap.getWidth(),(int)posY + this.bitmap.getHeight());
    }

    public double getDistanceBetweenObjects(Entity one,Entity two){
        return Math.sqrt(
                Math.pow(two.getPosX()- one.getPosX(),2) +
                Math.pow(two.getPosY() - one.getPosY(),2)
        );
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
    public Rect getCollisions() {
        return collisions;
    }

    public int convertDpToPixels(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public Bitmap resizeBitmap(Bitmap bitmap,int toWidth,int toHeight){
        return Bitmap.createScaledBitmap(bitmap, convertDpToPixels(toWidth),convertDpToPixels(toHeight),false);
    }
}
