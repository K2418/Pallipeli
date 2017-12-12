package com.example.kaupp.ballmaze20;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

import static xdroid.toaster.Toaster.toast;
import static xdroid.toaster.Toaster.toastLong;

public class GameView extends SurfaceView implements SensorEventListener, Runnable {

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    //Game Objects
    private Ball ball;
    private Goal goal;
    private Level level;

    //Variables for level selection
    private boolean goalIsAchieved = false;
    private int selected;


    //Add sensors to game
    public SensorManager sensorManager;
    private Sensor sensor;

    //For drawing game
    private Paint paint, wallPaint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Bitmap wood1;

    double x = 0, y = 0;
    int screenWidth,screenHeight;
    int[] linePos = new int[4];
    boolean drawLine = false;

    //Class constructor
    public GameView(Context context, SensorManager passedManager, DisplayMetrics passedDisplay) {
        super(context);
        sensorManager = passedManager;
        screenWidth = passedDisplay.widthPixels;
        screenHeight = passedDisplay.heightPixels;
        wood1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wood1);
        ball = new Ball(context, passedDisplay);
        goal = new Goal(context, passedDisplay);
        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        wallPaint = new Paint();
        wallPaint.setStrokeWidth(50);

    }

    @Override
    public void run() {

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_GAME);

        while (playing) {
            //update important game stuff
            update();

            //draw the frame
            draw();

            //controls
            control();
        }
    }

    private void update() {
        ball.SetLines(linePos);
        goal.SetLines(linePos);
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.WHITE);
            //Drawing the player
            canvas.drawBitmap(
                    wood1,
                    0,
                    0,
                    paint);

            DrawWalls(canvas);

            canvas.drawBitmap(
                    goal.getBitmap(),
                    goal.getX(),
                    goal.getY(),
                    paint);
            canvas.drawBitmap(
                    ball.getBitmap(),
                    ball.getX(),
                    ball.getY(),
                    paint);
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
            int xNew = (int) x;
            int yNew = (int) y;
            ball.update(xNew,yNew);
            if(ball.getX() >= goal.getX() - 20 && ball.getX() <= goal.getX() + 20){
                if(ball.getY() >= goal.getY() - 20 && ball.getY() <= goal.getY() + 20) {
                    GoalAchieved();
                }
            }
            Log.d("Demo","X=" + Integer.toString(xNew) + "Y="+ Integer.toString(yNew));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to f alse
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensorDump, int accuracy){}

    public void GoalAchieved(){
        toastLong("Jippii");
        StageSelector();
        ball.Destroy();
        goal.Done();
        goalIsAchieved = true;
    }

    public void DrawWalls(Canvas wallCanvas){

        if(drawLine){
            wallCanvas.drawLine(linePos[0],linePos[2],linePos[1],linePos[3], wallPaint);
        }
    }
    private void StageSelector(){

        Random stage = new Random();
        int stageSelector;

        if(goalIsAchieved){
            stageSelector = stage.nextInt((5-0+1)+1);
            selected = stageSelector;
            goalIsAchieved = false;
        }
        else {
            stageSelector = selected;
        }
        switch (stageSelector){
            case 0:
                wallPaint.setColor(Color.MAGENTA);
                linePos[0] = 0;
                linePos[1] = 600;
                linePos[2] = 700;
                linePos[3] = 700;
                drawLine = true;
                break;
            case 1:
                wallPaint.setColor(Color.RED);
                linePos[0] = 500;
                linePos[1] = 500;
                linePos[2] = 50;
                linePos[3] = 700;
                drawLine = true;
                break;
            case 2:
                wallPaint.setColor(Color.BLUE);
                linePos[0] = 500;
                linePos[1] = 500;
                linePos[2] = 250;
                linePos[3] = 800;
                drawLine = true;
                break;
            case 3:
                wallPaint.setColor(Color.GREEN);
                linePos[0] = 600;
                linePos[1] = 600;
                linePos[2] = 250;
                linePos[3] = 1600;
                drawLine = true;
                break;
            case 4:
                wallPaint.setColor(Color.BLACK);
                linePos[0] = 700;
                linePos[1] = 700;
                linePos[2] = 400;
                linePos[3] = 700;
                drawLine = true;
                break;
            case 5:
                linePos[0] = 0;
                linePos[1] = 0;
                linePos[2] = 0;
                linePos[3] = 0;
                drawLine = false;
                break;
            default:
                break;
        }
    }
}
