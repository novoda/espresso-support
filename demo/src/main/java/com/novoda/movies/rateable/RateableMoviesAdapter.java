package com.novoda.movies.rateable;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

class RateableMoviesAdapter extends RecyclerView.Adapter<RateableMovieViewHolder> {

    private List<RateableMovieViewModel> viewModels;

    RateableMoviesAdapter() {
        super.setHasStableIds(true);
    }

    void update(List<RateableMovieViewModel> viewModels) {
        this.viewModels = viewModels;
        notifyDataSetChanged();
    }

    @Override
    public RateableMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RateableMovieViewHolder.inflate(parent);
    }

    @Override
    public void onBindViewHolder(RateableMovieViewHolder holder, int position) {
        holder.bind(viewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return viewModels == null ? 0 : viewModels.size();
    }

    @Override
    public long getItemId(int position) {
        return viewModels.get(position).id();
    }
}
