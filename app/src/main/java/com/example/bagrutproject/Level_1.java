package com.example.bagrutproject;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class Level_1 extends Map{

    List<Enemy> enemies;


    public Level_1(Context context, Canvas c,Player player) {
        super(context,c,player);

        Paint p = new Paint();
        p.setColor(Color.RED);
        setWallPaint(p);

        createWalls();
//        createEnemies();
    }

    private void createEnemies() {
        enemies.add(new Enemy(800,200, BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy),getContext(),getPlayer()));
        setEnemies(enemies);
    }

    public void createWalls() {
        if (getWallList() != null) {
            addWall(new Wall(0, 0, 1950, 50, getWallPaint()));
            addWall(new Wall(0, 1030, 1950, 50, getWallPaint()));
            addWall(new Wall(0, 0, 50, 1030, getWallPaint()));
            addWall(new Wall(1870, 0, 50, 1030, getWallPaint()));
        }
    }


}
