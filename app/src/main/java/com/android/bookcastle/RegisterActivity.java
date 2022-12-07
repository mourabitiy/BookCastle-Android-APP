package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    EditText username_tv,email,password,repassword;
    ImageButton btnRegister;
    ImageButton btnBack;
    RadioGroup gender;

    UserDatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_tv = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.password_confirm);
        email = findViewById(R.id.email);
        btnRegister = findViewById(R.id.btn_register);
        gender = findViewById(R.id.gender);
        btnBack = findViewById(R.id.btnBack);
        DB = UserDatabaseHelper.getInstance(this);
        User user1 = new User();

        btnRegister.setOnClickListener(v -> {
                String username = username_tv.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String mail = email.getText().toString();
                //get the radio button selected
                gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = findViewById(checkedId);
                        user1.setGender(radioButton.getText().toString());
                    }
                });

                user1.setUsername(username);
                user1.setPassword(pass);
                user1.setEmail(mail);


                if(username.equals("") || pass.equals("") || repass.equals("") || mail.equals("")){
                    Snackbar.make(v, "Please enter all the fields ! ", Snackbar.LENGTH_LONG)
                            //add retry button
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnRegister.performClick();
                                }
                            })
                            .show();
                }else{
                    if(pass.equals(repass) ){
                        Boolean checkUser = DB.checkUsername(username);
                        if(checkUser == false){
                            Boolean insert = DB.addUser(user1);
                            if(insert == true){
                                Snackbar.make(v, "Registered successfully ! ", Snackbar.LENGTH_LONG)
                                        .show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Snackbar.make(v, "Failed to register, please try later again", Snackbar.LENGTH_LONG)
                                        //add retry button
                                        .setAction("Retry", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnRegister.performClick();
                                            }
                                        })
                                        .show();
                            }
                        }else{
                            Snackbar.make(v, "User already exists! please sign in", Snackbar.LENGTH_LONG)
                                    //add retry button
                                    .setAction("Login", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                        }
                                    })
                                    .show();
                        }
                    }else{
                            Snackbar.make(v, "Password not matching", Snackbar.LENGTH_LONG)
                                    //add retry button
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            btnRegister.performClick();
                                        }
                                    })
                                    .show();
                    }
                }
        });

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}