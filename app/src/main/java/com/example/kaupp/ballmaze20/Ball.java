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
    private float acceleration;


    //constructor
    public Ball(Context context, DisplayMetrics passedDisplay) {

        x = 75;
        y = 50;
        acceleration = 1.25f;


        screenWidth = passedDisplay.widthPixels;
        screenHeight = passedDisplay.heightPixels;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        ballHeight = bitmap.getHeight();
        ballWidth = bitmap.getWidth();
    }

    //Method to update coordinate of character
    public void update( int xNew, int yNew){
        //updating x & y coordinate
        if(xNew > 6 || xNew < -6){
            x -= xNew * (acceleration*2);
        }
        else if(xNew > 5 || xNew < -5){
            x -= xNew * (acceleration *1.8);
        }
        else if(xNew > 4 || xNew < -4){
            x -= xNew * (acceleration *1.6);
        }

        else if(xNew > 3 || xNew < -3){
            x -= xNew * (acceleration *1.4);
        }

        else if(xNew > 2 || xNew < -2){
            x -= xNew * (acceleration *1.2);
        }
        else{
            x -= xNew * acceleration;
        }

        if(yNew > 6 || yNew < -6){
            y += yNew * (acceleration*2);
        }
        else if(yNew > 5 || yNew < -5){
            y += yNew * (acceleration *1.8);
        }
        else if(yNew > 4 || yNew < -4){
            y += yNew * (acceleration *1.6);
        }

        else if(yNew > 3 || yNew < -3){
            y += yNew * (acceleration *1.4);
        }

        else if(yNew > 2 || yNew < -2){
            y += yNew * (acceleration *1.2);
        }
        else{
            y += yNew * acceleration;
        }


        if(x > screenWidth - ballWidth){
            xNew = Bounce(xNew);
            x += xNew;
        }
        if(x<0)x=0;
        if(y<0)y=0;
        if(y>screenHeight - ballHeight)y=screenHeight-ballHeight;
        Log.d("Demo2","X=" + Integer.toString(x) + "Y="+ Integer.toString(y));
    }

    private int Bounce(int bounce){

        bounce = bounce * -1;
        return bounce;
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

}
