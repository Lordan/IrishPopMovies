package com.example.android.irishpopmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Harald Schlindwein on 05/02/2017.
 */


public class Movie implements Parcelable {


    public final static String PARCABLE_MOVIE_TAG = "movieTag";

    String title;
    String posterUrl;
    String plotSynopsis;
    float rating;
    //for stage 1 we leave that, should use SimpleDateFormat to localise the date
    String releaseDate;

    public Movie(String title, String posterUrl, String plotSynopsis, float rating, String releaseDateString) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.plotSynopsis = plotSynopsis;
        this.rating = rating;
        this.releaseDate = releaseDateString;
    }

    public Movie(Parcel in){
        String[] data= new String[3];

        in.readStringArray(data);
        this.title = data[0];
        this.posterUrl = data[1];
        this.plotSynopsis = data[2];
        this.rating = Float.parseFloat(data[3]);
        this.releaseDate = data[4];
    }
    @Override
    public int describeContents() {
// TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
// TODO Auto-generated method stub

        dest.writeStringArray(new String[]{this.title, this.posterUrl, this.plotSynopsis, String.valueOf(this.rating), this.releaseDate});
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
// TODO Auto-generated method stub
            return new Movie(source);  //using parcelable constructor
        }

        @Override
        public Movie[] newArray(int size) {
// TODO Auto-generated method stub
            return new Movie[size];
        }
    };

}
