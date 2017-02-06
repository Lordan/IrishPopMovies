package com.example.android.irishpopmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Harald Schlindwein on 06/02/2017.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> moviesList ;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public MoviesRecyclerViewAdapter(Context context, ArrayList<Movie> moviesList) {
        this.inflater = LayoutInflater.from(context);
        this.moviesList = moviesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movies_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the imageview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = moviesList[position];
        holder.myImageView.setText(animal);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return moviesList.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView MoviesRecyclerViewAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            MoviesRecyclerViewAdapter = (ImageView) itemView.findViewById(R.id.movie_poster_grid);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return moviesList[id];
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
