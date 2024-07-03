package com.example.bagrutproject;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class Map {

    private List<Enemy> enemies;
    private List<Wall> walls;

    int r;
    int g;
    int b;
    MySurfaceView mySurfaceView;
    Context context;

    public Map(int r, int g, int b, MySurfaceView mySurfaceView, Context context) {
        walls = new ArrayList<Wall>();
        enemies = new ArrayList<Enemy>();
        this.r = r;
        this.g = g;
        this.b = b;
        this.mySurfaceView = mySurfaceView;
        this.context = context;
    }

    protected void drawMap(Canvas c){
        for (Wall wall: walls){
            wall.draw(c);
        }
    }

    protected void drawEnemies(Canvas c){
        for (Enemy enemy: enemies){
            enemy.draw(c);
        }
    }

    protected abstract void createWalls();

    protected abstract void createEnemies();

    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    public List<Wall> getWalls() {
        return walls;
    }
    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    protected void addWall(Wall wall){
        walls.add(wall);
    }
    protected void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }
    protected int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    protected void updateWalls() {
        for (Wall wall: walls){
            wall.update();
        }
    }

    protected void updateEnemies(Player player) {
        for (Enemy enemy: enemies){
            enemy.update(player);
        }
    }

    //    private void createEnemies() {
    //        enemies.add(new Enemy(800,200, BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy),getContext(),getPlayer()));
    //        setEnemies(enemies);
    //    }

}
