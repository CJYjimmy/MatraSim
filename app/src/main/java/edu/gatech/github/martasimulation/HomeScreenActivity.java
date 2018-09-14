package edu.gatech.github.martasimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreenActivity extends AppCompatActivity {

    Button mainListButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }
    public void onTap (View v) {
        Intent returnToLast = new Intent(HomeScreenActivity.this, WelcomeActivity.class);
        HomeScreenActivity.this.startActivity(returnToLast);
        Toast myToast = Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_LONG);
        myToast.show();
    }

    public void toSim (View v) {
        Intent toS = new Intent(HomeScreenActivity.this, SimulationPage.class);
        HomeScreenActivity.this.startActivity(toS);
    }
    public void toMainList (View v) {
        Intent toList = new Intent(HomeScreenActivity.this, ListActivity.class);
        startActivity(toList);
    }
    public void toMap (View v) {
        Intent toList = new Intent(HomeScreenActivity.this, MapActivity.class);
        startActivity(toList);
    }
}
