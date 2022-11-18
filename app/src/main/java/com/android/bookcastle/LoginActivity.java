package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookcastle.utils.UserDatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText username1, password1;
    Button btnLogin;
    TextView tvRegister;
    UserDatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username1 = findViewById(R.id.username1);
        password1 = findViewById(R.id.password1);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        DB = UserDatabaseHelper.getInstance(this);

        btnLogin.setOnClickListener(v -> {
                String user = username1.getText().toString();
                String pass = password1.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkUserPass = DB.checkPassword(user,pass);
                    if(checkUserPass == true){
                        Toast.makeText(LoginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
        });
        tvRegister.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
        });
    }

}