package minasameh.topmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import minasameh.topmovies.model.Movie;


public class DbHelper extends SQLiteOpenHelper{



    public DbHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    public boolean isFavorite(int MovieId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(DBContract.Movie.TABLE_NAME, new String[]{DBContract.Movie._ID}, DBContract.Movie._ID+" = "+MovieId, null, null, null, null);
        return c.getCount()==1;
    }

    public ArrayList<Movie> getMovies(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(DBContract.Movie.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Movie> ret= new ArrayList<>();
        if(!c.moveToFirst()){
            return ret;
        }
        for(int i=0;i<c.getCount();i++){
            Movie m = new Movie();
            m.ID = c.getInt(c.getColumnIndex(DBContract.Movie._ID));
            m.name = c.getString(c.getColumnIndex(DBContract.Movie.NAME));
            m.imageUri = c.getString(c.getColumnIndex(DBContract.Movie.IMAGE_URL));
            m.longDescription = c.getString(c.getColumnIndex(DBContract.Movie.DESCRIPTION));
            m.rate = c.getFloat(c.getColumnIndex(DBContract.Movie.RATE));
            m.releaseDate = c.getString(c.getColumnIndex(DBContract.Movie.RELEASE_DATE));
            ret.add(m);
            c.moveToNext();
        }
        return ret;
    }



    public long insertMovie(Movie m){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues movieValues = new ContentValues();
        movieValues.put(DBContract.Movie._ID, m.ID);
        movieValues.put(DBContract.Movie.DESCRIPTION, m.longDescription);
        movieValues.put(DBContract.Movie.IMAGE_URL, m.imageUri);
        movieValues.put(DBContract.Movie.NAME, m.name);
        movieValues.put(DBContract.Movie.RATE, m.rate);
        movieValues.put(DBContract.Movie.RELEASE_DATE, m.releaseDate);

        return db.insert(DBContract.Movie.TABLE_NAME, null, movieValues);
    }


    public int deleteMovie(int MovieId){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DBContract.Movie.TABLE_NAME, DBContract.Movie._ID+" = "+MovieId, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  " + DBContract.Movie.TABLE_NAME
                +" ("+ DBContract.Movie._ID+" INTEGER PRIMARY KEY UNIQUE NOT NULL, "
                + DBContract.Movie.NAME+" TEXT NOT NULL, "
                + DBContract.Movie.RELEASE_DATE+" TEXT NOT NULL, "
                + DBContract.Movie.DESCRIPTION+" TEXT NOT NULL, "
                + DBContract.Movie.IMAGE_URL+" TEXT NOT NULL, "
                + DBContract.Movie.RATE+" REAL NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST "+ DBContract.Movie.TABLE_NAME);
        onCreate(db);
    }
}
