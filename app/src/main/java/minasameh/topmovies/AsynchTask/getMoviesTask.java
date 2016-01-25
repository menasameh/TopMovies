package minasameh.topmovies.AsynchTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import minasameh.topmovies.DetailsFragment;
import minasameh.topmovies.MainFragment;
import minasameh.topmovies.R;
import minasameh.topmovies.Settings;
import minasameh.topmovies.adapters.MovieCustomAdapter;
import minasameh.topmovies.MainActivity;
import minasameh.topmovies.model.Movie;


public class getMoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

    final String LOG_TAG = getMoviesTask.class.getSimpleName();
    String ApiKey="b7b092c2e175e13cf779c08c8eac31b2";
    FragmentActivity mContext;
    MovieCustomAdapter adapter;

    public getMoviesTask(FragmentActivity mContext, MovieCustomAdapter adapter){
        this.mContext= mContext;
        this.adapter = adapter;
    }


    private ArrayList<Movie> getMoviesFromJson(String MoviesJsonStr)
            throws JSONException {

        final String OWM_LIST = "results";
        final String OWM_TITLE = "title";
        final String OWM_POSTER = "poster_path";
        final String OWM_OVERVIEW = "overview";
        final String OWM_RATE = "vote_average";
        final String OWM_DATE = "release_date";
        final String OWM_ID = "id";
        final String baseURL = "http://image.tmdb.org/t/p/";

        final String size = "w185";

        JSONObject MoviesJson = new JSONObject(MoviesJsonStr);
        JSONArray MoviesArray = MoviesJson.getJSONArray(OWM_LIST);
        ArrayList<Movie> list = new ArrayList<>();
        for(int i = 0; i < MoviesArray.length(); i++) {
            JSONObject MovieItem = MoviesArray.getJSONObject(i);
            if(MovieItem.getString(OWM_POSTER).equals("null")){
                continue;
            }
            Movie m = new Movie();
            m.ID = MovieItem.getInt(OWM_ID);
            m.name = MovieItem.getString(OWM_TITLE);
            m.imageUri = baseURL+size+MovieItem.getString(OWM_POSTER);
            m.longDescription = MovieItem.getString(OWM_OVERVIEW);
            m.rate = (float)MovieItem.getDouble(OWM_RATE);
            m.releaseDate = MovieItem.getString(OWM_DATE);
            list.add(m);
        }
        return list;
    }


    @Override
    protected ArrayList<Movie> doInBackground(Void... args) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr = null;

        try {

            final String Movies_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String QUERY_PARAM = "sort_by";
            final String APPID_PARAM = "api_key";

            SharedPreferences settings = mContext.getSharedPreferences(Settings.PREFS_NAME, Context.MODE_PRIVATE);
            int orderBy = settings.getInt(mContext.getString(R.string.Movies_order_key),
                    Integer.parseInt(mContext.getString(R.string.Movies_order_default)));

            String order;
            if(orderBy == 0){
                order = "popularity.desc";
            }
            else{
                order = "vote_average.desc";
            }


            Uri builtUri = Uri.parse(Movies_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, order)
                    .appendQueryParameter(APPID_PARAM, ApiKey)
                    .build();

            java.net.URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            moviesJsonStr = "";
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getMoviesFromJson(moviesJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error in parsing Json", e);
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> s) {
        if(s!=null && s.size() > 0){
            adapter.replace(s);
        }
        if(MainActivity.mTwoPane && s.size() > 0){
            Bundle arguments = new Bundle();
            arguments.putInt(MainFragment.MOVIE, 0);

            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(arguments);

            mContext.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment,
                            DetailsFragment.TAG)
                    .commit();
        }
    }
}