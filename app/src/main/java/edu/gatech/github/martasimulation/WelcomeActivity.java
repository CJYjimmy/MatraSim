package edu.gatech.github.martasimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void onSignInTap (View v) {
        Intent login = new Intent(WelcomeActivity.this, LoginScreen.class);
        WelcomeActivity.this.startActivity(login);
    }
    public void onRegisterTap (View v) {
        Toast myToast = Toast.makeText(getApplicationContext(),"Can't Do That Shit", Toast.LENGTH_LONG);
        myToast.show();
    }
    public void skipToHome (View v) {
        Intent signIn = new Intent(WelcomeActivity.this, SelectDataActivity.class);
        WelcomeActivity.this.startActivity(signIn);

    }
}
