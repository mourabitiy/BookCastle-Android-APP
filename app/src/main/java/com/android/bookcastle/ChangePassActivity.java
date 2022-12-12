package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.bookcastle.utils.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class ChangePassActivity extends AppCompatActivity {
    ImageView imgBack ;
    EditText oldPass, newPass, confirmPass;
    Button ConfirmPassBtn;
    UserDatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        DB = UserDatabaseHelper.getInstance(this);

        imgBack = (ImageView) findViewById(R.id.back_btn);
        oldPass = (EditText) findViewById(R.id.oldPass);
        newPass = (EditText) findViewById(R.id.newPass);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        ConfirmPassBtn = (Button) findViewById(R.id.ConfirmPassBtn);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ConfirmPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //change password in DB
                //get id from shared pref
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                String id = sharedPreferences.getString("user_id", "");
                String oldPassword = oldPass.getText().toString();
                String newPassword = newPass.getText().toString();
                String confirmPassword = confirmPass.getText().toString();
                if(oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")){
                    oldPass.setError("Please enter all the fields");
                    newPass.setError("Please enter all the fields");
                    confirmPass.setError("Please enter all the fields");
                }
                else{
                    if(newPassword.equals(confirmPassword)){
                        Boolean checkPass = DB.checkPass(id, oldPassword);
                        if(checkPass==true){
                            Boolean updatePass = DB.updatePass(id, newPassword);
                            if(updatePass==true){
                                Snackbar.make(view, "Password changed successfully", Snackbar.LENGTH_LONG)
                                        .setAction("Go back",
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        onBackPressed();
                                                    }
                                                }).show();
                            }
                        }
                        else{
                            oldPass.setError("Wrong Password");
                        }
                    }
                    else{
                        newPass.setError("Password not matching");
                        confirmPass.setError("Password not matching");
                    }
                }
            }
        });


    }
}