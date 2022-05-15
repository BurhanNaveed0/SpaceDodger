package com.example.surfaceviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        scoreText = findViewById(R.id.id_text_score);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);

        scoreText.setText(score);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maskedAction = event.getActionMasked();

        switch(maskedAction) {
            case(MotionEvent.ACTION_DOWN): {
                finish();
                break;
            } default: {
                break;
            }
        }

        return super.onTouchEvent(event);
    }
}