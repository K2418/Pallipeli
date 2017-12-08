package com.example.kaupp.ballmaze20;

import android.app.Service;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SensorEventListener, Runnable {

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    private Ball ball;
    private Level level;


    //Add sensors to game
    public SensorManager sensorManager;
    private Sensor sensor;

    //For drawing game
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    double x = 0, y = 0;

    //Class constructor
    public GameView(Context context, SensorManager passedManager) {
        super(context);
        sensorManager = passedManager;
        ball = new Ball(context);
        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
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
/*            canvas.drawBitmap(
                    level.getBitmap(),
                    level.getX(),
                    level.getY(),
                    paint);*/
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
            Log.d("Demo","X=" + Integer.toString(xNew) + "Y="+ Integer.toString(yNew));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
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
}
