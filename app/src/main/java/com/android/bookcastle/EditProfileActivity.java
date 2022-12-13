package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class EditProfileActivity extends AppCompatActivity {

    EditText username, email;
    Button update_btn;
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        update_btn = findViewById(R.id.update_btn);
        back_btn = findViewById(R.id.back_btn);
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        back_btn.setOnClickListener(v -> {
           onBackPressed();
        });

        update_btn.setOnClickListener(v -> {
            UserDatabaseHelper DB = UserDatabaseHelper.getInstance(this);
            String username = this.username.getText().toString();
            String email = this.email.getText().toString();
            DB.updateUser(user.getId(), username, email);
            Snackbar.make(v, "Profile updated successfully", Snackbar.LENGTH_LONG).show();
        });


    }
}