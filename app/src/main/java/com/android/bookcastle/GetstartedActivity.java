package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GetstartedActivity extends AppCompatActivity {

    Button signup, signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);

        signup = findViewById(R.id.signup_btn);
        signin = findViewById(R.id.signin_btn);

        signup.setOnClickListener(v -> {
            //with ab animation
            Intent intent = new Intent(GetstartedActivity.this, RegisterActivity.class);
            startActivity(intent);
            //make the animation smooth and fast
overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        signin.setOnClickListener(v -> {
            //with ab animation
            Intent intent = new Intent(GetstartedActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}