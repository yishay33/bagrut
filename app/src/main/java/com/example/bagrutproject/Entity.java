package com.example.bagrutproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Entity {

    private float posX;
    private float posY;
    private Bitmap bitmap;
    private Rect collisions;

    public Entity(float cordX, float cordY, Bitmap bitmap) {
        this.posX = cordX;
        this.posY = cordY;
        this.bitmap = bitmap;

        collisions = new Rect(0,0,this.bitmap.getWidth(),this.bitmap.getHeight());
    }

    public boolean doesIntersects(Entity entity2){
        return collisions.intersect(entity2.collisions);
    }

    public void draw(Canvas c){
        c.drawBitmap(this.bitmap,this.posX,this.posY,null);
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

}
