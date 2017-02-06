package com.example.android.irishpopmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent callerIntent = getIntent();

        if (callerIntent.hasExtra(Movie.PARCABLE_MOVIE_TAG)) {

            Movie movie = callerIntent.getParcelableExtra(Movie.PARCABLE_MOVIE_TAG);

            // TODO make it happen!
        }
    }
}
