package com.gzr7702.booksearch;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.format.Time;
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
import java.util.Vector;

/**
 * Fetch the results of a query based on Author, Title, Description
 */

public class FetchSearchResults extends AsyncTask<String, Void, Void>{
    private final String LOG_TAG = FetchSearchResults.class.getSimpleName();
    @Override
    protected Void doInBackground(String... params) {

        // Nothing to look up.
        if (params.length == 0) {
            return null;
        }
        String bookQuery = params[0];

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String resultJsonStr = null;

        try {
            final String BASE_URL =
                    "https://www.googleapis.com/books/v1/volumes";
            final String QUERY_PARAM = "q";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, params[0])
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
            getDataFromJson(resultJsonStr, bookQuery);
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
        return null;
    }

    private void getDataFromJson(String jsonString, String query) throws JSONException {

        try {
            JSONObject bookJson = new JSONObject(jsonString);
            JSONArray itemsArray = bookJson.getJSONArray("items");

            for(int i = 0; i < itemsArray.length(); i++) {
                // These are the values that will be collected.
                String title;
                ArrayList<String> authors;
                int coverImage;

                JSONObject item = itemsArray.getJSONObject(i);
                JSONObject volumeInfoObj = item.getJSONObject("volumeInfo");

                String bookTitle = volumeInfoObj.getString("title");

                Log.v(LOG_TAG, bookTitle);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
