package com.example.krisda.myfirstapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Krisda on 9/9/2014.
 */
public class Sprite {
    int x,y;
    int xSpeed,ySpeed;
    int width,height;
    Bitmap b;
    SurfaceViewExample.OurView ov;
    int currentFrame = 0;
    int direction = 3;
    public Sprite(SurfaceViewExample.OurView ourView, Bitmap character){
        b = character;
        ov = ourView;
        //4 rows, 3 columns
        height = b.getHeight()/4;
        width = b.getWidth()/4;
        x = 0;
        y = 0;
        xSpeed = 8;
        ySpeed = 0;
    }
    protected void onDraw(Canvas c){
        update();
        int srcX = currentFrame * width;
        int srcY = direction * height;
        Rect src = new Rect(srcX,srcY,srcX+width,srcY+height);
        Rect dst = new Rect(x,y,x+width,y+height);
        c.drawBitmap(b,src,dst,null);
    }
    public void update(){
        //0 = down
        //1 = left
        //2 = up
        //3 = right
        //facing down
        if(x > ov.getWidth() - width - xSpeed){
            xSpeed = 0;
            ySpeed = 8;
            direction = 0;
        }
        //going left
        if(y > ov.getHeight() - height - ySpeed){
            ySpeed = 0;
            xSpeed = -8;
            direction = 1;
        }
        //going up
        if(x + xSpeed < 0){
            x = 0;
            xSpeed = 0;
            ySpeed = -8;
            direction = 2;
        }
        //facing right
        if(y + ySpeed < 0){
            y = 0;
            ySpeed = 0;
            xSpeed = 8;
            direction = 3;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentFrame = ++currentFrame % 3;
        x += xSpeed;
        y += ySpeed;
    }
}
