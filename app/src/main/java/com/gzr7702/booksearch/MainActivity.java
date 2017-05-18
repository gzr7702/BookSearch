package com.gzr7702.booksearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search_button);
        TextView searchText = (TextView) findViewById(R.id.search_text);
        final String searchString = searchText.getText().toString();

        searchButton.setOnClickListener(new View.OnClickListener() {
             @Override
                 public void onClick(View view) {
                    FetchSearchResults searchTask = new FetchSearchResults();
                    searchTask.execute(searchString);
                 }
             }
        );
    }
}
