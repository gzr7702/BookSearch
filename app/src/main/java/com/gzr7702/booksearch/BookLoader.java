package com.gzr7702.booksearch;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetch the results of a query based on Author, Title, Publish Date
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private final String LOG_TAG = BookLoader.class.getSimpleName();
    private String mSearchWord;

    public BookLoader (Context context, String searchWord) {
        super(context);
        mSearchWord = searchWord;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        // Nothing to look up.
        if (mSearchWord == null) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        List<Book> bookList = new ArrayList<>();

        // Will contain the raw JSON response as a string.
        String resultJsonStr = null;

        try {
            final String BASE_URL =
                    "https://www.googleapis.com/books/v1/volumes";
            final String QUERY_PARAM = "q";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, mSearchWord)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // line end for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            resultJsonStr = buffer.toString();
            bookList = getDataFromJson(resultJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return bookList;
    }

    private List<Book> getDataFromJson(String jsonString) throws JSONException {

        List<Book> bookList = new ArrayList<>();

        try {
            JSONObject bookJson = new JSONObject(jsonString);
            JSONArray itemsArray = bookJson.getJSONArray("items");

            for(int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                JSONObject volumeInfoObj = item.getJSONObject("volumeInfo");

                String title = volumeInfoObj.getString("title");
                String publishDate = volumeInfoObj.getString("publishedDate");

                JSONArray authorsArray = volumeInfoObj.getJSONArray("authors");
                StringBuilder authors = new StringBuilder();
                for (int j = 0; j < authorsArray.length(); j++) {
                    if (j >= 1) {
                        authors.append(", ");
                    }
                    authors.append(authorsArray.get(j));
                }

                bookList.add(new Book(title, authors.toString(), publishDate));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return bookList;
    }

}
