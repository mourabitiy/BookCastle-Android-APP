package com.android.bookcastle.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookcastle.MainActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.adapters.CategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    private CategoryAdapter adapter;
    ArrayList<Category> categories;
    ArrayList <Book>  books;


    TextView welcome_msg;
    String username;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LocalBroadcastManager manager;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //get the shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        this.username = sharedPreferences.getString("username", "");
        //select the recycler view inside the fragment
        recyclerView = getView().findViewById(R.id.parent_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            categories = ((MainActivity)getActivity()).getCategories();
            categoryAdapter = new CategoryAdapter(categories,  getContext());
            recyclerView.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
    }

    private void initBroadCastReceiver() {
        manager = LocalBroadcastManager.getInstance(getContext());
        MyBroadCastReceiver receiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.bookcastle");
        manager.registerReceiver(receiver,filter);
    }


    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //get the categories from the intent
            ArrayList<Category> categories = (ArrayList<Category>) intent.getSerializableExtra("categories");
            adapter.setList(categories);
            Toast.makeText(context, "RECEIVED", Toast.LENGTH_SHORT).show();
        }
    }
}