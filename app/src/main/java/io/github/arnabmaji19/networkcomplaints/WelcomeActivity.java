package io.github.arnabmaji19.networkcomplaints;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide(); //hide action bar
    }

    public void logInUser(View view) {
        //navigate user to Log In page
        startActivity(new Intent(WelcomeActivity.this, LogInActivity.class)); //start LogInActivity
        finish(); //finish the current activity
    }

    public void signUpUser(View view) {

    }

    public void switchOfflineMode(View view) {

    }
}
