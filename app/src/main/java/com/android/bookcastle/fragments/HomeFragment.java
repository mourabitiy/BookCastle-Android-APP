package com.android.bookcastle.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookcastle.MainActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.adapters.CategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    TextView welcome_msg;
    private CategoryAdapter adapter;
    ArrayList<Category> categories;
    ArrayList <Book>  books;
    SearchView search;

    String username;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        username = username.substring(0, 1).toUpperCase() + username.substring(1);
        welcome_msg = view.findViewById(R.id.welcome_msg);
        welcome_msg.setText("Welcome back, " + username);
        recyclerView = getView().findViewById(R.id.parent_rv);
        search = getView().findViewById(R.id.search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categories = ((MainActivity) getActivity()).getCategories();
        categoryAdapter = new CategoryAdapter(categories, getContext());
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        ((MainActivity) getActivity()).stopShimmer();

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        //display a dialog when the user clicks on the search icon
        search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displaySearchDialog();
            }
        });

    }

}