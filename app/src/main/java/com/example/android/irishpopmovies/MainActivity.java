package com.example.android.irishpopmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.irishpopmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import static com.example.android.irishpopmovies.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        requestMovieData(NetworkUtils.POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
                // TODO do something with it
            }
        }
    }

    private void makeToast(int textId, int duration) {
        String text = getResources().getString(textId);
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
