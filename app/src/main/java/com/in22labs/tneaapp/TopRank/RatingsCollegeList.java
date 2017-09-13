package com.in22labs.tneaapp.TopRank;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
public class RatingsCollegeList extends Fragment {
    String RatingId;
    DatabaseHelper db;
    ConnectionDetector cd;
    ListView expListView;
    Spinner filter;
    RelativeLayout rd;
    String CollegeName, CollegeCode;
    String Index;
    String FilterName;
    ArrayList<String> CollegeOrderList,LocationList, Local, RankList, FilterList1, FilterList2, FilterList3, FilterList4;
    String[] ArrCollegeName, ArrLocation, ArrRank;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_ratingcollegelist,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Typeface Ty1 = Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        Index=getArguments().getString("Index");
        if(Index=="1") {
            ab.setText("Ranking - Placement");
        }else if(Index=="2"){
            ab.setText("Ranking - Industry");
        }else if (Index=="3"){
            ab.setText("Ranking - Quality");
        }else if (Index=="4"){
            ab.setText("Ranking - Facilities");
        }
        ab.setTypeface(Ty1);
        ab.setTextSize(20.0f);
        rd=(RelativeLayout)getActivity().findViewById(R.id.ratinglay);
        filter=(Spinner)getActivity().findViewById(R.id.spinratecate);
        expListView = (ListView)getActivity().findViewById(R.id.list_collegerating);

        RatingId = getArguments().getString("Rating");

        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adViewRatingList);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }

        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FilterList1=new ArrayList<>();

        FilterList1.add(0, "All Colleges");
        FilterList1.add(1, "Best Placements");
        FilterList1.add(2, "Core Placements & Good IT cos");
        FilterList1.add(3, "Good placements");
        FilterList1.add(4, "Decent Placements");
        FilterList1.add(5, "Predominantly ITES and 1/2 IT placements");
        FilterList1.add(6, "Unknown Companies");



        FilterList2=new ArrayList<>();

        FilterList2.add(0, "All Colleges");
        FilterList2.add(1, "Highest & Best");
        FilterList2.add(2, "Best");
        FilterList2.add(3, "Good Tieups, but need more work ");
        FilterList2.add(4, "Good, but only Corp.Lectures ");
        FilterList2.add(5, "Average");
        FilterList2.add(6, "Ignorance of Industry ");


        FilterList3=new ArrayList<>();

        FilterList3.add(0, "All Colleges");
        FilterList3.add(1, "Highest & Best");
        FilterList3.add(2, "Best");
        FilterList3.add(3, "Good");
        FilterList3.add(4, "Average");
        FilterList3.add(5, "Below Average");
        FilterList3.add(6, "Low Quality");

        FilterList4=new ArrayList<>();

        FilterList4.add(0, "All Colleges");
        FilterList4.add(1, "Highest & Best");
        FilterList4.add(2, "Best");
        FilterList4.add(3, "Good");
        FilterList4.add(4, "Average");
        FilterList4.add(5, "Below Average");
        FilterList4.add(6, "Low Quality");


        db.ratingid=RatingId;
        if(RatingId=="Placement"){

            ArrayAdapter<String> adp = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, FilterList1);

            filter.setAdapter(adp);
            db.ratingid="Placement_rank";
            db.RatingFiled="Placement_Filter";
            CollegeOrderList=db.getRatingCollegeList();
            LocationList=db.getRatingLocationList();
            RankList=db.getRatingRankList();
        }else if(RatingId=="Industry"){

            ArrayAdapter<String> adp = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, FilterList2);

            filter.setAdapter(adp);

            db.ratingid="Industry_rank";

            CollegeOrderList=db.getRatingCollegeList();
            LocationList=db.getRatingLocationList();
            RankList=db.getRatingRankList();
        }else if(RatingId=="Quality"){
            ArrayAdapter<String> adp = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, FilterList3);

            filter.setAdapter(adp);
            db.ratingid="Quality_rank";

            CollegeOrderList=db.getRatingCollegeList();
            LocationList=db.getRatingLocationList();
            RankList=db.getRatingRankList();
        }else if(RatingId=="Facilities"){
            ArrayAdapter<String> adp = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line, FilterList4);

            filter.setAdapter(adp);
            db.ratingid="Facilities_rank";

            CollegeOrderList=db.getRatingCollegeList();
            LocationList=db.getRatingLocationList();
            RankList=db.getRatingRankList();
        }
        Local=db.getCityNew();

        if(CollegeOrderList.isEmpty()){
            Snackbar.make(rd, "No Search List Available", Snackbar.LENGTH_LONG).show();
        }



        ArrCollegeName=new String[CollegeOrderList.size()];
        ArrCollegeName=CollegeOrderList.toArray(ArrCollegeName);

        ArrLocation=new String[LocationList.size()];
        ArrLocation=LocationList.toArray(ArrLocation);

        ArrRank=new String[RankList.size()];
        ArrRank=RankList.toArray(ArrRank);

        expListView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));



        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
                FilterName = String.valueOf(filter.getSelectedItem());
                int i = filter.getSelectedItemPosition();
                //Snackbar.make(rd, "No Search List Available"+  i, Snackbar.LENGTH_LONG).show();

                if (RatingId == "Placement") {

                    if (i == 0) {
                        CollegeOrderList = db.getRatingCollegeList();
                        LocationList = db.getRatingLocationList();
                        RankList = db.getRatingRankList();
                    } else if (i == 1) {
                        db.quality = "Best";
                        CollegeOrderList = db.getRatingPlaceFilterCollegeList();
                        LocationList = db.getRatingPlaceFilterLocationList();
                        RankList = db.getRatingPlaceFilterRankList();
                    } else if (i == 2) {
                        db.quality = "Core";
                        CollegeOrderList = db.getRatingPlaceFilterCollegeList();
                        LocationList = db.getRatingPlaceFilterLocationList();
                        RankList = db.getRatingPlaceFilterRankList();
                    } else if (i == 3) {
                        db.quality = "Good";
                        CollegeOrderList = db.getRatingPlaceFilterCollegeList();
                        LocationList = db.getRatingPlaceFilterLocationList();
                        RankList = db.getRatingPlaceFilterRankList();
                    } else if (i == 4) {
                        db.quality = "Decent";
                        CollegeOrderList = db.getRatingPlaceFilterCollegeList();
                        LocationList = db.getRatingPlaceFilterLocationList();
                        RankList = db.getRatingPlaceFilterRankList();
                    } else if (i == 5) {
                        db.quality = "Predom";
                        CollegeOrderList = db.getRatingPlaceFilterCollegeList();
                        LocationList = db.getRatingPlaceFilterLocationList();
                        RankList = db.getRatingPlaceFilterRankList();
                    } else if (i == 6) {
                        db.quality = "Unknown";
                        CollegeOrderList = db.getRatingPlaceFilterCollegeList();
                        LocationList = db.getRatingPlaceFilterLocationList();
                        RankList = db.getRatingPlaceFilterRankList();
                    }

                } else if (RatingId == "Industry") {

                    if (i == 0) {
                        CollegeOrderList = db.getRatingCollegeList();
                        LocationList = db.getRatingLocationList();
                        RankList = db.getRatingRankList();
                    } else if (i == 1) {
                        db.quality = "Best";
                        CollegeOrderList = db.getRatingIndustryFilterCollegeList();
                        LocationList = db.getRatingIndustryFilterLocationList();
                        RankList = db.getRatingIndustryFilterRankList();
                    } else if (i == 2) {
                        db.quality = "Core";
                        CollegeOrderList = db.getRatingIndustryFilterCollegeList();
                        LocationList = db.getRatingIndustryFilterLocationList();
                        RankList = db.getRatingIndustryFilterRankList();
                    } else if (i == 3) {
                        db.quality = "Good";
                        CollegeOrderList = db.getRatingIndustryFilterCollegeList();
                        LocationList = db.getRatingIndustryFilterLocationList();
                        RankList = db.getRatingIndustryFilterRankList();
                    } else if (i == 4) {
                        db.quality = "Decent";
                        CollegeOrderList = db.getRatingIndustryFilterCollegeList();
                        LocationList = db.getRatingIndustryFilterLocationList();
                        RankList = db.getRatingIndustryFilterRankList();
                    } else if (i == 5) {
                        db.quality = "Predom";
                        CollegeOrderList = db.getRatingIndustryFilterCollegeList();
                        LocationList = db.getRatingIndustryFilterLocationList();
                        RankList = db.getRatingIndustryFilterRankList();
                    } else if (i == 6) {
                        db.quality = "Unknown";
                        CollegeOrderList = db.getRatingIndustryFilterCollegeList();
                        LocationList = db.getRatingIndustryFilterLocationList();
                        RankList = db.getRatingIndustryFilterRankList();
                    }

                } else if (RatingId == "Quality") {

                    if (i == 0) {
                        CollegeOrderList = db.getRatingCollegeList();
                        LocationList = db.getRatingLocationList();
                        RankList = db.getRatingRankList();
                    } else if (i == 1) {
                        db.quality = "Best";
                        CollegeOrderList = db.getRatingQualityFilterCollegeList();
                        LocationList = db.getRatingQualityFilterLocationList();
                        RankList = db.getRatingQualityFilterRankList();
                    } else if (i == 2) {
                        db.quality = "Core";
                        CollegeOrderList = db.getRatingQualityFilterCollegeList();
                        LocationList = db.getRatingIndustryFilterLocationList();
                        RankList = db.getRatingQualityFilterRankList();
                    } else if (i == 3) {
                        db.quality = "Good";
                        CollegeOrderList = db.getRatingQualityFilterCollegeList();
                        LocationList = db.getRatingQualityFilterLocationList();
                        RankList = db.getRatingQualityFilterRankList();
                    } else if (i == 4) {
                        db.quality = "Decent";
                        CollegeOrderList = db.getRatingQualityFilterCollegeList();
                        LocationList = db.getRatingQualityFilterLocationList();
                        RankList = db.getRatingQualityFilterRankList();
                    } else if (i == 5) {
                        db.quality = "Predom";
                        CollegeOrderList = db.getRatingQualityFilterCollegeList();
                        LocationList = db.getRatingQualityFilterLocationList();
                        RankList = db.getRatingQualityFilterRankList();
                    } else if (i == 6) {
                        db.quality = "Unknown";
                        CollegeOrderList = db.getRatingQualityFilterCollegeList();
                        LocationList = db.getRatingQualityFilterLocationList();
                        RankList = db.getRatingQualityFilterRankList();
                    }

                } else if (RatingId == "Facilities") {

                    if (i == 0) {
                        CollegeOrderList = db.getRatingCollegeList();
                        LocationList = db.getRatingLocationList();
                        RankList = db.getRatingRankList();
                    } else if (i == 1) {
                        db.quality = "Best";
                        CollegeOrderList = db.getRatingFacilityFilterCollegeList();
                        LocationList = db.getRatingFacilityFilterLocationList();
                        RankList = db.getRatingFacilityFilterRankList();
                    } else if (i == 2) {
                        db.quality = "Core";
                        CollegeOrderList = db.getRatingFacilityFilterCollegeList();
                        LocationList = db.getRatingFacilityFilterLocationList();
                        RankList = db.getRatingFacilityFilterRankList();
                    } else if (i == 3) {
                        db.quality = "Good";
                        CollegeOrderList = db.getRatingFacilityFilterCollegeList();
                        LocationList = db.getRatingFacilityFilterLocationList();
                        RankList = db.getRatingFacilityFilterRankList();
                    } else if (i == 4) {
                        db.quality = "Decent";
                        CollegeOrderList = db.getRatingFacilityFilterCollegeList();
                        LocationList = db.getRatingFacilityFilterLocationList();
                        RankList = db.getRatingFacilityFilterRankList();
                    } else if (i == 5) {
                        db.quality = "Predom";
                        CollegeOrderList = db.getRatingFacilityFilterCollegeList();
                        LocationList = db.getRatingFacilityFilterLocationList();
                        RankList = db.getRatingFacilityFilterRankList();
                    } else if (i == 6) {
                        db.quality = "Unknown";
                        CollegeOrderList = db.getRatingFacilityFilterCollegeList();
                        LocationList = db.getRatingFacilityFilterLocationList();
                        RankList = db.getRatingFacilityFilterRankList();
                    }

                }


                if (CollegeOrderList.isEmpty()) {
                    Snackbar.make(rd, "No Search List Available", Snackbar.LENGTH_LONG).show();
                }
                ArrCollegeName = new String[CollegeOrderList.size()];
                ArrCollegeName = CollegeOrderList.toArray(ArrCollegeName);

                ArrLocation = new String[LocationList.size()];
                ArrLocation = LocationList.toArray(ArrLocation);

                ArrRank = new String[RankList.size()];
                ArrRank = RankList.toArray(ArrRank);

                expListView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        MyApplication.getInstance().trackScreenView("TNEA Ranking Search");
    }
}
