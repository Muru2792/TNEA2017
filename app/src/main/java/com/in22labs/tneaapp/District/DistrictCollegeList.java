package com.in22labs.tneaapp.District;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Adapter.ExpandableListAdapter;
import com.in22labs.tneaapp.Adapter.ListCollegeAdapter;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.CollegeDetails.CollegeData;
import com.in22labs.tneaapp.app.MyApplication;
//import com.in22labs.tneaapp.college.CourseListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by In22lab on 4/26/2016.
 */
public class DistrictCollegeList extends Fragment {
    String Cutoff, District, Coursename,CollegeCode;
    int switch1;
    String CollegeName;
    ExpandableListAdapter listAdapter;
    TextView txtCollegeCout, Coursechoose;
    ListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    DatabaseHelper db;
    ArrayList<String> Location, Rank,tamp;
    LinearLayout autoCity;
    String[] ArrCollegeName;
    String[] ArrLocation;
    String[] ArrRank;
    RelativeLayout main;
    public String CourseCode;
    ConnectionDetector cd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.college_category,null);
    }
    @SuppressWarnings("ResourceType")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        expListView = (ListView)getActivity().findViewById(R.id.expand_college);
        // explist=(ListView)getActivity().findViewById(R.id.list_college);
        main=(RelativeLayout)getActivity().findViewById(R.id.distcollegelay);
        txtCollegeCout=(TextView)getActivity().findViewById(R.id.collegCount);
        Coursechoose=(TextView)getActivity().findViewById(R.id.txtCourseName);
        autoCity=(LinearLayout)getActivity().findViewById(R.id.lt_autocity);

        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewCollegeCate);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }

        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Search Colleges By District");
        ab.setTextSize(20.0f);

        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp = getArguments().getString("switch").toString();
//        autoCity.setVisibility(View.INVISIBLE);
        //Cutoff=getArguments().getString("");
//        Switch=getArguments().getInt("switch");
        switch1 =Integer.parseInt(temp);
        if(switch1==1){
            District=getArguments().getString("city").toString();
            Coursename=getArguments().getString("Course").toString();
            db.Dist_city=District;
            db.Dist_course=Coursename;
            CourseCode=db.getBrachCode(Coursename);
            listDataHeader=db.getCollegeDistrictNew();
            Location=db.getLocationDistrictNew();
            Rank=db.getRankDistrictNew();
            if(listDataHeader.isEmpty()){
                Snackbar.make(main, "No Search List Available", Snackbar.LENGTH_LONG).show();
            }

            txtCollegeCout.setText("  College: " + listDataHeader.size());
            Coursechoose.setText("CourseCode: "+CourseCode+"  ");

            ArrCollegeName=new String[listDataHeader.size()];
            ArrCollegeName=listDataHeader.toArray(ArrCollegeName);

            ArrLocation=new String[Location.size()];
            ArrLocation=Location.toArray(ArrLocation);

            ArrRank=new String[Rank.size()];
            ArrRank=Rank.toArray(ArrRank);

            expListView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
        }else if(switch1==2){
            District=getArguments().getString("city").toString();
            Cutoff=getArguments().getString("cutoff").toString();
            db.Dist_city=District;
            db.Dist_cutoff=Cutoff;

            listDataHeader=db.getCollegeDistrictCutoffNew();
            Location=db.getLocationDistrictCutoffNew();
            Rank=db.getRankDistrictCutoffNew();
            tamp=new ArrayList<>();
            for(int k=0; k<listDataHeader.size();k++){
                String temp1=listDataHeader.get(k);
                String tamp2=db.getRankCode(temp1);
                tamp.add(k, tamp2);
            }
            if(listDataHeader.isEmpty()){
                Snackbar.make(main, "No Search List Available", Snackbar.LENGTH_LONG).show();
            }

            txtCollegeCout.setText("  College: " + listDataHeader.size());
            Coursechoose.setText("Select Cutoff: "+Cutoff+"  ");
            ArrCollegeName=new String[listDataHeader.size()];
            ArrCollegeName=listDataHeader.toArray(ArrCollegeName);

            ArrLocation=new String[Location.size()];
            ArrLocation=Location.toArray(ArrLocation);

            ArrRank=new String[tamp.size()];
            ArrRank=tamp.toArray(ArrRank);

            expListView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));



//            expListView.setAdapter(adapter);

          //
        }




        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Position = position;
                CollegeName = listDataHeader.get(Position).toString();
              //  Toast.makeText(getActivity(),CollegeName,Toast.LENGTH_SHORT).show();
                CollegeCode=db.getCollegeCodeV1(CollegeName).toString();
//                prepareListData();
//                listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
//                expListView.setAdapter(listAdapter);
                Fragment mFragment = new CollegeData();
                final Bundle b1 = new Bundle();
//                b1.putString("city", s2);
                b1.putString("clgcode", CollegeCode);
                String Index;
                Index="1";
                b1.putString("Index", Index);
//                b1.putString("community", s3);
//                b1.putString("cutoff", s1);
//
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();





            }

        });

    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA District College List");
    }
}
