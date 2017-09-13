package com.in22labs.tneaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.in22labs.tneaapp.R;

public class ListCollegeAdapter extends BaseAdapter {

    Context mContext;
    String[] College;
    String[] Location;
    String[] Rank;

    private static LayoutInflater inflater = null;

    public ListCollegeAdapter(Context context, String[] mCollege, String[] mLocation, String[] mRank) {
        mContext = context;
        College = mCollege;
        Location = mLocation;
        Rank = mRank;


        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return College.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Holder {
        TextView txtCollegeName;
        TextView txtLocation;
        TextView txtRank;

        LinearLayout lay_main_college;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.listview_college, null);


        holder.txtCollegeName=(TextView)rowView.findViewById(R.id.textlist);
        holder.txtLocation=(TextView)rowView.findViewById(R.id.txtLocation);
        holder.txtRank=(TextView)rowView.findViewById(R.id.txtRank);
        holder.lay_main_college=(LinearLayout)rowView.findViewById(R.id.lay_list_college1);

        holder.txtCollegeName.setText(College[position]);
        holder.txtLocation.setText("Location: "+Location[position]);
        holder.txtRank.setText("Rank: "+Rank[position]);
        //final String pastyear_layout=College[position].toString();


        return rowView;
    }
}
