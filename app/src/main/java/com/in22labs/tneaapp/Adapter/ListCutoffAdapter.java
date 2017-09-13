package com.in22labs.tneaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.in22labs.tneaapp.R;


public class ListCutoffAdapter extends BaseAdapter {

    Context mContext;
    String[] Course;
    String[] OC;
    String[] BCM;
    String[] BC;
    String[] MBC;
    String[] SC;
    String[] ST;
    String[] SCA;

    private static LayoutInflater inflater = null;

    public ListCutoffAdapter(Context context, String[] arrCourse, String[] arrOC, String[] arrBCM, String[] arrBC, String[] arrMBC, String[] arrSC, String[] arrSCA, String[] arrST) {
        mContext=context;
        Course = arrCourse;
        OC=arrOC;
        BCM = arrBCM;
        BC = arrBC;
        MBC = arrMBC;
        SC = arrSC;
        ST = arrST;
        SCA = arrSCA;

        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Course.length;
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

        TextView txtCourse;
        TextView txtOc;
        TextView txtBCM;
        TextView txtBC;
        TextView txtMBC;
        TextView txtSC;
        TextView txtSCA;
        TextView txtST;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.testing, null);


        holder.txtCourse=(TextView)rowView.findViewById(R.id.pastcourse);
        holder.txtOc=(TextView)rowView.findViewById(R.id.pastoc);
        holder.txtBCM=(TextView)rowView.findViewById(R.id.pastbcm);
        holder.txtBC=(TextView)rowView.findViewById(R.id.pastbc);
        holder.txtMBC=(TextView)rowView.findViewById(R.id.pastmbc);
        holder.txtSC=(TextView)rowView.findViewById(R.id.pastsc);
        holder.txtSCA=(TextView)rowView.findViewById(R.id.pastsca);
        holder.txtST=(TextView)rowView.findViewById(R.id.pastst);
       // holder.lay_main_college=(LinearLayout)rowView.findViewById(R.id.lay_list_college1);

        holder.txtCourse.setText(Course[position]);
        holder.txtOc.setText(OC[position]);
        holder.txtBCM.setText(BCM[position]);
        holder.txtBC.setText(BC[position]);
        holder.txtMBC.setText(MBC[position]);
        holder.txtSC.setText(SC[position]);
        holder.txtSCA.setText(SCA[position]);
        holder.txtST.setText(ST[position]);
        //final String pastyear_layout=College[position].toString();


        return rowView;
    }
}
