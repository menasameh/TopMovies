package minasameh.topmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    ArrayList<Movie> mList;

    public CustomAdapter (Context context, ArrayList<Movie> list){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mList = list;
    }

    public void add(Movie m){
        mList.add(m);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Movie> m){
        mList.addAll(m);
        notifyDataSetChanged();
    }

    public void clear(){
        mList.clear();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.grid_custom_item, parent,false);
        } else {
            view = convertView;
        }

        Movie cur = mList.get(position);

        ImageView iv = (ImageView)view.findViewById(R.id.im);
        Glide.with(mContext).load(cur.imageUri).into(iv);
        TextView text = (TextView)view.findViewById(R.id.ti);
        text.setText(cur.name);
        return view;
    }
}
