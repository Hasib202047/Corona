package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton play,instuction,score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        play=(ImageButton) findViewById(R.id.Play);
        instuction=(ImageButton)findViewById(R.id.Instruction);
        score=(ImageButton)findViewById(R.id.Score);
        play.setOnClickListener(this);
        instuction.setOnClickListener(this);
        score.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Play:
                startActivity(new Intent(MainActivity.this,Game_Activity.class));
                break;
            case R.id.Score:
                startActivity(new Intent(MainActivity.this,Score_Activity.class));
                break;
            case R.id.Instruction :
                startActivity(new Intent(MainActivity.this,Insruction_Activity.class));
                break;


        }
    }
}
