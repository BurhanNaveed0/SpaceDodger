package com.example.surfaceviewdemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {

    // INSTANCE VARIABLES
    private int x;
    private int y;
    private int playerSpeed;

    private int width;
    private int height;

    private Bitmap bitmap;
    private Bitmap bitmapScaled;
    private int res;

    private boolean canCollide;

    private Rect rect;

    public Player(Context context, int x, int y, int width, int height, int res) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.playerSpeed = 0;
        this.canCollide = true;

        this.res = res;

        bitmap = BitmapFactory.decodeResource(context.getResources(), this.res);
        bitmapScaled = Bitmap.createScaledBitmap(bitmap, width, height, false);

        rect = new Rect(x, y, x + getWidth(), y + getHeight());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setRes(Context context, int res) {
        this.res = res;
        bitmap = BitmapFactory.decodeResource(context.getResources(), this.res);
        bitmapScaled = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public int getHeight() {
        return height;
    }

    public boolean getCollide() {
        return canCollide;
    }

    public void setCollide(boolean collide) {
        this.canCollide = collide;
    }

    public void setRect() {
        rect.set(x, y, x + getWidth(), y + getHeight());
    }

    public Rect getRect() {
        return rect;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap getBitmapScaled() {
        return bitmapScaled;
    }
}
