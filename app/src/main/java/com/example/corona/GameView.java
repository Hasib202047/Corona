package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying;
    private  Background background1,background2;
    private int screenX,screenY,score=0;
    private Paint paint;
    private Character hasib;
    public static float screenRatioX,screenRatioY;
    private List<Bullet> bullets;
    private Game_Activity activity;
    private Bullet bullet;
    private Virus []viruses;
    Random r=new Random();
    int i=r.nextInt(4);
    private boolean isGameOver=false;
    private SharedPreferences prefs;


    public GameView(Game_Activity activity,int screenX, int screenY) {
        super(activity);
        this.activity=activity;
        prefs=activity.getSharedPreferences("game",Context.MODE_PRIVATE);
        this.screenX=screenX;
        this.screenY=screenY;
        screenRatioX=1920f/screenX;
        screenRatioY=1080f/screenY;
        background1= new Background(screenX,screenY,getResources());
        background2= new Background(screenX,screenY,getResources());
        hasib = new Character(screenY,getResources());
        background2.x=screenX;
        paint=new Paint();
        paint.setTextSize(120);
        paint.setColor(Color.WHITE);
        bullets= new ArrayList<>();
        bullet=new Bullet(getResources());
        bullet.x=hasib.x+(hasib.width/2);
        bullet.y=hasib.y+(hasib.height/2);
        viruses=new Virus[3];
        for(int i=0;i<3;i++)
        {
            Virus v=new Virus(getResources());
            viruses[i]=v;
        }

    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();

        }

    }
    private void update(){
        background1.x -=10*screenRatioX;
        background2.x -=10*screenRatioX;

        if(background1.x+background1.background.getWidth()<0){
            background1.x=screenX;
        }
        if(background2.x+background2.background.getWidth()<0){
            background2.x=screenX;
        }


        if(hasib.isGoingUp)
            hasib.y -=70*screenRatioY;
        else
            hasib.y +=70*screenRatioY;
        if(hasib.y<-150)
            hasib.y=-150;
        if(hasib.y>screenY-hasib.height)
            hasib.y=screenY-hasib.height;

        List<Bullet>trash= new ArrayList<>();
        if(bullet.shoot) {
            for (Bullet bullet : bullets) {
                if (bullet.x > screenX)
                    trash.add(bullet);

                bullet.x += 40 * screenRatioX;
                for(int k=0;k<3;k++){
                    if (Rect.intersects(viruses[k].getCollisionShape(), bullet.getCollisionShape())) {
                        viruses[k].x = screenX-100;
                        bullet.x = screenX + 500;
                        viruses[k].wasShot=true;
                        if(i==0)
                        score +=2;
                        else if(i==1)
                            score ++;
                    else if(i==2)
                        score +=3;
                    else if(i==3)
                        score+=5;
                    }
                }
            }
            for (Bullet bullet : trash) {
                bullets.remove(bullet);
            }
        }

        for(int k=0;k<3;k++) {
            viruses[k].x -= viruses[k].speed;
            if (viruses[k].x + viruses[k].width < 0) {
                int bound = (int) (40 * screenRatioX);
                viruses[k].speed = r.nextInt(bound);

                if (viruses[k].speed < 10 * screenRatioX)
                    viruses[k].speed = (int) (10 * screenRatioX);

                viruses[k].x = screenX;
                viruses[k].y = r.nextInt(screenY - viruses[k].height);

                viruses[k].wasShot=false;
            }

            if (Rect.intersects(viruses[k].getCollisionShape(), hasib.getCollisionShape())) {
                isGameOver = true;
                return;
            }
        }

    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas=getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);
            canvas.drawBitmap(hasib.hasib,hasib.x,hasib.y,paint);
            for(Virus virus:viruses) {
                canvas.drawBitmap(virus.virus, virus.x, virus.y, paint);
            }
            if(isGameOver)
            {
                isPlaying=false;
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;

            }
            for(Bullet bullet : bullets)
            {

                canvas.drawBitmap(bullet.bullet[i],bullet.x,bullet.y,paint);
            }
            canvas.drawText(score + "", screenX / 2f, 164, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private  void sleep(){

        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {

        }
    }
    public void resume() {
        isPlaying=true;
        thread =new Thread(this);
        thread.start();
    }
    public void pause(){
        try {
            isPlaying=false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<screenX/2)
                    hasib.isGoingUp=true;
            break;
            case MotionEvent.ACTION_UP:
                hasib.isGoingUp=false;
                if(event.getX()>screenX/2)
                {
                    bullet.shoot=true;
                    newBullet();
                    i=r.nextInt(4);
                }
            break;
        }
        return true;
    }
    public  void newBullet(){

        Bullet bullet=new Bullet(getResources());
        bullet.x=hasib.x+(hasib.width/2);
        bullet.y=hasib.y+(hasib.height/2);
        bullets.add(bullet);

    }
}
