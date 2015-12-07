package minasameh.topmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        if(savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }
*/
        setContentView(R.layout.activity_details);


        Movie m;
        try{
             m = getIntent().getExtras().getParcelable(main.PARCE);
        }
        catch(Exception e){
            Toast.makeText(this, "activity started from place other than main", Toast.LENGTH_SHORT).show();
            return;
        }
        if(m==null)return ;


        ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);
        Glide.with(this).load(m.imageUri).into(moviePoster);


        TextView movieName = (TextView) findViewById(R.id.movie_name);
        movieName.setText(m.name);

        TextView movieDescription = (TextView) findViewById(R.id.movie_description);
        movieDescription.setText(m.longDescription);

        TextView movieYear = (TextView) findViewById(R.id.movie_date);
        movieYear.setText(m.releaseDate);

        RatingBar movieRate = (RatingBar) findViewById(R.id.movie_rate);
        movieRate.setNumStars(5);
        movieRate.setMax(5);
        movieRate.setStepSize(0.000001F);
        movieRate.setRating(m.rate / 2.0F);
        movieRate.setIsIndicator(true);
        movieRate.invalidate();

        Toast.makeText(this, (movieRate.getRating())+"", Toast.LENGTH_SHORT).show();

    }

}
