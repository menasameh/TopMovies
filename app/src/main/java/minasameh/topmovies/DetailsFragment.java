package minasameh.topmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import minasameh.topmovies.AsynchTask.getReviewTrailerTask;
import minasameh.topmovies.adapters.MovieCustomAdapter;
import minasameh.topmovies.data.DbHelper;
import minasameh.topmovies.model.Movie;

public class DetailsFragment extends Fragment {

    public static final String TAG = DetailsFragment.class.getSimpleName();

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Movie m;


        View view = inflater.inflate(R.layout.fragment_details, container, false);
        // Inflate the layout for this fragment
        try{
            int position=0;
            Bundle arguments = getArguments();
            if (arguments != null) {
                position = arguments.getInt(MainFragment.MOVIE);
            }
            m = MovieCustomAdapter.mList.get(position);
            if(m==null){
                getActivity().finish();
                return null;
            }
            new getReviewTrailerTask(getActivity()).execute(m);
        }
        catch(Exception e){
            return new View(getActivity());
        }
        final Movie mMovie = m;


        ImageView moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
        Glide.with(this).load(m.imageUri).into(moviePoster);


        TextView movieName = (TextView) view.findViewById(R.id.movie_name);
        movieName.setText(m.name);

        TextView movieDescription = (TextView) view.findViewById(R.id.movie_description);
        movieDescription.setText(m.longDescription);

        TextView movieYear = (TextView) view.findViewById(R.id.movie_date);
        movieYear.setText(m.releaseDate);

        CheckBox favorite = (CheckBox) view.findViewById(R.id.fav);
        favorite.setChecked(new DbHelper(getActivity()).isFavorite(m.ID));

        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new DbHelper(getActivity()).insertMovie(mMovie);
                } else {
                    new DbHelper(getActivity()).deleteMovie(mMovie.ID);
                }
            }
        });


        RatingBar movieRate = (RatingBar) view.findViewById(R.id.movie_rate);
        movieRate.setNumStars(5);
        movieRate.setMax(5);
        movieRate.setStepSize(0.000001F);
        movieRate.setRating(m.rate / 2.0F);
        movieRate.setIsIndicator(true);
        movieRate.invalidate();
        return view;
    }
}
