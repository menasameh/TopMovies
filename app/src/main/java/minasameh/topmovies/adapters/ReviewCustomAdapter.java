package minasameh.topmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import minasameh.topmovies.R;
import minasameh.topmovies.model.review;


public class ReviewCustomAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    public static ArrayList<review> mList;

    public ReviewCustomAdapter(Context context, ArrayList<review> list){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mList = list;
    }

    public void add(review m){
        mList.add(m);
        notifyDataSetChanged();
    }

    public void addAll(review[] m){
        mList.addAll(Arrays.asList(m));
        notifyDataSetChanged();
    }

    public void replace(ArrayList<review> m){
        mList.clear();
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
            view = mInflater.inflate(R.layout.review_list_item, null);
        } else {
            view = convertView;
        }

        review cur = mList.get(position);

        TextView author = (TextView)view.findViewById(R.id.author);
        TextView content = (TextView)view.findViewById(R.id.content);

        author.setText(cur.author);
        content.setText(cur.content);

        return view;

    }
}