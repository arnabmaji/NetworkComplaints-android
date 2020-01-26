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
import io.github.arnabmaji19.networkcomplaints.util.KeyboardHider;
import io.github.arnabmaji19.networkcomplaints.util.Session;
import io.github.arnabmaji19.networkcomplaints.util.Validations;
import io.github.arnabmaji19.networkcomplaints.util.WaitDialog;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";

    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberLogInCheckBox;
    private WaitDialog dialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        //links views
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        rememberLogInCheckBox = findViewById(R.id.rememberLogInCheckBox);

//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) actionBar.hide(); //hide action bar

        if (hasUserSavedLogInCredentials()) {
            //if user has saved log in credentials
            //retrieve log in credentials
            String email = sharedPreferences.getString("email", null);
            String password = sharedPreferences.getString("password", null);

            //attempt log in with saved credentials
            attemptLogIn(email, password);

        }

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

        //log in the user and check status
        attemptLogIn(email, password);

    }

    public void signUpUser(View view) {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class)); //start SignUp activity
    }

    private void saveUserCredentials(String email, String password) {
        //Save user's credentials for future use
        sharedPreferences
                .edit()
                .putString("email", email)
                .putString("password", password)
                .apply();
    }

    private boolean hasUserSavedLogInCredentials() {
        return sharedPreferences
                .getString("email", null) != null;
    }

    private void attemptLogIn(final String email, final String password) {
        //create dialog
        dialog = new WaitDialog(this);

        //Log In the user
        LogInAPI logInAPI = new LogInAPI(email, password);
        dialog.show(); //show dialog
        logInAPI.addOnCompleteListener(new LogInAPI.OnCompleteListener() { //set onCompleteListener
            @Override
            public void onComplete(int statusCode, String userId, String username) {
                dialog.hide(); //hide dialog
                String message = "";
                //check status of the server
                if (statusCode == LogInAPI.STATUS_CODE_SUCCESSFUL) {
                    message = "Log In successful!";
                    if (rememberLogInCheckBox.isChecked()) {
                        //if user checks to save log in info
                        saveUserCredentials(email, password);
                    }
                    Session.getInstance().create(userId, username, email);
                    startActivity(new Intent(LogInActivity.this, MainActivity.class)); //start main activity
                    finish(); //finish current activity
                } else if (statusCode == LogInAPI.STATUS_CODE_USER_NOT_REGISTERED) {
                    message = "Email not registered";
                } else if (statusCode == LogInAPI.STATUS_CODE_INCORRECT_PASSWORD) {
                    message = "Incorrect password";
                } else if (statusCode == LogInAPI.STATUS_CODE_FAILED) {
                    message = "Something went wrong!";
                }

                Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
        logInAPI.post(); //send post request
    }
}
