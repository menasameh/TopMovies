package minasameh.topmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    public int ID;
    public String name;
    public String releaseDate;
    public String longDescription;
    public String imageUri;
    public ArrayList<trailer> trailers;
    public ArrayList<review> reviews;
    public float rate;


    public Movie(){

    }

    protected Movie(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        releaseDate = in.readString();
        longDescription = in.readString();
        imageUri = in.readString();
        in.readTypedList(trailers, trailer.CREATOR);
        in.readTypedList(reviews, review.CREATOR);
        rate = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(releaseDate);
        dest.writeString(longDescription);
        dest.writeString(imageUri);
        dest.writeTypedList(trailers);
        dest.writeTypedList(reviews);
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
