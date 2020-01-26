package io.github.arnabmaji19.networkcomplaints;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.arnabmaji19.networkcomplaints.api.LogInAPI;
import io.github.arnabmaji19.networkcomplaints.model.User;
import io.github.arnabmaji19.networkcomplaints.util.KeyboardHider;
import io.github.arnabmaji19.networkcomplaints.util.Validations;
import io.github.arnabmaji19.networkcomplaints.util.WaitDialog;

public class LogInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberLogInCheckBox;
    private WaitDialog dialog;

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

        KeyboardHider.hideKeyboard(LogInActivity.this); //hide the keyboard

        //create dialog
        dialog = new WaitDialog(this);

        //Log In the user
        LogInAPI logInAPI = new LogInAPI(email, password);
        dialog.show(); //show dialog
        logInAPI.addOnCompleteListener(new LogInAPI.OnCompleteListener() { //set onCompleteListener
            @Override
            public void onComplete(User user) {
                dialog.hide(); //hide dialog

            }
        });
        logInAPI.post(); //send post request


    }

    public void signUpUser(View view) {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class)); //start SignUp activity
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
