package com.example.kaupp.ballmaze20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Level {

    //Bitmap to get character from image
    private Bitmap bitmap;
    private int x;
    private int y;

    //constructor
    public Level(Context context) {
        x = 0;
        y= 0;
        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.outerwall);
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
