package com.example.bagrutproject;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Random;

public class Level_1 extends Map{

    Paint wallPaint = new Paint();

    public Level_1(MySurfaceView mySurfaceView, Context context) {
        super(mySurfaceView,context);
        int color = ContextCompat.getColor(context,R.color.wall);
        wallPaint.setColor(color);
    }



    @Override
    public void createWalls() {
        //borders
        addWall(new Wall(0, 0, mySurfaceView.getWidth(), mySurfaceView.getHeight()/50,wallPaint ));
        addWall(new Wall(0, mySurfaceView.getHeight() -mySurfaceView.getHeight()/80, mySurfaceView.getWidth(), mySurfaceView.getHeight()/80, wallPaint));
        addWall(new Wall(0, 0, mySurfaceView.getWidth()/100, mySurfaceView.getHeight(), wallPaint));
        addWall(new Wall(mySurfaceView.getWidth() - mySurfaceView.getWidth()/100, 0, mySurfaceView.getWidth()/100, mySurfaceView.getHeight(), wallPaint));

        //obstacles
        addWall(new Wall(mySurfaceView.getWidth()/6,0,mySurfaceView.getWidth()/100, (int) (mySurfaceView.getHeight()*0.8),wallPaint));
        addWall(new Wall(mySurfaceView.getWidth()/6, (int) (mySurfaceView.getHeight()*0.3), (int) (mySurfaceView.getWidth()/0.4),mySurfaceView.getHeight()/100,wallPaint));
    }

    @Override
    protected void createEnemies() {
        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.05), (float) (mySurfaceView.getHeight()*0.1)
                ,BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost1)));
        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.7), (float) (mySurfaceView.getHeight()*0.5)
                ,BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost1)));
        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.3), (float) (mySurfaceView.getHeight()*0.6),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost1)));



        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.98), (float) (mySurfaceView.getHeight()*0.4)
                ,BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost2)));
        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.01), (float) (mySurfaceView.getHeight()*0.98)
                ,BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost2)));
        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.5), (float) (mySurfaceView.getHeight()*0.6),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost2)));



        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.6), (float) (mySurfaceView.getHeight()*0.9)
                ,BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost3)));
        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.1), (float) (mySurfaceView.getHeight()*0.5)
                ,BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost3)));
        addEnemy(new Enemy((float) (mySurfaceView.getWidth()*0.9), (float) (mySurfaceView.getHeight()*0.1),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost3)));
    }


}
