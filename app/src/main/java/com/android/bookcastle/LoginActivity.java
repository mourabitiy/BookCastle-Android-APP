package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
            Log.d("LoginActivity", "onCreate: " + sharedPreferences.getString("username", "NOT STORED"));
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
                        editor.putBoolean("logged", true);
                        editor.putString("username", user);
                        editor.putString("password", pass);
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
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
        });
    }
    public void goToMain(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }


}