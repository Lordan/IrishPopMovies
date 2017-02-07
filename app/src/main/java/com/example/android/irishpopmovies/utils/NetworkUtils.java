package com.example.android.irishpopmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Harald Schlindwein on 06/02/2017.
 */

public class NetworkUtils {

    // used outside this class as sorting id
    public final static String POPULAR= "popular";
    public final static String TOP_RATED= "top_rated";

    private final static String TMDB_API_BASE_URL =
            "https://api.themoviedb.org/3/movie";

    private final static String TMDB_IMAGE_BASE_URL =
            "http://image.tmdb.org/t/p";

    private final static String PARAM_API_KEY = "api_key";
    private final static String TMDB_API_KEY = BuildConfig.API_KEY;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /**
     * Builds the URL used to query TMDB for movies.
     *
     * @param sortCriteria The sort criteria path.
     * @return The URL to use to query TMDB for movies
     */
    public static URL buildTmdbApiUrl(String sortCriteria) {
        Uri builtUri = Uri.parse(TMDB_API_BASE_URL).buildUpon()
                .appendPath(sortCriteria)
                .appendQueryParameter(PARAM_API_KEY, TMDB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to query TMDB for movie posters.
     *
     * @param sizeCriteria The size criteria path.
     * @param posterPath The id for the poster
     * @return The URL to use to query TMDB for movie posters
     */
    public static URL buildTmdbImageUrl(String sizeCriteria, String posterPath) {
        Uri builtUri = Uri.parse(TMDB_IMAGE_BASE_URL).buildUpon()
                .appendPath(sizeCriteria)
                .appendPath(posterPath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
