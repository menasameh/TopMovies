package minasameh.topmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import minasameh.topmovies.AsynchTask.getMoviesTask;
import minasameh.topmovies.adapters.MovieCustomAdapter;
import minasameh.topmovies.data.DbHelper;
import minasameh.topmovies.model.Movie;


public class MainFragment extends Fragment {

    public static String MOVIE = "parce";
    MovieCustomAdapter adapter;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        adapter = new MovieCustomAdapter(getActivity(), new ArrayList<Movie>());
        GridView grid = (GridView) view.findViewById(R.id.grid_view);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(MainActivity.mTwoPane){
                    Bundle arguments = new Bundle();
                    arguments.putInt(MOVIE, position);

                    DetailsFragment fragment = new DetailsFragment();
                    fragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment,
                                    DetailsFragment.TAG)
                            .commit();
                }
                else{
                    Intent i = new Intent(getActivity(), DetailsActivity.class);
                    i.putExtra(MOVIE, position);
                    startActivity(i);
                }

            }
        });
        new DbHelper(getActivity());
        if(util.getSortOrder(getActivity())!=2)
            new getMoviesTask(getActivity(), adapter).execute();
        else{
            adapter.replace(new DbHelper(getActivity()).getMovies());
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(util.getSortOrder(getActivity())==2)
            adapter.replace(new DbHelper(getActivity()).getMovies());
    }




}
