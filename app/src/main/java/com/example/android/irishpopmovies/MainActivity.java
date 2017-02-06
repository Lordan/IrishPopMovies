package com.example.android.irishpopmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.irishpopmovies.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.irishpopmovies.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ArrayList<Movie> moviesList:
    private String SORT_CRITERIA_TAG = "sortCriteria";
    private String sortCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        if(savedInstanceState == null) {
            // no saved instance state, default to 'popular'
            sortCriteria = NetworkUtils.POPULAR;
        }
        else {
            //get stored sort criteria or use default vaulue if not available
            sortCriteria= savedInstanceState.getString(SORT_CRITERIA_TAG, NetworkUtils.POPULAR);
        }

        requestMovieData(sortCriteria);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SORT_CRITERIA_TAG, sortCriteria);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedItemId = item.getItemId();
        switch (clickedItemId) {
            case R.id.action_popular:
                requestMovieData(NetworkUtils.POPULAR);
                break;
            case R.id.action_top_rated:
                requestMovieData(NetworkUtils.TOP_RATED);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO change to actual container
    /*private TextView movieTextView;

    movieTextView.setOnClickListener(new OnClickListener() {

        *//**
         * The onClick method is triggered when the view (TODO) is selected
         *
         * @param v The view that is clicked. In this case, it's TODO.
         *//*
        @Override
        public void onClick (View v){

            Class DetailsActivity = DetailsActivity.class;

            Intent startDetailsActivityIntent = new Intent(context, DetailsActivity);

            //TODO add movie object
            startDetailsActivityIntent.putExtra(Movie.PARCABLE_MOVIE_TAG, textEntered);

            startActivity(startDetailsActivityIntent);
        }
    }*/

    private void requestMovieData(String sortCriteria) {

        if (NetworkUtils.isOnline(context)) {

            URL movieApiUrl = NetworkUtils.buildTmdbApiUrl(sortCriteria);
            new MovieDbApiQueryTask().execute(movieApiUrl);
        }
        else {
            // no connection no fun, let that toast sink in (_LONG)
            makeToast(R.string.no_connection, Toast.LENGTH_LONG);
            // we should do something about it, e.g. retry but not for stage 1
        }
    }

    private class MovieDbApiQueryTask extends AsyncTask<URL, Void, String> {
        
        @Override
        protected String doInBackground(URL... params) {
            URL queryUrl = params[0];
            String movieQueryResults = null;
            try {
                movieQueryResults = NetworkUtils.getResponseFromHttpUrl(queryUrl);
            } catch (IOException e) {
                // that can't be good, let us inform the user with a nice toast
                makeToast(R.string.failed_loading, Toast.LENGTH_SHORT);
                e.printStackTrace();
                // we should do something about it, e.g. retry but not for stage 1
            }
            return movieQueryResults;
        }

        @Override
        protected void onPostExecute(String movieApiResults) {
            if (movieApiResults != null && !movieApiResults.equals("")) {
                parseMovieJSONResult(movieApiResults);
            }
            else {
                makeToast(R.string.failed_loading, Toast.LENGTH_SHORT);
            }
    }

    private void parseMovieJSONResult( String jsonResult) {

        try {
            // Create the root JSONObject from the JSON string.
            JSONObject  jsonRootObject = new JSONObject(jsonResult);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("CHANGEME");//TODO put in id for array of movies

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //TODO set correct ids
                int id = Integer.parseInt(jsonObject.optString("CHANGEME").toString());
                String name = jsonObject.optString("CHANGEME").toString();
                float salary = Float.parseFloat(jsonObject.optString("CHANGEME").toString());
                //make movie objects and put them in an arraylist
            }
        } catch (JSONException e) {
            makeToast(R.string.failed_loading, Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

}

    private void makeToast(int textId, int duration) {
        String text = getResources().getString(textId);
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
