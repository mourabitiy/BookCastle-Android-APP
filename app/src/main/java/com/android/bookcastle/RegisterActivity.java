package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.UserDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText username,password,repassword;
    Button btnLogin,btnRegister;
    UserDatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        DB = UserDatabaseHelper.getInstance(this);

        btnRegister.setOnClickListener(v -> {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                User user1 = new User(user,pass);

                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields ! ", Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(repass)){
                        Boolean checkUser = DB.checkUsername(user);
                        if(checkUser == false){
                            Boolean insert = DB.addUser(user1);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "Registed successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "User already exists please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
        });

  btnLogin.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
        });
    }
}