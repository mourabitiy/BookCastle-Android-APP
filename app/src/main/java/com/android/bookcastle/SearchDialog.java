package com.android.bookcastle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

//SearchDialog class
public class SearchDialog extends DialogFragment {

    //onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_dialog, container, false);
        return view;
    }

    //onViewCreated method
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get the string from the search dialog
        String search = getArguments().getString("search");
        //set the string to the search dialog
        ((MainActivity) getActivity()).setSearch(search);
    }

    //onResume method
    @Override
    public void onResume() {
        super.onResume();
        //set the width and height of the search dialog
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //newInstance method
    public static SearchDialog newInstance(String search) {
        SearchDialog f = new SearchDialog();
        Bundle args = new Bundle();
        args.putString("search", search);
        f.setArguments(args);
        return f;
    }


}