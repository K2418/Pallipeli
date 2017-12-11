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

    //Class constructor
    public GameView(Context context, SensorManager passedManager, DisplayMetrics passedDisplay) {
        super(context);
        sensorManager = passedManager;
        wood1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wood1);
        ball = new Ball(context, passedDisplay);
        goal = new Goal(context, passedDisplay);
        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        wallPaint = new Paint();

    }

    @Override
    public void run() {

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_GAME);

        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }

    private void update() {

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
        ball.Destroy();
        goal.Done();
        goalIsAchieved = true;
    }

    public void DrawWalls(Canvas wallCanvas){


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
                wallPaint.setStrokeWidth(50);
                wallCanvas.drawLine(300,100,300,700, wallPaint);
                break;
            case 1:
                wallPaint.setColor(Color.RED);
                wallPaint.setStrokeWidth(50);
                wallCanvas.drawLine(500,50,500,700, wallPaint);
                break;
            case 2:
                wallPaint.setColor(Color.BLUE);
                wallPaint.setStrokeWidth(50);
                wallCanvas.drawLine(50,50,500,700, wallPaint);
                break;
            case 3:
                wallPaint.setColor(Color.GREEN);
                wallPaint.setStrokeWidth(50);
                wallCanvas.drawLine(50,50,1000,700, wallPaint);
                break;
            case 4:
                wallPaint.setColor(Color.BLACK);
                wallPaint.setStrokeWidth(50);
                wallCanvas.drawLine(50,50,1000,700, wallPaint);
                break;
            case 5:

            default:
                wallPaint.setColor(Color.MAGENTA);
                wallPaint.setStrokeWidth(50);
                wallCanvas.drawLine(50,50,1000,700, wallPaint);
                break;
        }
    }
}
