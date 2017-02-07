package com.example.android.irishpopmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.irishpopmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.ItemClickListener {

    private Context context;
    private ArrayList<Movie> moviesList;
    private String SORT_CRITERIA_TAG = "sortCriteria";
    private String sortCriteria;
    private GridLayoutManager moviesGridLayout;
    private RecyclerView movieRecyclerView;
    private MoviesRecyclerViewAdapter moviesRVAdapter;

    private final static String TAG = "MainActivity";

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
            //get stored sort criteria or use default value if not available
            sortCriteria= savedInstanceState.getString(SORT_CRITERIA_TAG, NetworkUtils.POPULAR);
        }

        moviesGridLayout = new GridLayoutManager(context, 4);

        movieRecyclerView = (RecyclerView)findViewById(R.id.movie_rv);
        movieRecyclerView.setLayoutManager(moviesGridLayout);

        //setting an adapter with empty data set, will be populated in async post execute
        moviesList = new ArrayList<>();
        moviesRVAdapter = new MoviesRecyclerViewAdapter(context, moviesList);
        movieRecyclerView.setAdapter(moviesRVAdapter);

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

    @Override
    public void onItemClick(View view, int position) {

        Class DetailsActivity = DetailsActivity.class;

        Intent startDetailsActivityIntent = new Intent(context, DetailsActivity);

        Movie clickedMovie = moviesRVAdapter.getItem(position);
        startDetailsActivityIntent.putExtra(Movie.PARCABLE_MOVIE_TAG, clickedMovie);

        startActivity(startDetailsActivityIntent);
    }

    private void requestMovieData(String sortCriteria) {

        if (NetworkUtils.isOnline(context)) {

            URL movieApiUrl = NetworkUtils.buildTmdbApiUrl(sortCriteria);
            Log.d(TAG, ".requestMovieData(): tmdb query url: " + movieApiUrl.toString());
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
                Log.e(TAG, ".doInBackground(): Movie db query failed: " + e.getMessage());
                e.printStackTrace();
                // we should do something about it, e.g. retry but not for stage 1
            }
            return movieQueryResults;
        }

        @Override
        protected void onPostExecute(String movieApiResults) {

            ArrayList<Movie> tmpMoviesList = new ArrayList<>();

            Log.d(TAG, ".onPostExecute(): tmdb result: " + movieApiResults);
            if (movieApiResults != null && !movieApiResults.equals("")) {
                MoviesFromJSONResult.parseMovieJSONResult(movieApiResults, tmpMoviesList);
            }
            // Only replace existing list of movies if we got new movies back
            if (tmpMoviesList.size() == 0) {
                Log.e(TAG, ".onPostExecute(): empty movie array returned from parser.");
                makeToast(R.string.failed_loading, Toast.LENGTH_SHORT);
            }
            else {
                moviesList = tmpMoviesList;
                moviesRVAdapter.swapData(moviesList);
            }
    }
}

    private void makeToast(int textId, int duration) {
        String text = getResources().getString(textId);
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
