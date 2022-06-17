package com.example.parstagram.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.parstagram.Models.User;
import com.example.parstagram.databinding.ActivitySignupBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    public static String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use view binding
        binding = ActivitySignupBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        //Only visible when the user clicks sign up btn
        binding.tvErrorSignUp.setVisibility(View.GONE);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String email = binding.email.getText().toString();
//                String name = binding.name.getText().toString();
                String password = binding.pwdSignUp.getText().toString();
                String username = binding.username.getText().toString();

                if (password == null || username == null || password == "" || username == "") {
                    //we can notify the user the problem if we want
                    binding.tvErrorSignUp.setVisibility(View.VISIBLE);
                    return;

                }
                SignUp(username, password);
            }
        });
    }

    private void SignUp(String username, String password) {

        // Create the ParseUser
        User user = new User();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.i(TAG, "it worked!");
                    goToHomeActivity();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    Log.e(TAG, "Sign up not successful!", e);
                    binding.tvErrorSignUp.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void goToHomeActivity() {
        Intent i = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}