package com.example.surfaceviewdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Projectile {

    // INSTANCE VARIABLES
    private int yOrig;

    private int x;
    private int y;
    private int projectileSpeed;

    private boolean canScore;

    private Bitmap bitmap;
    private Rect rect;

    public Projectile(Context context, int x, int y, int projectileSpeed) {
        this.x = x;
        this.y = y;
        this.yOrig = y;

        this.projectileSpeed = projectileSpeed;
        this.canScore = true;

        int random = (int)(Math.random()*2)+1;

        if(random == 1)
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile);
        else
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile2);

        rect = new Rect(x, y, x + getWidth(), y + getHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getYOrig() {
        return yOrig;
    }

    public int getSpeed() {
        return projectileSpeed;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public boolean getScorable() {
        return canScore;
    }

    public void setScorable(boolean scorable) {
        canScore = scorable;
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


}
