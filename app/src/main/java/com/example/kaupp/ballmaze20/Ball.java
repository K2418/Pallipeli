package com.example.kaupp.ballmaze20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Ball {

    //Bitmap to get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    private int width, height;

    //motion speed of the character
    private int speed = 0;


    //constructor
    public Ball(Context context) {

        x = 75;
        y = 50;
        speed = 1;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
    }

    //Method to update coordinate of character
    public void update( int xNew, int yNew){
        //updating x coordinate
        x += xNew;
        y += yNew;


        if(x>1650)x=1650;
        if(x<0)x=0;
        if(y<0)y=0;
        if(y>890)y=890;
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

    public int getSpeed() {
        return speed;
    }
}
