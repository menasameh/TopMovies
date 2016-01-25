package minasameh.topmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        Movie m;
//        try{
//            int position = getIntent().getExtras().getInt(MainFragment.MOVIE);
//            m = MovieCustomAdapter.mList.get(position);
//            if(m==null){
//                finish();
//                return;
//            }
//            new getReviewTrailerTask(this).execute(m);
//        }
//        catch(Exception e){
//            Toast.makeText(this, "activity started from place other than main", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//        final Movie mMovie = m;
//
//        setContentView(R.layout.activity_details);
//
//        ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);
//        Glide.with(this).load(m.imageUri).into(moviePoster);
//
//
//        TextView movieName = (TextView) findViewById(R.id.movie_name);
//        movieName.setText(m.name);
//
//        TextView movieDescription = (TextView) findViewById(R.id.movie_description);
//        movieDescription.setText(m.longDescription);
//
//        TextView movieYear = (TextView) findViewById(R.id.movie_date);
//        movieYear.setText(m.releaseDate);
//
//        CheckBox favorite = (CheckBox) findViewById(R.id.fav);
//        favorite.setChecked(new DbHelper(this).isFavorite(m.ID));
//
//        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    new DbHelper(details.this).insertMovie(mMovie);
//                } else {
//                    new DbHelper(details.this).deleteMovie(mMovie.ID);
//                }
//            }
//        });
//
//
//        RatingBar movieRate = (RatingBar) findViewById(R.id.movie_rate);
//        movieRate.setNumStars(5);
//        movieRate.setMax(5);
//        movieRate.setStepSize(0.000001F);
//        movieRate.setRating(m.rate / 2.0F);
//        movieRate.setIsIndicator(true);
//        movieRate.invalidate();

    }



}
