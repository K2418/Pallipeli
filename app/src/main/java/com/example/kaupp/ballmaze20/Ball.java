package com.example.kaupp.ballmaze20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;

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

    private boolean bounceTrue = false;
    private int bounceAmount, bounceDirection;
    private int timesUpdated = 0;
    int[] lines;


    //constructor
    public Ball(Context context, DisplayMetrics passedDisplay) {

        x = 150;
        y = 150;
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
        if(bounceTrue == true){
            timesUpdated++;
            if(bounceDirection == 0){
                int move = bounceAmount / 5;
                x -= move;
                if(timesUpdated >= 5){
                    timesUpdated = 0;
                    bounceTrue = false;
                }
            }
            else if(bounceDirection == 1){
                int move = bounceAmount / 5;
                x -= move;
                if(timesUpdated >= 5){
                    timesUpdated = 0;
                    bounceTrue = false;
                }
            }
            else if(bounceDirection == 2){
                int move = bounceAmount / 5;
                y += move;
                if(timesUpdated >= 5){
                    timesUpdated = 0;
                    bounceTrue = false;
                }
            }
            else if(bounceDirection == 3){
                int move = bounceAmount / 5;
                y += move;
                if(timesUpdated >= 5){
                    timesUpdated = 0;
                    bounceTrue = false;
                }
            }
            else{
                bounceTrue = false;
            }
        }
        else{
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
                //x = screenWidth - xNew - ballWidth;
                bounceTrue = true;
                bounceAmount = xNew;
                bounceDirection = 0;
            }
            if(x<0){
                xNew = Bounce(xNew);
                bounceTrue = true;
                bounceAmount = xNew;
                bounceDirection = 1;
            }
            if(y<0){
                yNew = Bounce(yNew);
                bounceTrue = true;
                bounceAmount = yNew;
                bounceDirection = 2;
            }
            if(y>screenHeight - ballHeight){
                yNew = Bounce(yNew);
                bounceTrue = true;
                bounceAmount = yNew;
                bounceDirection = 3;
            }
            if(lines[0] == lines[1]){
                if(x >= lines[0] - ballWidth - 25 && x <= lines[0] - ballWidth){
                    if(y > lines[2]){
                        if(y < lines[3]){
                            xNew = Bounce(xNew);
                            bounceTrue = true;
                            bounceAmount = xNew;
                            bounceDirection = 0;
                        }
                    }
                    else if(y < lines[3]){
                        if (y > lines[2]){
                            xNew = Bounce(xNew);
                            bounceTrue = true;
                            bounceAmount = xNew;
                            bounceDirection = 0;
                        }
                    }
                }
                else if(x >= lines[0] - ballWidth + 25 && x <= lines[0] + 25){
                    if(y > lines[2]){
                        if(y < lines[3]){
                            xNew = Bounce(xNew);
                            bounceTrue = true;
                            bounceAmount = xNew;
                            bounceDirection = 1;
                        }
                    }
                    else if(y < lines[3]){
                        if (y > lines[2]){
                            xNew = Bounce(xNew);
                            bounceTrue = true;
                            bounceAmount = xNew;
                            bounceDirection = 1;
                        }
                    }
                }
            }
            if(lines[2] == lines[3]){
                if(y >= lines[2] - ballWidth - 25 && y <= lines[2] - 25){
                    if(x > lines[0]){
                        if(x < lines[1]){
                            yNew = Bounce(yNew);
                            bounceTrue = true;
                            bounceAmount = yNew;
                            bounceDirection = 2;
                        }
                    }
                    else if(x < lines[1]){
                        if (x > lines[0]){
                            yNew = Bounce(yNew);
                            bounceTrue = true;
                            bounceAmount = yNew;
                            bounceDirection = 2;
                        }
                    }
                }
                if(y <= lines[2] - ballWidth + 25 && y >= lines[2] + 25){
                    if(x > lines[0]){
                        if(x < lines[1]){
                            yNew = Bounce(yNew);
                            bounceTrue = true;
                            bounceAmount = yNew;
                            bounceDirection = 3;
                        }
                    }
                    else if(x < lines[1]){
                        if (x > lines[0]){
                            yNew = Bounce(yNew);
                            bounceTrue = true;
                            bounceAmount = yNew;
                            bounceDirection = 3;
                        }
                    }
                }
            }


        }


        Log.d("Demo2","X=" + Integer.toString(x) + "Y="+ Integer.toString(y));
    }

    private int Bounce(int bounce){

        bounce = bounce * -1;
        bounce = bounce*15;
        return bounce;
    }

    public void Destroy(){
        int min = ballHeight;
        int maxH = screenHeight - ballHeight;
        int maxW = screenWidth - ballWidth;

        Random hori = new Random();
        x = hori.nextInt(maxW - min + 1) + min;
        Random verti = new Random();
        y = verti.nextInt(maxH - min + 1) + min;
        if(lines != null){
            if(lines[1] - lines[0] == 0){
                if(x + ballWidth >= lines[0]) {
                    if (x - ballWidth <= lines[1]) {
                        if (lines[0] > ballWidth) {
                            x = lines[0] - ballWidth - 25;
                        } else {
                            x = lines[0] + ballWidth + 25;
                        }
                    }
                }
            }
            else if(lines[3] - lines[2] == 0){
                if(y + ballWidth >= lines[2]) {
                    if (y - ballWidth <= lines[3]) {
                        if (lines[2] > ballWidth) {
                            y = lines[2] - ballWidth - 25;
                        } else {
                            y = lines[2] + ballWidth + 25;
                        }
                    }
                }
            }
            else{}

            /*if(x >= lines[0]){
                if(x <= lines[1]){
                    if(lines[1] - lines[0] == 0){
                        if(lines[0] > ballWidth){
                            x = lines[0] - ballWidth - 25;
                        }
                        else{
                            x = lines[0] + ballWidth + 25;
                        }
                    }
                }
            }*/
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
