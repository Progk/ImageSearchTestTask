package com.sivulskiy.imagesearchtesttask.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sivulskiy.imagesearchtesttask.R;
import com.sivulskiy.imagesearchtesttask.db.ImageDbHelper;

public class SearchFragment extends Fragment {

    public final static String SEARCH_FRAGMENT_TAG = "fragment_search";

    private EditText mSearchTextView;
    private Button mSearchButton;


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        mSearchTextView = (EditText) view.findViewById(R.id.search_fragment_search_text_view);
        mSearchButton = (Button) view.findViewById(R.id.search_fragment_search_btn);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mSearchTextView.getText().toString();
                if (text.equals("")) {
                    mSearchTextView.setError("required");
                } else {
                    long id = ImageDbHelper.getInstance(getContext()).insertNewSearchText(text);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_activity_content, ShowImageFragment.newInstance(text, id), ShowImageFragment.SHOW_IMAGES_TAG)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }
}
