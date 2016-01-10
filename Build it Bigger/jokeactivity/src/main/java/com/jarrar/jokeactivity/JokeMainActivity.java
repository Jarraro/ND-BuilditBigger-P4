package com.jarrar.jokeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeMainActivity extends AppCompatActivity {

    String joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView textView = (TextView) findViewById(R.id.textViewJoke);
        Intent intent = getIntent();
        if (intent != null) {
            joke = intent.getStringExtra("jokeString");
            textView.setText(joke);
        }
    }
}
