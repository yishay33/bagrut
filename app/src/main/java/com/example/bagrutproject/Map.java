package com.example.bagrutproject;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public abstract class Map {

    private List<Enemy> enemies;
    private List<Wall> walls;

    int r;
    int g;
    int b;

    public Map(int r,int g, int b) {
        walls = new ArrayList<Wall>();
        this.r = r;
        this.g = g;
        this.b = b;
    }

    protected void drawMap(Canvas c){
        for (Wall wall: walls){
            wall.draw(c);
        }
    }

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

    //    private void createEnemies() {
    //        enemies.add(new Enemy(800,200, BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy),getContext(),getPlayer()));
    //        setEnemies(enemies);
    //    }

}
