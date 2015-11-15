package com.sivulskiy.imagesearchtesttask.activities;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sivulskiy.imagesearchtesttask.R;
import com.sivulskiy.imagesearchtesttask.db.ImageDbHelper;
import com.sivulskiy.imagesearchtesttask.fragments.SearchFragment;
import com.sivulskiy.imagesearchtesttask.fragments.ShowImageFragment;

import retrofit.http.Path;

public class MainActivity extends AppCompatActivity {

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportFragmentManager().findFragmentByTag(SearchFragment.SEARCH_FRAGMENT_TAG) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_activity_content, SearchFragment.newInstance(), SearchFragment.SEARCH_FRAGMENT_TAG)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                openSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSearch() {
        ActionBar action = getSupportActionBar();

        if (isSearchOpened) {
            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            mSearchAction.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_search_white_24dp));

            isSearchOpened = false;
        } else {

            action.setDisplayShowCustomEnabled(true);
            action.setCustomView(R.layout.search_toolbar);
            action.setDisplayShowTitleEnabled(false);

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch);

            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        String text = v.getText().toString();
                        if (!text.equals("")) {
                            findImages(text);
                            return true;
                        } else {
                            v.setError("required");
                        }
                    }
                    return false;
                }
            });

            edtSeach.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);

            mSearchAction.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_close_white_24dp));

            isSearchOpened = true;
        }
    }

    public void findImages(String text) {
        long id = ImageDbHelper.getInstance(getBaseContext()).insertNewSearchText(text);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_content, ShowImageFragment.newInstance(text, id), ShowImageFragment.SHOW_IMAGES_TAG)
                .addToBackStack(null)
                .commit();
    }
}
