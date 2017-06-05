package com.gzr7702.booksearch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    // Var used to track if button is clicked. Otherwise, ResultsActivity keeps starting on its own
    //private static boolean isClicked = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //isClicked = true;
                if (!isOnline()) {
                    // TODO: add this to strings file
                    String message = getString(R.string.internet_unavailable_message);
                    Log.v(LOG_TAG, message);
                } else {
                    TextView searchText = (TextView) findViewById(R.id.search_text);
                    String searchString = searchText.getText().toString();
                    Intent i = new Intent(MainActivity.this, ResultsActivity.class);
                    i.putExtra("SearchString", searchString);
                    startActivity(i);

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


}
