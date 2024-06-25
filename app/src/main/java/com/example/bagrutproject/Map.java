package com.example.bagrutproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private List<Enemy> enemies;
    private List<Wall> wallList;
    private Paint wallPaint;
    private Player player;
    private JoyStick movementJoyStick;
    Paint enemyPaint;
    Context context;
    Canvas c;


    public Map(Context context,Canvas c,Player player){
       this.context = context;
       this.c = c;
       this.player = player;

       wallList = new ArrayList<Wall>();
   }

    public void drawMap(Canvas c){

       //draws all of the enemies
//       for (Enemy enemy: enemies){
//            enemy.draw(c,enemyPaint);
//        }
        c.drawRGB(8, 85, 201);
        //draws all of the wall list
        if (!wallList.isEmpty()) {
            for (Wall wall : wallList) {
                wall.draw(c);
                wall.update();
            }
        }

    }

    public void update(){
        updateEnemies();
    }


    private void updateEnemies() {
        for (Enemy enemy: enemies){
            enemy.draw(c,enemyPaint);
            enemy.update();
        }
    }

    public void addWall(Wall wall){
        wallList.add(wall);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Paint getWallPaint() {
        return wallPaint;
    }

    public void setWallPaint(Paint wallPaint) {
        this.wallPaint = wallPaint;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Wall> getWallList() {
        return wallList;
    }

    public void setWallList(List<Wall> wallList) {
        this.wallList = wallList;
    }




}
