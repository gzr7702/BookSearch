package com.gzr7702.booksearch;

import android.content.Context;
import android.content.Intent;
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

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BOOK_LOADER_ID = 1;
    private String mSearchString;
    // Var used to track if button is clicked. Otherwise, ResultsActivity keeps starting on its own
    private static boolean isClicked = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isClicked = true;
                if (!isOnline()) {
                    // TODO: add this message to an activity
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
        if (isClicked == true) {
            Log.v(LOG_TAG, books.toString());
            Intent i = new Intent(this, ResultsActivity.class);
            i.putExtra("BookList", (Serializable) books);
            startActivity(i);
            isClicked = false;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.v(LOG_TAG, "Clear data here");
        // TODO: I think this needs to be moved elsewhere
        getLoaderManager().destroyLoader(loader.getId());

    }
}
