package com.gzr7702.booksearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rob on 5/29/17.
 */

public class ResultsActivity extends AppCompatActivity{
    ArrayList<Book> mBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mBooks = (ArrayList<Book>) bundle.getSerializable("BookList");
        ArrayAdapter adapter = new BookAdapter(this, mBooks);
        final ListView listView = (ListView) findViewById(R.id.book_list);
        listView.setAdapter(adapter);
    }
}
