package com.android.bookcastle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bookcastle.databinding.ActivityMainBinding;
import com.android.bookcastle.factories.BookFactory;
import com.android.bookcastle.fragments.BookmarkFragment;
import com.android.bookcastle.fragments.HomeFragment;
import com.android.bookcastle.fragments.SettingsFragment;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.ECategories;
import com.android.bookcastle.utils.NetworkChangeListener;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    ArrayList<Category> categories;
    ArrayList<Book> books;
    BookFactory bookFactory;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ShimmerFrameLayout shimmerFrameLayout;
    MaterialAlertDialogBuilder builder;
    UserDatabaseHelper DB;
    private static BottomNavigationView bottomNavigationView;

    public static BottomNavigationView getNavigation() {
        return bottomNavigationView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Binding the layout with the activity
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        DB = UserDatabaseHelper.getInstance(this);
        setContentView(binding.getRoot());
            categories = new ArrayList<>();
        bookFactory = new BookFactory();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        hideBottomNavigation();
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        GetBooksAsync getBooksAsync = new GetBooksAsync();
        getBooksAsync.execute();
        //replaceFragment(new HomeFragment());


        binding.bottomNavigation.setOnItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_saved:
                    replaceFragment(new BookmarkFragment());
                    break;
                case R.id.nav_settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    public void hideBottomNavigation(){
        binding.bottomNavigation.setVisibility(View.GONE);
    }
    public void showBottomNavigation(){
        binding.bottomNavigation.setVisibility(View.VISIBLE);
        binding.bottomNavigation.setAlpha(0f);
        binding.bottomNavigation.animate().alpha(1f).setDuration(500);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }


    public void setSearch(String search) {
        Intent intent = new Intent("search");
        intent.putExtra("search", search);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void displayCategoryActivity(Category category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    public void displayUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id", "");
        User loggedUser = DB.getUserById(user_id);

        builder = new MaterialAlertDialogBuilder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_bottom_sheet, null);
        TextView username2 = view.findViewById(R.id.username);
        TextView usermail = view.findViewById(R.id.usermail);
        ImageView userImage = view.findViewById(R.id.user_profile);
        if(loggedUser.getGender().equals("male")){
            userImage.setImageResource(R.drawable.pp);
        }
        else{
            userImage.setImageResource(R.drawable.ppf);
        }
        LinearProgressIndicator progressindicator = view.findViewById(R.id.progress_bar);
        TextView progress = view.findViewById(R.id.progress_text);
        Button settings_btn = view.findViewById(R.id.settings_btn);
        username2.setText(loggedUser.getUsername().substring(0, 1).toUpperCase() + loggedUser.getUsername().substring(1));
        usermail.setText(loggedUser.getEmail());
       progressindicator.setProgress(loggedUser.getDailyReadingGoal());
        progress.setText("You have read " + loggedUser.getDailyReadingGoal() + " min today");

        builder.setView(view);
        AlertDialog dialog = builder.show();
        settings_btn.setOnClickListener(v -> {
            replaceFragment(new SettingsFragment());
            dialog.dismiss();
        });

    }


    class GetBooksAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            for (ECategories category : ECategories.values()) {
                try {
                    categories.add(new Category(category.toString(), bookFactory.getBooks(category, -1)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent("com.android.bookcastle");
            intent.putExtra("categories", categories);
            replaceFragment(new HomeFragment());

        }
    }

    public void stopShimmer(){
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        showBottomNavigation();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}