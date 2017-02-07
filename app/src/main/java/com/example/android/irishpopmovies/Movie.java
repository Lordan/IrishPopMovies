package com.example.android.irishpopmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Harald Schlindwein on 05/02/2017.
 */


public class Movie implements Parcelable {


    public final static String PARCABLE_MOVIE_TAG = "movieTag";

    int id;
    String title;
    String posterPath;
    String plotSynopsis;
    float rating;
    //for stage 1 we leave that, should use SimpleDateFormat to localise the date
    String releaseDate;

    public Movie(int id, String title, String posterPath, String plotSynopsis, float rating, String releaseDateString) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.plotSynopsis = plotSynopsis;
        this.rating = rating;
        this.releaseDate = releaseDateString;
    }

    public Movie(Parcel in){
        String[] data= new String[6];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.title = data[1];
        this.posterPath = data[2];
        this.plotSynopsis = data[3];
        this.rating = Float.parseFloat(data[4]);
        this.releaseDate = data[5];
    }
    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{String.valueOf(this.id), this.title, this.posterPath, this.plotSynopsis, String.valueOf(this.rating), this.releaseDate});
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {

            return new Movie(source);  //using parcelable constructor
        }

        @Override
        public Movie[] newArray(int size) {

            return new Movie[size];
        }
    };

}
