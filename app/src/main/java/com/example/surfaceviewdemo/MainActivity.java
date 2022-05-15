package com.example.surfaceviewdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // GAME SURFACE
    GameSurface gameSurface;

    // TIMER
    CountDownTimer timer;

    // CONSTANTS
    final int TOTAL_TIME = 60;
    final int TIME_INTERVAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this);
        setContentView(gameSurface);

        // ROTATION SENSOR
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_FASTEST);

        // TIMER
        timer = new CountDownTimer(TOTAL_TIME * 1000, TIME_INTERVAL * 1000) {

            public void onTick(long millisUntilFinished) {
                gameSurface.setTime(gameSurface.getTime()-1);
            }

            public void onFinish() {
                gameSurface.state = 1;
            }

        };
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameSurface.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameSurface.resume();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // SET SPEED VALUE FROM SENSOR VALUE
        int xSpeed = (int) ((sensorEvent.values[1]) * 40);

        if(gameSurface.player.getCollide())
            gameSurface.setSpeed(xSpeed);
        else
            gameSurface.setSpeed(0);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(gameSurface.state == 1) {
                    gameSurface.reset();
                    gameSurface.state = 0;
                } else {
                    if(gameSurface.gameSpeed == 1)
                        gameSurface.gameSpeed = 2;
                    else
                        gameSurface.gameSpeed = 1;
                }
        }

        return super.onTouchEvent(event);
    }

    public class GameSurface extends SurfaceView implements Runnable {

        Thread gameThread;
        SurfaceHolder holder;

        // SOUND EFFECTS
        MediaPlayer gameMusic;
        MediaPlayer scoreSound;
        MediaPlayer collideSound;

        // GAME OBJECTS
        Player player;
        Projectile projectile1;
        Projectile projectile2;
        Projectile projectile3;

        Projectile[] projectiles;

        // BITMAPS
        Bitmap background;
        Bitmap planet1;
        Bitmap planet2;
        Bitmap planet3;
        Bitmap heart;

        // SCALED BITMAPS
        Bitmap backgroundScaled;
        Bitmap playerScaled;
        Bitmap planetScaled1;
        Bitmap planetScaled2;
        Bitmap planetScaled3;
        Bitmap heartScaled;

        Paint paintProperty;

        // STATE VARIABLES
        volatile boolean running = false;
        boolean playerHit = false;
        int projHitSpeed = 0;
        long startTime = 0;

        int state = 0;
        int gameSpeed = 1;

        int score = 0;
        int time = 60;
        int lives = 3;

        // SCREEN SIZE
        int screenWidth;
        int screenHeight;

        // POSITION VARIABLES
        int planetX1 = 300;
        int planetY1 = 1000;
        int planetX2 = 800;
        int planetY2 = 500;
        int planetX3 = 70;
        int planetY3 = 70;
        int backY = 0;

        // SPEED VARIABLES
        int xSpeed = 0;

        public GameSurface(Context context) {
            super(context);
            holder = getHolder();

            // DEFINING GAME OBJECTS
            player = new Player(getBaseContext(),470, 1300, 160,120, R.drawable.player);

            projectile1 = new Projectile(getBaseContext(), 60, -500, 7);
            projectile2 = new Projectile(getBaseContext(), 500, -1500, 7);
            projectile3 = new Projectile(getBaseContext(), 700, -2500, 7);

            projectiles = new Projectile[] {projectile1, projectile2, projectile3};

            // DEFINE OBJECT BITMAPS
            background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            planet1 = BitmapFactory.decodeResource(getResources(), R.drawable.mercury);
            planet2 = BitmapFactory.decodeResource(getResources(), R.drawable.red);
            planet3 = BitmapFactory.decodeResource(getResources(), R.drawable.rings);
            heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);

            // SCALE UP BITMAPS
            backgroundScaled = Bitmap.createScaledBitmap(background, 1080, 2000, false);
            planetScaled1 = Bitmap.createScaledBitmap(planet1, 90, 90, false);
            planetScaled2 = Bitmap.createScaledBitmap(planet2, 130, 130, false);
            planetScaled3 = Bitmap.createScaledBitmap(planet3, 250, 250, false);
            heartScaled = Bitmap.createScaledBitmap(heart, 80, 80, false);

            Display screenDisplay = getWindowManager().getDefaultDisplay();
            Point sizeOfScreen = new Point();
            screenDisplay.getSize(sizeOfScreen);

            gameMusic = MediaPlayer.create(getBaseContext(), R.raw.game_music);
            collideSound = MediaPlayer.create(getBaseContext(), R.raw.explosion);
            scoreSound = MediaPlayer.create(getBaseContext(), R.raw.score);

            gameMusic.setVolume(20, 20);
            gameMusic.setLooping(true);
            gameMusic.start();

            scoreSound.setLooping(false);
            collideSound.setLooping(false);
            collideSound.setVolume(10, 10);

            screenWidth = sizeOfScreen.x;
            screenHeight = sizeOfScreen.y;

            paintProperty = new Paint();

        }

        @Override
        public void run() {
            timer.start();

            while (running == true) {
                if (holder.getSurface().isValid() == false)
                    continue;

                Canvas canvas = holder.lockCanvas();
                canvas.drawRGB(50, 50, 50);

                if(state == 0) {
                    // DRAW BACKGROUND
                    for (int i = 0; i < 20; i++) {
                        canvas.drawBitmap(backgroundScaled, 0, (backY - (i * 2000)), null);
                    }

                    // DRAW OBJECTS
                    canvas.drawBitmap(planetScaled1, planetX1, planetY1, null);
                    canvas.drawBitmap(planetScaled2, planetX2, planetY2, null);
                    canvas.drawBitmap(planetScaled3, planetX3, planetY3, null);

                    canvas.drawBitmap(player.getBitmapScaled(), player.getX(), player.getY(), null);
                    player.setRect();

                    for (int i = 0; i < projectiles.length; i++) {
                        canvas.drawBitmap(projectiles[i].getBitmap(), projectiles[i].getX(), projectiles[i].getY(), null);
                        projectiles[i].setRect();
                    }

                    // FONT CONFIGURATION
                    Paint paint = new Paint();
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/spaceworm.ttf");
                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(60);
                    paint.setTypeface(typeface);

                    // PLAYER HUD
                    canvas.drawText("Score: " + score, 20, 90, paint);
                    canvas.drawText("Time: " + time, 20, 190, paint);

                    for (int i = 0; i < lives; i++) {
                        canvas.drawBitmap(heartScaled, screenWidth - 100 - (100 * i), 40, null);
                    }

                    // OBJECT SPEEDS
                    backY += (3*gameSpeed);
                    planetY1 += (4*gameSpeed);
                    planetY2 += (5*gameSpeed);
                    planetY3 += (6*gameSpeed);

                    if(!player.getCollide()) {
                        player.setY(player.getY() + projHitSpeed);
                        player.setRes(getBaseContext(), R.drawable.explosion05);

                        if(System.currentTimeMillis()-startTime > 3000) {
                            player.setRes(getBaseContext(), R.drawable.player);
                            player.setY(1300);
                            player.setCollide(true);
                        }
                    }


                    for (int i = 0; i < projectiles.length; i++) {
                        projectiles[i].setY(projectiles[i].getY() + (projectiles[i].getSpeed()*gameSpeed));
                    }

                    player.setX(player.getX() + xSpeed);

                    // PROJECTILE RESPAWN
                    for (int i = 0; i < projectiles.length; i++) {
                        if (projectiles[i].getY() > screenHeight) {
                            projectiles[i].setY(projectiles[i].getYOrig());
                            projectiles[i].setScorable(true);
                        }
                    }

                    // PLANET RESPAWN
                    if (planetY1 > screenHeight) {
                        planetY1 = -500;
                    }
                    if (planetY2 > screenHeight) {
                        planetY2 = -800;
                    }
                    if (planetY3 > screenHeight) {
                        planetY3 = -1000;
                    }

                    // SCORING
                    for (int i = 0; i < projectiles.length; i++) {
                        if (projectiles[i].getY() > player.getY() && projectiles[i].getScorable()) {
                            score++;
                            scoreSound.start();
                            projectiles[i].setScorable(false);
                        }
                    }

                    // COLLISIONS
                    for (int i = 0; i < projectiles.length; i++) {
                        if (player.getCollide()) {
                            if (player.getRect().intersect(projectiles[i].getRect())) {
                                lives--;
                                projHitSpeed = projectiles[i].getSpeed();
                                startTime = System.currentTimeMillis();
                                collideSound.start();
                                player.setCollide(false);
                            }
                        }
                    }

                    // PLAYER BOUNDARIES
                    if (player.getX() < 0) {
                        player.setX(0);
                    }

                    if (player.getX() > screenWidth - player.getWidth()) {
                        player.setX(screenWidth - player.getWidth());
                    }

                    // CHECK LIVES
                    if (lives <= 0) {
                        state = 1;
                    }

                    holder.unlockCanvasAndPost(canvas);
                } else {
                    timer.cancel();
                    gameMusic.pause();
                    gameMusic.seekTo(0);
                    canvas.drawBitmap(backgroundScaled, 0, 0, null);

                    // FONT CONFIGURATION
                    Paint paint = new Paint();
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/spaceworm.ttf");
                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(100);
                    paint.setTypeface(typeface);

                    // PLAYER HUD
                    canvas.drawText("Your Score Was...", 120, screenHeight/4, paint);
                    paint.setColor(Color.RED);
                    canvas.drawText(String.valueOf(score), (screenWidth/2)-30, (screenHeight/4)+200, paint);
                    paint.setColor(Color.rgb(255, 126, 13));
                    paint.setTextSize(80);
                    canvas.drawText("Tap To Play Again", 200, screenHeight-700, paint);

                    holder.unlockCanvasAndPost(canvas);
                }

            }
        }

        public void resume(){
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        public void pause() {
            running = false;
            while (true) {
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                }
            }
        }

        public void setSpeed(int speed) {
            this.xSpeed = speed;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getTime() {
            return time;
        }

        public void reset() {
            gameMusic.start();

            player = new Player(getBaseContext(),470, 1300, 160,120, R.drawable.player);

            projectile1 = new Projectile(getBaseContext(), 60, -500, 7);
            projectile2 = new Projectile(getBaseContext(), 500, -1500, 7);
            projectile3 = new Projectile(getBaseContext(), 700, -2500, 7);

            projectiles = new Projectile[] {projectile1, projectile2, projectile3};

            planetX1 = 300;
            planetY1 = 1000;
            planetX2 = 800;
            planetY2 = 500;
            planetX3 = 70;
            planetY3 = 70;
            backY = 0;

            xSpeed = 0;

            score = 0;
            time = 60;
            lives = 3;

            state = 0;

            timer.start();
        }

    }
}