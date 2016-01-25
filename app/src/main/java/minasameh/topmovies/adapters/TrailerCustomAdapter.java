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
import minasameh.topmovies.model.trailer;

public class TrailerCustomAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    public static ArrayList<trailer> mList;

    public TrailerCustomAdapter(Context context, ArrayList<trailer> list){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mList = list;
    }

    public void add(trailer m){
        mList.add(m);
        notifyDataSetChanged();
    }

    public void addAll(trailer[] m){
        mList.addAll(Arrays.asList(m));
        notifyDataSetChanged();
    }

    public void replace(ArrayList<trailer> m){
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
            view = mInflater.inflate(R.layout.trailer_list_item, null);
        } else {
            view = convertView;
        }

        trailer cur = mList.get(position);

        TextView trailerName = (TextView)view.findViewById(R.id.trailer_name);

        trailerName.setText(cur.name);
        return view;
    }
}