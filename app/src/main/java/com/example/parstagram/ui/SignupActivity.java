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
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Only visible when the user clicks sign up btn
        binding.tvErrorSignUp.setVisibility(View.GONE);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.pwdSignUp.getText().toString();
                String username = binding.username.getText().toString();

                if (password == null || username == null || password == "" || username == "") {
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
                    goToHomeActivity();
                } else {
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