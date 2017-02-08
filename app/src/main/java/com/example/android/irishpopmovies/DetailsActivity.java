package com.example.android.irishpopmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.irishpopmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.apache.http.conn.ConnectTimeoutException;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title;
    private TextView rating;
    private TextView releaseDate;
    private TextView plot;
    private Context context;

    //should be dynamic on device width, not for stage 1
    private final static  String SIZE_CRITERIA = "w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = DetailsActivity.this;

        poster = (ImageView) findViewById(R.id.iv_movie_poster);
        title = (TextView) findViewById(R.id.tv_movie_title);
        rating = (TextView) findViewById(R.id.tv_movie_rating);
        releaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        plot = (TextView) findViewById(R.id.tv_movie_plot);

        Intent callerIntent = getIntent();

        if (callerIntent.hasExtra(Movie.PARCABLE_MOVIE_TAG)) {

            Movie movie = callerIntent.getParcelableExtra(Movie.PARCABLE_MOVIE_TAG);

            String posterUrl = NetworkUtils.buildTmdbImageUrlString(SIZE_CRITERIA, movie.posterPath).toString();
            Picasso.with(context).load(posterUrl).into(poster);

            title.setText(movie.title);
            DecimalFormat df = new DecimalFormat("#.#");
            rating.setText(df.format(movie.rating));
            releaseDate.setText(movie.releaseDate);
            plot.setText(movie.plotSynopsis);

        }
    }
}
