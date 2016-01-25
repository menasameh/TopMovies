package minasameh.topmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
        Bundle arguments = new Bundle();
            arguments.putInt(MainFragment.MOVIE, getIntent().getIntExtra(MainFragment.MOVIE, 0));
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit();
        }
    }
}
