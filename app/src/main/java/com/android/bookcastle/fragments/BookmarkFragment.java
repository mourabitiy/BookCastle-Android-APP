package com.android.bookcastle.fragments;

import android.app.Activity;
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
import com.android.bookcastle.adapters.BookmarkAdapter;
import com.android.bookcastle.adapters.BooksByCategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView bookmarks_rv;
    UserDatabaseHelper DB;
    FloatingActionButton btn_back;
    ArrayList<Book> books;
    TextView bookmark_empty;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(String param1, String param2) {
        BookmarkFragment fragment = new BookmarkFragment();
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
        books = DB.getAllBooks();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        bookmarks_rv = view.findViewById(R.id.bookmarks_rv);
        bookmark_empty = view.findViewById(R.id.bookmark_empty);
        if(books.size() == 0) {
            bookmark_empty.setVisibility(View.VISIBLE);
        }
        else {
            BookmarkAdapter adapter = new BookmarkAdapter(DB.getAllBooks(), getContext());
            bookmarks_rv.setAdapter(adapter);
            bookmarks_rv.setLayoutManager(new LinearLayoutManager(getContext()));
            btn_back = view.findViewById(R.id.back_btn);
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to home fragment
                //call the replace fragment method of the main activity
                ((MainActivity) getActivity()).replaceFragment(new HomeFragment());
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        books = DB.getAllBooks();
        BookmarkAdapter adapter = new BookmarkAdapter(DB.getAllBooks(), getContext());
        bookmarks_rv.setAdapter(adapter);
        bookmarks_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}