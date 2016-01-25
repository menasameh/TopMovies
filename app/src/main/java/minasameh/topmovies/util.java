package minasameh.topmovies;

import android.content.Context;
import android.content.SharedPreferences;

public class util {

    public static int getSortOrder(Context mContext){
        SharedPreferences settings = mContext.getSharedPreferences(Settings.PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(mContext.getString(R.string.Movies_order_key),
                Integer.parseInt(mContext.getString(R.string.Movies_order_default)));
    }
}
