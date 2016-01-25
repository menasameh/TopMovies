package minasameh.topmovies.data;

import android.provider.BaseColumns;


public class DBContract {

    //database name
    final public static String DB_NAME = "movies";
    final public static int DB_VERSION=1;



    //movie table parameters
    public class Movie implements BaseColumns{
        final public static String TABLE_NAME = "movies";

        final public static String NAME = "name";

        final public static String RELEASE_DATE = "date";

        final public static String DESCRIPTION = "desc";

        final public static String IMAGE_URL = "url";

        final public static String RATE = "rate";

    }




}
