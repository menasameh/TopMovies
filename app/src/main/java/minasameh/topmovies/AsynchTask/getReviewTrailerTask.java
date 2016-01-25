package minasameh.topmovies.AsynchTask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

import minasameh.topmovies.R;
import minasameh.topmovies.adapters.ReviewCustomAdapter;
import minasameh.topmovies.adapters.TrailerCustomAdapter;
import minasameh.topmovies.model.Movie;
import minasameh.topmovies.model.review;
import minasameh.topmovies.model.trailer;

public class getReviewTrailerTask extends AsyncTask<Movie, Void, Void> {

        final String LOG_TAG = getReviewTrailerTask.class.getSimpleName();
        String ApiKey="b7b092c2e175e13cf779c08c8eac31b2";
        Movie m;
        Activity view;


        public getReviewTrailerTask(Activity mContext){
            this.view = mContext;
        }


        private ArrayList<trailer> getTrailersFromJson(String trailersJsonStr)
                throws JSONException {

            final String OWM_LIST = "results";
            final String OWM_KEY = "key";
            final String OWM_ID = "id";
            final String OWM_NAME = "name";


            JSONObject TrailersJson = new JSONObject(trailersJsonStr);
            JSONArray TrailersArray = TrailersJson.getJSONArray(OWM_LIST);
            ArrayList<trailer> list = new ArrayList<>();
            for(int i = 0; i < TrailersArray.length(); i++) {
                JSONObject TrailerItem = TrailersArray.getJSONObject(i);

                trailer m = new trailer();
                m.ID = TrailerItem.getString(OWM_ID);
                m.name = TrailerItem.getString(OWM_NAME);
                m.key = TrailerItem.getString(OWM_KEY);
                list.add(m);
            }
            return list;
        }

        private ArrayList<review> getReviewsFromJson(String trailersJsonStr)
                throws JSONException {

            final String OWM_LIST = "results";
            final String OWM_ID = "id";
            final String OWM_AUTH = "author";
            final String OWM_CONTENT = "content";


            JSONObject reviewsJson = new JSONObject(trailersJsonStr);
            JSONArray reviewsArray = reviewsJson.getJSONArray(OWM_LIST);
            ArrayList<review> list = new ArrayList<>();
            for(int i = 0; i < reviewsArray.length(); i++) {
                JSONObject reviewItem = reviewsArray.getJSONObject(i);

                review m = new review();
                m.ID = reviewItem.getString(OWM_ID);
                m.author = reviewItem.getString(OWM_AUTH);
                m.content = reviewItem.getString(OWM_CONTENT);
                list.add(m);
            }
            return list;
        }

        void getObjects(Movie m, boolean isTrailer){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String trailersJsonStr = null;
            try {
                final String trailersBaseURL = "http://api.themoviedb.org/3/movie/"+m.ID+(isTrailer?"/videos":"/reviews");
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(trailersBaseURL).buildUpon()
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
                    if(isTrailer)
                        m.trailers=new ArrayList<>();
                    else
                        m.reviews = new ArrayList<>();
                    return;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                if (buffer.length() == 0) {
                    if(isTrailer)
                        m.trailers=new ArrayList<>();
                    else
                        m.reviews = new ArrayList<>();
                    return;
                }
                trailersJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                trailersJsonStr = "";
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
                if(isTrailer)
                    m.trailers= getTrailersFromJson(trailersJsonStr);
                else
                    m.reviews = getReviewsFromJson(trailersJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error in parsing Json", e);
            }
        }

        @Override
        protected Void doInBackground(Movie... params) {

            m = params[0];

            getObjects(m, true);
            getObjects(m, false);

            return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        ListView trailers = (ListView) view.findViewById(R.id.trailers_listview);
        ListView reviews = (ListView) view.findViewById(R.id.reviews_listview);

        trailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + m.trailers.get(position).key)));
            }
        });

        TrailerCustomAdapter trailerCustomAdapter = new TrailerCustomAdapter(view, m.trailers);
        ReviewCustomAdapter reviewCustomAdapter = new ReviewCustomAdapter(view, m.reviews);
        trailers.setAdapter(trailerCustomAdapter);
        reviews.setAdapter(reviewCustomAdapter);


    }
}
