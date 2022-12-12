package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    EditText username1, password1;
    ImageButton btnLogin;
    TextView tvRegister, forgot_password;
    UserDatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username1 = findViewById(R.id.username1);
        password1 = findViewById(R.id.password1);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        User loggedUser = new User();
        DB = UserDatabaseHelper.getInstance(this);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        if(sharedPreferences.getBoolean("logged", false)){
            goToMain(loggedUser);
        }

        btnLogin.setOnClickListener(v -> {
                String user = username1.getText().toString();
                String pass = password1.getText().toString();
                loggedUser.setUsername(user);
                loggedUser.setPassword(pass);



                    if(user.equals("") || pass.equals("")){
                    Snackbar.make(v, "Please enter all the fields ! ", Snackbar.LENGTH_LONG)
                            //add retry button
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnLogin.performClick();
                                }
                            })
                            .show();
                }else{
                    Boolean checkUserPass = DB.checkPassword(user,pass);
                    if(checkUserPass == true){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //get userid from database
                        String id = DB.getUserId(user, pass);
                        editor.putString("user_id", id);
                        editor.putBoolean("logged", true);
                        editor.putString("username", user);
                        editor.putString("password", pass);
                        editor.putString("gender", DB.getGender(user));
                        editor.apply();

                        goToMain(loggedUser);
                    }else{
                       Snackbar.make(v, "Username or password are incorrect ! ", Snackbar.LENGTH_LONG)
                               //add retry button
                               .setAction("Retry", new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       btnLogin.performClick();
                                   }
                               })
                               .show();
                    }
                }
        });
        tvRegister.setOnClickListener(v -> {
                Fade fade = new Fade();
                fade.setDuration(1000);
                getWindow().setExitTransition(fade);
                getWindow().setEnterTransition(fade);
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
        });
    }
    public void goToMain(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }


}