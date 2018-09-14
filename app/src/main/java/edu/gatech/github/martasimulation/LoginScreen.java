package edu.gatech.github.martasimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mEmailView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
    }

    public void onCancel (View v) {
        Intent returnToLast = new Intent(LoginScreen.this, WelcomeActivity.class);
        LoginScreen.this.startActivity(returnToLast);
    }

    public void signInTask (View v) {
        String username = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (username.equals("user") && password.equals("pass")) {
            Intent signIn = new Intent(LoginScreen.this, SelectDataActivity.class);
            LoginScreen.this.startActivity(signIn);
        } else {
            Toast myToast = Toast.makeText(getApplicationContext(),"Invalid username or password", Toast.LENGTH_LONG);
            myToast.show();
        }
    }

}
