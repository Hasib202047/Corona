package com.example.corona;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {

    boolean shoot=false;
    int x;
    int y;
    int width;
    int height;

    Bitmap bullet[]=new Bitmap[4];
    Bullet(Resources res){
        bullet[0]= BitmapFactory.decodeResource(res,R.drawable.hand);
        bullet[1]= BitmapFactory.decodeResource(res,R.drawable.mask1);
        bullet[2]= BitmapFactory.decodeResource(res,R.drawable.cloths1);
        bullet[3]= BitmapFactory.decodeResource(res,R.drawable.house);


        width=bullet[0].getWidth();


        height=bullet[0].getHeight();


        width/=10;


        height/=10;


        bullet[0]=Bitmap.createScaledBitmap(bullet[0],width,height,false);
        bullet[1]=Bitmap.createScaledBitmap(bullet[1],width,height,false);
        bullet[2]=Bitmap.createScaledBitmap(bullet[2],width,height,false);
        bullet[3]=Bitmap.createScaledBitmap(bullet[3],width,height,false);


    }

    Rect getCollisionShape()
    {
        return new Rect(x,y,x+width,y+height);
    }

}
