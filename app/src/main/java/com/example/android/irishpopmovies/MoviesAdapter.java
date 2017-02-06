package com.example.android.irishpopmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;


/**
 * Created by Harald Schlindwein on 06/02/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

        private static final String TAG = MoviesAdapter.class.getSimpleName();

        final private ListItemClickListener mOnClickListener;

        private static int viewHolderCount;

        private int numberItems;

        /**
         * The interface that receives onClick messages.
         */
        public interface ListItemClickListener {
            void onListItemClick(int clickedItemIndex);
        }

        /**
         * Constructor for MoviesAdapter, accepts a number of items to display and the specification
         * for the ListItemClickListener.
         *
         * @param numberOfItems Number of items to display in list
         * @param listener Listener for list item clicks
         */
        public MoviesAdapter(int numberOfItems, ListItemClickListener listener) {
            numberItems = numberOfItems;
            mOnClickListener = listener;
            viewHolderCount = 0;
        }

        /**
         *
         * This gets called when each new ViewHolder is created. This happens when the RecyclerView
         * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
         *
         * @param viewGroup The ViewGroup that these ViewHolders are contained within.
         * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
         *                  can use this viewType integer to provide a different layout. See
         *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
         *                  for more details.
         * @return A new NumberViewHolder that holds the View for each list item
         */
        @Override
        public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            Context context = viewGroup.getContext();
            int layoutIdForListItem = R.layout.movies_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
            MoviesViewHolder viewHolder = new MoviesViewHolder(view);


            viewHolderCount++;
            Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                    + viewHolderCount);
            return viewHolder;
        }

        /**
         * OnBindViewHolder is called by the RecyclerView to display the data at the specified
         * position. In this method, we update the contents of the ViewHolder to display the correct
         * indices in the list for this particular position, using the "position" argument that is conveniently
         * passed into us.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(MoviesViewHolder holder, int position) {
            Log.d(TAG, "#" + position);

        }

        /**
         * This method simply returns the number of items to display. It is used behind the scenes
         * to help layout our Views and for animations.
         *
         * @return The number of items available
         */
        @Override
        public int getItemCount() {
            return numberItems;
        }


        /**
         * Cache of the children views for a list item.
         */
        class MoviesViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            /**
             * Constructor for the ViewHolder, sets an onClickListener
             * @param itemView The View that you inflated in
             *                 {@link MoviesAdapter#onCreateViewHolder(ViewGroup, int)}
             */
            public MoviesViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);
            }

            /**
             * Called whenever a user clicks on an item in the list.
             * @param v The View that was clicked
             */
            @Override
            public void onClick(View v) {
                int clickedPosition = getAdapterPosition();
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }