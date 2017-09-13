package com.in22labs.tneaapp.TopRank;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Adapter.ListCollegeAdapter;
import com.in22labs.tneaapp.CollegeDetails.CollegeData;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by In22lab on 5/18/2016.
 */
public class RankingCollegeList extends Fragment {

    ListView collegelist;
    String Ranking, City;
    String CollegeName, CollegeCode;

    ArrayList<String> CollegeOrderList,LocationList, Local, RankList;
    String[] ArrCollegeName, ArrLocation, ArrRank;
    ConnectionDetector cd;
    RelativeLayout main;
    LinearLayout lt1, lt2;
    DatabaseHelper db;
    String Index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.college_category,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adViewCollegeCate);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }


        //final Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        Index=getArguments().getString("Index");
        if(Index=="1") {
            ab.setText("AA+ Rated Colleges");
        }else if(Index=="2"){
            ab.setText("AA Rated Colleges");
        }else if (Index=="3"){
            ab.setText("A Rated Colleges");
        }else if (Index=="4"){
            ab.setText("B+ Rated Colleges");
        }else if (Index=="5"){
            ab.setText("B Rated Colleges");
        }else if(Index=="6"){
            ab.setText("C Rated Colleges");
        }else if(Index=="7"){
            ab.setText("D Rated Colleges");
        }
        ab.setTextSize(20.0f);

        lt1=(LinearLayout)getActivity().findViewById(R.id.lt_autocity);
        lt2=(LinearLayout)getActivity().findViewById(R.id.lt_coursecount);
        lt2.setVisibility(View.GONE);
        lt1.setVisibility(View.VISIBLE);

        final AutoCompleteTextView Location=(AutoCompleteTextView)getActivity().findViewById(R.id.autoDist);
        main=(RelativeLayout)getActivity().findViewById(R.id.distcollegelay);
        collegelist = (ListView)getActivity().findViewById(R.id.expand_college);
        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ranking=getArguments().getString("Ranking");

        if(Ranking=="AAPLUS"){
            db.rank = "AA+";
        }else if(Ranking=="AA"){
            db.rank="AA";
        }else if(Ranking=="A"){
            db.rank="A";
        }else if(Ranking=="BPLUS"){
            db.rank="B+";
        }else if(Ranking=="B"){
            db.rank="B";
        }else if(Ranking=="C"){
            db.rank="C";
        }else if(Ranking=="D"){
            db.rank="D";
        }



        CollegeOrderList=db.getCollegeRanking();
        LocationList=db.getLocationRanking();
        RankList=db.getRankRanking();
        Local=db.getCityNew();

        if(CollegeOrderList.isEmpty()){
            Snackbar.make(main, "No Search List Available", Snackbar.LENGTH_LONG).show();
        }



        ArrCollegeName=new String[CollegeOrderList.size()];
        ArrCollegeName=CollegeOrderList.toArray(ArrCollegeName);

        ArrLocation=new String[LocationList.size()];
        ArrLocation=LocationList.toArray(ArrLocation);

        ArrRank=new String[RankList.size()];
        ArrRank=RankList.toArray(ArrRank);
        collegelist.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
        ArrayAdapter<String> adpDistrict = new ArrayAdapter<String>
                (getActivity(), R.layout.autolist_college,R.id.dist_list, Local);
        Location.setThreshold(1);
        Location.setTextSize(15.5f);
        Location.setAdapter(adpDistrict);
        Location.setVisibility(View.VISIBLE);

        Location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                City = Location.getText().toString();
               // Toast.makeText(getActivity(), City, Toast.LENGTH_SHORT).show();
                //lt1.setVisibility(View.INVISIBLE);

                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(Location.getWindowToken(), 0);

                db.tempLoc = City;

                if (Ranking == "AAPLUS") {
                    db.rank = "AA+";
                } else if (Ranking == "AA") {
                    db.rank = "AA";
                } else if (Ranking == "A") {
                    db.rank = "A";
                } else if (Ranking == "BPLUS") {
                    db.rank = "B+";
                } else if (Ranking == "B") {
                    db.rank = "B";
                } else if (Ranking == "C") {
                    db.rank = "C";
                } else if (Ranking == "D") {
                    db.rank = "D";
                }

                CollegeOrderList = db.getCollegeRankingLocation();
                LocationList = db.getLocationRankingLocation();
                RankList = db.getRankRankingLocation();
                if (CollegeOrderList.isEmpty()) {
                    Snackbar.make(main, "No Search List Available", Snackbar.LENGTH_LONG).show();
                }
                ArrCollegeName = new String[CollegeOrderList.size()];
                ArrCollegeName = CollegeOrderList.toArray(ArrCollegeName);

                ArrLocation = new String[LocationList.size()];
                ArrLocation = LocationList.toArray(ArrLocation);

                ArrRank = new String[RankList.size()];
                ArrRank = RankList.toArray(ArrRank);
//                txtCollegeCout.setText("College:"+CollegeOrderList.size());
                //explist.setVisibility(View.VISIBLE);
                collegelist.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));

            }
        });


        collegelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int Position = position;
                CollegeName = CollegeOrderList.get(Position).toString();
                //getCourseChildList(position);
                //Toast.makeText(getActivity(),CollegeName,Toast.LENGTH_SHORT).show();
//                Location.setText("");
                CollegeCode = db.getCollegeCodeV1(CollegeName).toString();
                Fragment mFragment = new CollegeData();
                final Bundle b1 = new Bundle();
                b1.putString("clgcode", CollegeCode);
                String Index;
                Index = "1";
               // Toast.makeText(getActivity(), Index, Toast.LENGTH_SHORT).show();
                b1.putString("Index", Index);
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
        MyApplication.getInstance().trackScreenView("TNEA Rating Search Results");
    }
}
