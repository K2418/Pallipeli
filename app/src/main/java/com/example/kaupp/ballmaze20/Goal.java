package com.example.kaupp.ballmaze20;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.util.Random;

public class Goal {
    //Bitmap to get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    int goalHeight, goalWidth;
    int screenWidth,screenHeight;


    public Goal(Context context, DisplayMetrics passedDisplay) {

        screenWidth = passedDisplay.widthPixels;
        screenHeight = passedDisplay.heightPixels;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.goal);
        goalHeight = bitmap.getHeight();
        goalWidth = bitmap.getWidth();

        x = screenWidth / 2 - goalWidth;
        y = screenHeight /2  - goalWidth;
    }

    public void Done(){
        int min = goalHeight;
        int maxH = screenHeight;
        int maxW = screenWidth;

        Random hori = new Random();
        y = hori.nextInt(maxW - min + 1) + min;

        Random verti = new Random();
        x = verti.nextInt(maxH - min + 1) + min;
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
