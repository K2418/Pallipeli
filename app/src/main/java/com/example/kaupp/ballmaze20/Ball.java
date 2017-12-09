package com.example.kaupp.ballmaze20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import static java.security.AccessController.getContext;

public class Ball {

    //Bitmap to get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    private int ballWidth, ballHeight;

    private int screenWidth, screenHeight;

    //motion speed of the character
    private int speed = 0;


    //constructor
    public Ball(Context context, DisplayMetrics passedDisplay) {

        x = 75;
        y = 50;
        speed = 1;


        screenWidth = passedDisplay.widthPixels;
        screenHeight = passedDisplay.heightPixels;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        ballHeight = bitmap.getHeight();
        ballWidth = bitmap.getWidth();
    }

    //Method to update coordinate of character
    public void update( int xNew, int yNew){
        //updating x coordinate
        x -= xNew;
        y += yNew;


        if(x>screenWidth - ballWidth)x=screenWidth-ballWidth;
        if(x<0)x=0;
        if(y<0)y=0;
        if(y>screenHeight - ballHeight)y=screenHeight-ballHeight;
        Log.d("Demo2","X=" + Integer.toString(x) + "Y="+ Integer.toString(y));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() { return ballWidth; }

    public int getHeight() { return ballHeight; }

    public int getSpeed() {
        return speed;
    }
}
