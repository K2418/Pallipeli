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

    private int goalHeight, goalWidth;
    private int screenWidth,screenHeight;

    int[] lines;


    public Goal(Context context, DisplayMetrics passedDisplay) {

        screenWidth = passedDisplay.widthPixels;
        screenHeight = passedDisplay.heightPixels;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.goal);
        goalHeight = bitmap.getHeight();
        goalWidth = bitmap.getWidth();

        x = screenWidth / 2 - goalWidth;
        y = screenHeight / 2  - goalWidth;
    }

    public void Done(){
        int min = goalHeight;
        int maxH = screenHeight - goalHeight;
        int maxW = screenWidth - goalWidth;

        Random hori = new Random();
        x = hori.nextInt(maxW - min + 1) + min;

        Random verti = new Random();
        y = verti.nextInt(maxH - min + 1) + min;

        if(lines != null) {
            if(lines[1] - lines[0] == 0){
                if(x + goalWidth >= lines[0]) {
                    if (x - goalWidth <= lines[1]) {
                        if (lines[0] > goalWidth) {
                            x = lines[0] - goalWidth - 25;
                        } else {
                            x = lines[0] + goalWidth + 25;
                        }
                    }
                }
            }
            else if(lines[3] - lines[2] == 0){
                if(y + goalWidth >= lines[2]) {
                    if (y - goalWidth <= lines[3]) {
                        if (lines[2] > goalWidth) {
                            y = lines[2] - goalWidth - 25;
                        } else {
                            y = lines[2] + goalWidth + 25;
                        }
                    }
                }
            }
            else{}
        }
    }

    public void SetLines(int[] gets){
        lines = gets;
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
