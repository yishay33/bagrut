package com.example.bagrutproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Wall{

    private int width;
    private int height;
    private int posX;
    private int posY;
    private Paint p;
    private Rect collisions;



    public Wall(int posX, int posY,int width, int height,Paint p) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        this.p = p;

        collisions = new Rect(this.posX,this.posY,this.posX+width,this.posY+height);
    }

    public void update(){
        collisions.set(this.posX,this.posY,this.posX+width,this.posY+height);
    }


    public void draw(Canvas c){
        c.drawRect(collisions,p);
    }

    public Rect getCollisions() {
        return collisions;
    }

    public void setCollisions(Rect collisions) {
        this.collisions = collisions;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setCords(int i, int i1) {
        posX = i;
        posY = i1;
    }

}