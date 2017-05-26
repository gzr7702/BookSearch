package com.gzr7702.booksearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private ArrayList<Book> mBookArrayList = new ArrayList<>();
    private static final int BOOK_LOADER_ID = 1;
    private String mSearchString;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isOnline()) {
                    String message = "Sorry, the internet is unreachable. " + "" +
                            "Please check you're connection and try again!";
                    Log.v(LOG_TAG, message);
                } else {
                    TextView searchText = (TextView) findViewById(R.id.search_text);
                    mSearchString = searchText.getText().toString();
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }
            }
        }
        );

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, mSearchString);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.v(LOG_TAG, books.toString());
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.v(LOG_TAG, "Clear data here");

    }
}
