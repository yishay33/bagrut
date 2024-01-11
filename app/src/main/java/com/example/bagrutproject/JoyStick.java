package com.example.bagrutproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class JoyStick {

    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int outerCircleRadius;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private  int innerCircleRadius;

    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private double joystickCenterToTouchRadius;
    private boolean isPressed;

    private double actuatorX;
    private double actuatorY;

    public JoyStick(int centerPositionX,int centerPositionY,int outerCircleRadius,int innerCircleRadius){

        //both circles make the joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        //circles radius
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        //paint
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.RED);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }
    public void update(){
        updateInnerCirclePos();
    }

    private void updateInnerCirclePos() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    public void draw(Canvas c){
        c.drawCircle(outerCircleCenterPositionX,outerCircleCenterPositionY,outerCircleRadius,outerCirclePaint);
        c.drawCircle(innerCircleCenterPositionX,innerCircleCenterPositionY,innerCircleRadius,innerCirclePaint);
    }

    public boolean isPressed(double touchPosX,double touchPosY) {
        joystickCenterToTouchRadius = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPosX, 2) +
                Math.pow(outerCircleCenterPositionY - touchPosY, 2)
        );

        return joystickCenterToTouchRadius < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
    public boolean getIsPressed(){
        return this.isPressed;
    }

    public void setActuator(double touchPosX,double touchPosY) {
        double deltaX = touchPosX - outerCircleCenterPositionX;
        double deltaY = touchPosY - outerCircleCenterPositionY;

        double deltaDistance = Math.sqrt(
                Math.pow(deltaX, 2) +
                Math.pow(deltaY, 2)
                );
        if (deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }else{
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }

    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }
    public double getActuatorY() {
        return actuatorY;
    }
}
