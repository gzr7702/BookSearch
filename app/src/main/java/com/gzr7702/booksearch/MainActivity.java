package com.gzr7702.booksearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private ArrayList<Book> mBookArrayList = new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search();
    }

    private void search(){
        final Button searchButton = (Button) findViewById(R.id.search_button);
        TextView searchText = (TextView) findViewById(R.id.search_text);
        final String searchString = searchText.getText().toString();

        searchButton.setOnClickListener(
                new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (!isOnline()) {
                                String message = "Sorry, the internet is unreachable. " + "" +
                                        "Please check you're connection and try again!";
                                Log.v(LOG_TAG, message);
                            } else {
                                FetchSearchResults searchTask = new FetchSearchResults();
                                searchTask.execute(searchString);
                                try {
                                    mBookArrayList = searchTask.get();
                                } catch (InterruptedException e) {
                                    Log.e(LOG_TAG, e.toString());
                                } catch (ExecutionException e) {
                                    Log.e(LOG_TAG, e.toString());
                                }
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
