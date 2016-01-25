package minasameh.topmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


            Bundle arguments = new Bundle();
            arguments.putParcelable(MainFragment.MOVIE, getIntent().getParcelableExtra(MainFragment.MOVIE));
            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();

    }
}
