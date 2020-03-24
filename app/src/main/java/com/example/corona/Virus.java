package com.example.corona;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Virus {
    public int speed=30;
    public boolean wasShot=true;
    int x=0,y,width,height;
    Bitmap virus;
    Virus(Resources res)
    {
        virus= BitmapFactory.decodeResource(res,R.drawable.coronavirus);
        width=virus.getWidth();
        height=virus.getHeight();
        width /=10;
        height /=10;

        virus=Bitmap.createScaledBitmap(virus,width,height,false);
        y=-height;
    }

    Rect getCollisionShape()
    {
        return new Rect(x,y,x+width/3,y+height/3);
    }
}
