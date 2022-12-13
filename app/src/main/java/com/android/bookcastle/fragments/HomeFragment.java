package com.android.bookcastle.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookcastle.BookDetailActivity;
import com.android.bookcastle.MainActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.adapters.CategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.UserDatabaseHelper;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    TextView welcome_msg;
    private CategoryAdapter adapter;
    ArrayList<Category> categories;
    ArrayList <Book>  books;
    ImageView user_profile;
    String username;
    SearchView search;
    UserDatabaseHelper DB;

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
        DB = UserDatabaseHelper.getInstance(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id", "");
        User user = DB.getUserById(sharedPreferences.getString("user_id", ""));

        username = user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1);
        welcome_msg = view.findViewById(R.id.welcome_msg);
        welcome_msg.setText("Welcome back, " + username);
        recyclerView = getView().findViewById(R.id.parent_rv);
        user_profile = getView().findViewById(R.id.user_profile);
        if(user.getGender().equals("male")){
            user_profile.setImageResource(R.drawable.pp);
        }
        else{
            user_profile.setImageResource(R.drawable.ppf);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categories = ((MainActivity) getActivity()).getCategories();
        categoryAdapter = new CategoryAdapter(categories, getContext());
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        ((MainActivity) getActivity()).stopShimmer();


        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayUserProfile();
            }
        });
        configureSearchView();

    }

    private void configureSearchView() {
        ArrayList<Book> books = new ArrayList<>();
        categories.stream().forEach(category -> {
            books.addAll(category.getBooks());
        });

        search = getView().findViewById(R.id.search);
        search.setQueryHint("Search for a book");
        String[] projections = new String[]{"_id", "title", "author"};
        MatrixCursor cursor = new MatrixCursor(projections);
        books.stream().forEach(book -> {
            cursor.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor()});
        });

        String[] from = new String[]{"title", "author"};
        int[] to = new int[]{R.id.text1, R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(),  R.layout.search_item, cursor, from, to, 0);
        search.setSuggestionsAdapter(adapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), "Searching for " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 0){
                    ArrayList<Book> onChangeBooks = new ArrayList<>();
                    books.stream().forEach(book -> {
                        if(book.getTitle().toLowerCase().contains(newText.toLowerCase())){
                            onChangeBooks.add(book);
                        }
                    });
                    MatrixCursor cursor1 = new MatrixCursor(projections);
                    onChangeBooks.stream().forEach(book -> {
                        cursor1.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor()});
                    });
                    search.setSuggestionsAdapter(new SimpleCursorAdapter(getContext(),  R.layout.search_item, cursor1, from, to, 0));
                }
                return false;
            }

        });




        search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Intent intent = new Intent(getContext(), BookDetailActivity.class);
                intent.putExtra("book", books.get(position));
                startActivity(intent);
                return false;
            }
        });




    }


}