package com.android.bookcastle.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bookcastle.MainActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.adapters.CategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;
    ArrayList <Book>  PopularBooks;

    RequestQueue requestQueue;

    TextView welcome_msg;
    String username;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        PopularBooks = new ArrayList<Book>();
        requestQueue = Volley.newRequestQueue(getContext());

        if(((MainActivity)getActivity()).getCategories() == null){
            parseJson();
        }else{
            //if yes, get them from the main activity
            categories = ((MainActivity)getActivity()).getCategories();
            categoryAdapter = new CategoryAdapter(categories,  getContext());
            recyclerView.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
        }
    }

    private void parseJson() {
        String url = "https://www.googleapis.com/books/v1/volumes?q=subject:android&maxResults=40";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("items");
                //display size of json array using toast
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject book = jsonArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");
                    String image = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");
                    //author is an array
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    String author = authors.getString(0);
                    Book book1 = new Book(title, author, image);
                    PopularBooks.add(book1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                categories = new ArrayList<>();
                categories.add(new Category("Popular", PopularBooks));
                categoryAdapter = new CategoryAdapter(categories,  getContext());
                recyclerView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }
        }, error -> {
            Snackbar.make(recyclerView, "Error fetching data", Snackbar.LENGTH_SHORT).show();
        });

        requestQueue.add(request);
    }
    @Override
    public void onStart() {
        super.onStart();

        welcome_msg = getView().findViewById(R.id.welcome_msg);
        welcome_msg.setText("Welcome back, " + username.substring(0, 1).toUpperCase() + username.substring(1));
    }


    @Override
    public void onDestroy() {
        //override on destroy to save the categories in homeactivity
        super.onDestroy();
        ((MainActivity) getActivity()).setCategories(categories);
    }

}