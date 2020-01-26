package io.github.arnabmaji19.networkcomplaints;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.arnabmaji19.networkcomplaints.api.SignUpAPI;
import io.github.arnabmaji19.networkcomplaints.model.User;
import io.github.arnabmaji19.networkcomplaints.util.Validations;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private CheckBox termsConditionsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //link views
        usernameEditText = findViewById(R.id.username_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        phoneEditText = findViewById(R.id.phone_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edittext);
        termsConditionsCheckBox = findViewById(R.id.termsConditionsCheckBox);
    }

    public void signUpUser(View view) {
        //Get user info
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String contactNumber = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        //validate user info

        if (username.isEmpty() //check if any field is empty
                || email.isEmpty()
                || contactNumber.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Field(s) can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validations.isEmailValid(email)) { //validate email
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validations.isContactNumberValid(contactNumber)) { //validate contact number
            Toast.makeText(this, "Invalid Contact Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validations.isPasswordValid(password)) { //validate password
            passwordEditText.setError("Password must contain at least one digit, " +
                    "one upper case, " +
                    "one lower, " +
                    "one special character " +
                    "and must be 8 to 32 characters long");
            return;
        }

        if (!password.equals(confirmPassword)) { //check if two password matched
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!termsConditionsCheckBox.isChecked()) {
            Toast.makeText(this, "Please agree to our terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        //Sign up the user
        User user = new User(username, email, contactNumber, password); //create the user object
        SignUpAPI signUpAPI = new SignUpAPI(user);
        signUpAPI.addOnCompleteListener(new SignUpAPI.OnCompleteListener() { //adds onCompleteListener
            @Override
            public void onComplete(int statusCode) {
                Log.d(TAG, "onComplete: ");
                //further coding
            }
        });
        signUpAPI.post(); //send post request
    }
}
