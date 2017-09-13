package com.in22labs.tneaapp.CollegeDetails;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;


public class CollegeRank extends Fragment {
    TextView txtContent;
    RelativeLayout barQuality, Rank;

    public BarChart bar;
    ConnectionDetector cd;
    Float GivenPlace, GivenIndustry, GivenQuality, GivenFacility;

    String PlacemantGivenRankString, IndustryGivenRankString, QualityGivenRankString, FacilityGivenRankString;

    DatabaseHelper db;
    String CollegeCode;
    public static final int[] PASTEL_COLORS = {
            Color.rgb(66, 165, 245), Color.rgb(77, 182, 172), Color.rgb(79, 195, 247),
            Color.rgb(76, 155, 80), Color.rgb(206, 27, 96)
    };



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
        return inflater.inflate(R.layout.rank_layout,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//
        barQuality=(RelativeLayout)getActivity().findViewById(R.id.bar_placement);
        Rank=(RelativeLayout)getActivity().findViewById(R.id.rank_lay);
        txtContent=(TextView)getActivity().findViewById(R.id.txtContentRank);
        cd= new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewRankProf);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                mAdView.setVisibility(View.VISIBLE);
            }
        }
        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CollegeCode= getArguments().getString("clgcode");
        db.clg_code=CollegeCode;
        PlacemantGivenRankString = db.getPlaceRank();
        IndustryGivenRankString = db.getIndusRank();
        QualityGivenRankString = db.getQualRank();
        FacilityGivenRankString = db.getFaceRank();
        if(PlacemantGivenRankString=="null")
        {
            GivenPlace=0.0f;
        }else {
            GivenPlace = Float.valueOf(PlacemantGivenRankString);
        }

        if(IndustryGivenRankString=="null"){
            GivenIndustry=0.0f;
        }else {
            GivenIndustry = Float.valueOf(IndustryGivenRankString);
        }

        if (QualityGivenRankString=="null"){
            GivenQuality=0.0f;
        } else {
            GivenQuality = Float.valueOf(QualityGivenRankString);
        }
        if (FacilityGivenRankString=="null"){
            GivenFacility=0.0f;
        } else {
            GivenFacility = Float.valueOf(FacilityGivenRankString);
        }

//
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(GivenPlace, 0));
        entries.add(new BarEntry(GivenIndustry, 1));
        entries.add(new BarEntry(GivenQuality, 2));
        entries.add(new BarEntry(GivenFacility, 3));

        BarDataSet dataset = new BarDataSet(entries, "# Rank");


        ArrayList<String> labels = new ArrayList<>();
        labels.add("PL");
        labels.add("II");
        labels.add("QY");
        labels.add("FY");


        bar = new BarChart(getActivity());



        ArrayList<Integer> color= new ArrayList<>();

        for( int c: PASTEL_COLORS)
            color.add(c);

        color.add(ColorTemplate.getHoloBlue());
        dataset.setColors(color);
        BarData data = new BarData(labels, dataset);
        bar.setData(data);

        bar.setDescription("");

        bar.animateY(2500);
        dataset.setDrawValues(true);


        LimitLine line = new LimitLine(10f);
        line.setLabel("Top Ranks");
        line.setTextSize(16f);
        YAxis dataYaxis = bar.getAxisLeft();

        dataYaxis.setAxisMaxValue(11f);
        dataYaxis.setAxisMinValue(0f);
        dataYaxis.addLimitLine(line);

        XAxis dataXAxis = bar.getXAxis();
        dataXAxis.setTextColor(R.color.red);
        dataXAxis.setGridColor(R.color.tranred);
        dataXAxis.setTextSize(16.0f);

        YAxis dataYRaxis = bar.getAxisRight();

        dataYRaxis.setAxisMaxValue(11f);
        dataYRaxis.setAxisMinValue(0f);
        dataYRaxis.addLimitLine(line);

        bar.setTouchEnabled(false);

        barQuality.addView(bar, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));


    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA College Rank");
    }


}
