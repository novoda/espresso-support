package com.novoda.movies.topten;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.novoda.movies.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopTenActivity extends AppCompatActivity {

    private final TestRepository testRepository = new TestRepository();
    private final TopTenAdapter topTenAdapter = new TopTenAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        RecyclerView recyclerView = findViewById(R.id.top_ten_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(topTenAdapter);
        testRepository.observeChanges(observer);
    }

    private final TestRepository.Observer observer = new TestRepository.Observer() {
        @Override
        public void onUpdated(List<RateableMovieViewModel> viewModels) {
            List<RateableMovieViewModel> viewModelsWithActions = new ArrayList<>(viewModels.size());
            for (final RateableMovieViewModel viewModel : viewModels) {
                viewModelsWithActions.add(convertToCompleteViewModel(viewModel));
            }
            topTenAdapter.update(viewModelsWithActions);
        }

        // converts to viewmodels (with actions). would be a different class, but seemed pointless for demo
        private RateableMovieViewModel convertToCompleteViewModel(final RateableMovieViewModel viewModel) {
            return new RateableMovieViewModelBuilder(viewModel).actions(new RateableMovieViewModel.UserActions() {
                @Override
                public void onSelectMovie() {
                    // in a real app, this would probably navigate somewhere
                    Log.d("!!!", "onSelectMovie: " + viewModel.title());
                }

                @Override
                public void onToggleLike() {
                    testRepository.onToggleLike(viewModel.id());
                }

                @Override
                public void onRate(float rating) {
                    testRepository.onRate(viewModel.id(), rating);
                }
            }).build();
        }
    };

    private static class TopTenAdapter extends RecyclerView.Adapter<RateableMovieViewHolder> {

        private List<RateableMovieViewModel> viewModels;

        TopTenAdapter() {
            super.setHasStableIds(true);
        }

        @Override
        public RateableMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return RateableMovieViewHolder.inflate(parent);
        }

        public void update(List<RateableMovieViewModel> viewModels) {
            this.viewModels = viewModels;
            notifyDataSetChanged();
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

    // note: this demo does not showcase any dependency injection :D
    private static class TestRepository {

        private final List<RateableMovieViewModel> viewModels = initialFetchFromSomewhere();
        private Observer observer;

        void observeChanges(Observer observer) {
            this.observer = observer;
            observer.onUpdated(viewModels);
        }

        void onRate(long id, float newRating) {
            List<RateableMovieViewModel> updated = new ArrayList<>();
            for (RateableMovieViewModel viewModel : viewModels) {
                RateableMovieViewModelBuilder builder = new RateableMovieViewModelBuilder(viewModel);
                if (id == viewModel.id()) {
                    builder.rating(newRating);
                }
                updated.add(builder.build());
            }
            updateWith(updated);
        }

        void onToggleLike(long id) {
            List<RateableMovieViewModel> updated = new ArrayList<>();
            for (RateableMovieViewModel viewModel : viewModels) {
                RateableMovieViewModelBuilder builder = new RateableMovieViewModelBuilder(viewModel);
                if (id == viewModel.id()) {
                    builder.liked(!viewModel.liked());
                }
                updated.add(builder.build());
            }
            updateWith(updated);
        }

        private void updateWith(List<RateableMovieViewModel> updated) {
            viewModels.clear();
            viewModels.addAll(updated);
            observer.onUpdated(updated);
        }

        private static List<RateableMovieViewModel> initialFetchFromSomewhere() {
            return new ArrayList<>(Arrays.asList(
                    newModel("Arrival", 4.5f, true, R.drawable.topten_arrival),
                    newModel("Interstellar", 4.5f, true, R.drawable.topten_interstellar),
                    newModel("The Royal Tenenbaums", 5, true, R.drawable.topten_royaltenenbaums),
                    newModel("Whiplash", 4.5f, true, R.drawable.topten_whiplash),
                    newModel("Beetlejuice", 5, false, R.drawable.topten_beetlejuice),
                    newModel("Iron Giant", 4.5f, true, R.drawable.topten_irongiant),
                    newModel("Million Dollar Baby", 4.5f, true, R.drawable.topten_milliondollarbaby),
                    newModel("Take Shelter", 4.5f, true, R.drawable.topten_takeshelter),
                    newModel("Planes, Trains and Automobiles", 4, true, R.drawable.topten_planestrainsautomobiles),
                    newModel("Fantastic Mr Fox", 4, false, R.drawable.topten_fantasticmrfox)
            ));
        }

        private static RateableMovieViewModel newModel(String title, float rating, boolean liked, @DrawableRes int poster) {
            return new RateableMovieViewModelBuilder()
                    .id(title.hashCode())
                    .title(title)
                    .liked(liked)
                    .rating(rating)
                    .poster(poster)
                    .build();
        }

        interface Observer {

            void onUpdated(List<RateableMovieViewModel> viewModels);
        }
    }
}
