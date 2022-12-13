package com.android.bookcastle.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookcastle.ChangePassActivity;
import com.android.bookcastle.EditProfileActivity;
import com.android.bookcastle.GetstartedActivity;
import com.android.bookcastle.MainActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView change_password,edit_profile, tvName, tvEmail;
    ImageView back_btn2;
    UserDatabaseHelper DB;
    LinearLayout favorites;
    Button logout_btn;
    CircularImageView profile_image;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DB = UserDatabaseHelper.getInstance(getContext());
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        change_password = view.findViewById(R.id.change_password);
        logout_btn = view.findViewById(R.id.logout_btn);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        back_btn2 = view.findViewById(R.id.back_btn2);
        profile_image = view.findViewById(R.id.profile_pic);
        edit_profile = view.findViewById(R.id.edit_profile);
        back_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to main activity
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                fragmentTransaction.commit();
                MainActivity.getNavigation().setSelectedItemId(R.id.nav_home);
    }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePassActivity.class);
                startActivity(intent);
            }
        });
        favorites = view.findViewById(R.id.favorites);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new BookmarkFragment()).commit();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear all shared preferences
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getContext(), GetstartedActivity.class);
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        String id = sharedPreferences.getString("user_id", "");
        User user = new User();
        user = DB.getUserById(id);
        if (user != null) {
            tvName.setText(user.getUsername());
            tvEmail.setText(user.getEmail());
            Log.d("GENDER", user.getGender());
            if(user.getGender().equals("male")){
                profile_image.setImageResource(R.drawable.pp);
            }
            else{
                profile_image.setImageResource(R.drawable.ppf);
            }
        }
        User finalUser = user;
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open new activity to edit profile
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("user", finalUser);
                startActivity(intent);
            }
        });

        return view;

    }
}