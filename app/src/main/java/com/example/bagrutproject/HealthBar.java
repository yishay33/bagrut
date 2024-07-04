package com.example.bagrutproject;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HealthBar {

    private Player player;
    private int height,margin;
    private Paint healthPaint, backgroundPaint;

    public HealthBar(Player player){
        this.player = player;
        this.height = 20;
        this.margin = 2;

        healthPaint = new Paint();
        healthPaint.setColor(Color.GREEN);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.GRAY);
    }

    public void draw(Canvas canvas){
        float x = player.getPosX();
        float y = player.getPosY();

        float distanceAbovePlayer = 30;
        float healthPointsPercentage = (float) player.getCurrentHp()/player.MAX_HP;

        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x;
        borderRight = x + player.getBitmap().getWidth();
        borderBottom = y - distanceAbovePlayer;
        borderTop = borderBottom - height;
        //draws the background of empty health

        canvas.drawRect(borderLeft,borderTop,borderRight,borderBottom,backgroundPaint);

        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = player.getBitmap().getWidth();
        healthHeight = height - 2 * margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth*healthPointsPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;

        //draws the current health above the empty health
        canvas.drawRect(healthLeft,healthTop,healthRight,healthBottom,healthPaint);
    }
}
