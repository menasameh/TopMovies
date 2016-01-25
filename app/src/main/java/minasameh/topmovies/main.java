package minasameh.topmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import minasameh.topmovies.AsynchTask.getMoviesTask;
import minasameh.topmovies.adapters.MovieCustomAdapter;
import minasameh.topmovies.data.DbHelper;
import minasameh.topmovies.model.Movie;

public class main extends AppCompatActivity {

    public static String MOVIE = "parce";
    MovieCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MovieCustomAdapter(this, new ArrayList<Movie>());
        GridView grid = (GridView) findViewById(R.id.grid_view);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(main.this, details.class);
                i.putExtra(MOVIE, position);
                startActivity(i);
            }
        });
        new DbHelper(this);
        if(util.getSortOrder(this)!=2)
            new getMoviesTask(this, adapter).execute();
        else{
            adapter.replace(new DbHelper(this).getMovies());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(util.getSortOrder(this)==2)
            adapter.replace(new DbHelper(this).getMovies());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(this, Settings.class));
        }
        return super.onOptionsItemSelected(item);

    }



}
