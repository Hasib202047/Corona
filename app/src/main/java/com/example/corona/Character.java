package com.example.corona;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.corona.GameView.screenRatioX;
import static com.example.corona.GameView.screenRatioY;

public class Character {


    public boolean isGoingUp=false;
    int x,y,width,height,wingCounter=0;
    Bitmap hasib;
    Character(int screenY, Resources res)
    {
        hasib= BitmapFactory.decodeResource(res,R.drawable.hasib);
        width=hasib.getWidth();
        height=hasib.getHeight();

        width /=12;
        height /=12;

       // width *=(int)screenRatioX;
       // height *=(int)screenRatioY;

        hasib = Bitmap.createScaledBitmap(hasib,width,height,false);
        y=screenY/2;
        x=(int)(64*screenRatioX);

    }

    Rect getCollisionShape()
    {
        return new Rect(x,y,x+width/3,y+height/3);
    }


}
