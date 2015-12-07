package minasameh.topmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    String name;
    String releaseDate;
    String longDescription;
    String imageUri;
    float rate;

    public Movie(){

    }

    protected Movie(Parcel in) {
        name = in.readString();
        releaseDate = in.readString();
        longDescription = in.readString();
        imageUri = in.readString();
        rate = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(releaseDate);
        dest.writeString(longDescription);
        dest.writeString(imageUri);
        dest.writeFloat(rate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
