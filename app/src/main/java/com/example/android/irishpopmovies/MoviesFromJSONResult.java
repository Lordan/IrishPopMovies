package com.example.android.irishpopmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Harald Schlindwein on 06/02/2017.
 */

public class MoviesFromJSONResult {

    /**
     * Set of ids expected in valid TMDB movie API response
     **/
    private final static String RESULT_ARRAY = "results";
    private final static String ID = "id";
    private final static String POSTER_PATH = "poster_path";
    private final static String PLOT_SYNOPSIS = "overview";
    private final static String TITLE = "original_title";
    private final static String RATING = "vote_average";
    private final static String RELEASE_DATE = "release_date";

    private final static String TAG = "MoviesFromJSONResult";


    /**
     *  This method takes a JSON string, extracts the results array and
     *  parses the elements needed to create new Movie objects.
     *  It adds the new Movie objects to the given ArrayList container.
     * @param jsonResult  The JSON string to parse
     * @param moviesList The container to add all Movie objects successfully parsed
     */
    public static void parseMovieJSONResult( String jsonResult, ArrayList<Movie> moviesList) {

        Movie newMovie;
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(jsonResult);
            Log.d(TAG, ".parseMovieJSONResult(): object parsed from JSON: " + jsonRootObject.toString());

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray(RESULT_ARRAY);

            Log.d(TAG, ".parseMovieJSONResult(): array parsed from JSON has length " + jsonArray.length());

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // parse all elements of JSON object we are interested in
                int id = Integer.parseInt(jsonObject.optString(ID).toString());
                String posterPath = jsonObject.optString(POSTER_PATH).toString();
                String plotSynopsis = jsonObject.optString(PLOT_SYNOPSIS).toString();
                String title = jsonObject.optString(TITLE).toString();
                float rating = Float.parseFloat(jsonObject.optString(RATING).toString());
                String releaseDate = jsonObject.optString(RELEASE_DATE).toString();


                Log.d(TAG, ".parseMovieJSONResult(): movie id " + id + " title " + title);

                newMovie = new Movie(id, title, posterPath, plotSynopsis, rating, releaseDate);
                moviesList.add(newMovie);
            }
            Log.d(TAG, ".parseMovieJSONResult(): JSON parsed to movie array of length" + moviesList.size());
        } catch (JSONException e) {
            Log.e(TAG, ".parseMovieJSONResult(): JSON parsing failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
