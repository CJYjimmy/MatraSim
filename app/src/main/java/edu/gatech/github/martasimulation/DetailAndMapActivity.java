package edu.gatech.github.martasimulation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class DetailAndMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_and_map);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        myMap canvas = new myMap(this);
        canvas.setBackgroundColor(Color.WHITE);
        setContentView(canvas);
        layout.addView(canvas);

    }
}
