package com.example.android.irishpopmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.irishpopmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Harald Schlindwein on 06/02/2017.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> moviesList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private Context context;

    //should be dynamic on device width, not for stage 1
    private final static  String SIZE_CRITERIA = "w185";

    public MoviesRecyclerViewAdapter(Context context, ArrayList<Movie> moviesList) {
        this.inflater = LayoutInflater.from(context);
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieView = inflater.inflate(R.layout.movies_list_item, parent, false);
        return new ViewHolder(movieView);
    }

    // binds the data to the imageview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie newMovie = moviesList.get(position);
        holder.viewHolderMovie = newMovie;
        String posterUrl = NetworkUtils.buildTmdbImageUrl(SIZE_CRITERIA, newMovie.posterPath).toString();
        Picasso.with(context).load(posterUrl).into(holder.movieView);
    }

    // total number of cells
    @Override
    public int getItemCount() {

        return moviesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView movieView;
        public Movie viewHolderMovie;

        public ViewHolder(View itemView) {
            super(itemView);
            movieView = (ImageView) itemView.findViewById(R.id.movie_poster_grid);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Movie getItem(int idx) {
        return moviesList.get(idx);
    }

    /**
     * This method swaps the existing data set with the given one
     * and updated the observer of this adapter
     * @param newMovies The new data set
     */
    public void swapData(ArrayList<Movie> newMovies) {
        moviesList = newMovies;
        notifyDataSetChanged();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
