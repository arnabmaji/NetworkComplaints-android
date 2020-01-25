package io.github.arnabmaji19.networkcomplaints;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.arnabmaji19.networkcomplaints.util.Validations;

public class LogInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberLogInCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //links views
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        rememberLogInCheckBox = findViewById(R.id.rememberLogInCheckBox);

//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) actionBar.hide(); //hide action bar
    }

    public void logInExistingUser(View view) {
        //get user credentials
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //check if fields are not empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Field(s) can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //check if email is in valid format
        if (!Validations.isEmailValid(email)) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        //check if password is in valid format
        if (!Validations.isPasswordValid(password)) {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Log In the user



    }

    private void saveUserCredentials(String email, String password) {
        //Save user's credentials for future use
        SharedPreferences preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        preferences
                .edit()
                .putString("email", email)
                .putString("password", password)
                .apply();
    }
}
