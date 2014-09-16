package com.example.krisda.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class SurfaceViewExample extends Activity implements View.OnTouchListener {

    OurView v;
    Bitmap ball,character;
    float x,y;
    Sprite sprite;
    FrameLayout frm;
    FrameLayout frm2;
    ImageButton stop,start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new OurView(this);
        v.setOnTouchListener(this);
        Bitmap btn = BitmapFactory.decodeResource(getResources(),R.drawable.nav);
        ball = BitmapFactory.decodeResource(getResources(),R.drawable.soccerball);
        character = BitmapFactory.decodeResource(getResources(),R.drawable.spritesheet4);
        setContentView(R.layout.activity_surface_view_example);
        x = 0;
        y = 0;
        stop = (ImageButton)findViewById(R.id.btnStop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.pause();
            }
        });
        start = (ImageButton)findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.resume();
            }
        });
        frm = (FrameLayout)findViewById(R.id.frameLayout);
        frm.addView(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
        }
        return false;
    }


    public class OurView extends SurfaceView implements Runnable{

        Thread t = null;
        SurfaceHolder holder;
        Boolean run = false;
        Boolean spriteLoaded = false;
        Button c = (Button)findViewById(R.id.button);

        public OurView(Context context){
            super(context);
            holder = getHolder();
        }

        @Override
        public void run() {
            sprite = new Sprite(this,character);
            while(run == true){
                if(!holder.getSurface().isValid() == true){
                    continue;
                }
               Canvas c = holder.lockCanvas();
               onDraw(c);
               holder.unlockCanvasAndPost(c);
            }
        }

        public void onDraw(Canvas c){
           // Rect paintOver = new Rect(0,0,540,700);
           // c.drawRect(paintOver,new Paint());
           c.drawARGB(255,120,150,10);
           // c.drawBitmap(ball, x - (ball.getWidth() / 2), y - (ball.getHeight() / 2), null);
           sprite.onDraw(c);
        }
        public void pause(){
            run = false;
            while(true){
                try{
                    t.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }
        public void resume(){
            run = true;
            t = new Thread(this);
            t.start();
        }
    }
}
