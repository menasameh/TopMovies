package minasameh.topmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

public class review implements Parcelable {
    public String ID;
    public String author;
    public String content;

    public review(){

    }

    protected review(Parcel in) {
        ID = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<review> CREATOR = new Creator<review>() {
        @Override
        public review createFromParcel(Parcel in) {
            return new review(in);
        }

        @Override
        public review[] newArray(int size) {
            return new review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(author);
        dest.writeString(content);
    }
}
