package minasameh.topmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

public class trailer implements Parcelable {
    public String ID;
    public String key;
    public String name;

    public trailer(){

    }

    protected trailer(Parcel in) {
        ID = in.readString();
        key = in.readString();
        name = in.readString();
    }

    public static final Creator<trailer> CREATOR = new Creator<trailer>() {
        @Override
        public trailer createFromParcel(Parcel in) {
            return new trailer(in);
        }

        @Override
        public trailer[] newArray(int size) {
            return new trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(key);
        dest.writeString(name);
    }
}
