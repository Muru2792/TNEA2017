package com.in22labs.tneaapp.TopRank;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;

/**
 * Created by In22lab on 4/27/2016.
 */
public class TopRankZone extends Fragment implements View.OnClickListener{
    DatabaseHelper db;
    String CategoryName;
    TextView txtTitle, t2;
    LinearLayout zone1, zone2, zone3, zone4, zone5, zone6, zone7, zone8;

    TextView group1, group2, group3, group4, group5, group6, group7, group8, group9;
    ConnectionDetector cd;
    private ShowcaseView showcaseView5;
    RelativeLayout trl;
    private int counter = 0;
    InterstitialAd interAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lay_topranklist,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        trl=(RelativeLayout)getActivity().findViewById(R.id.snackRankzone);
        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adViewtopRankList);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
                fullBanner();
            }
        }
        t2=(TextView)getActivity().findViewById(R.id.liveTopRank);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(trl, "No Internet Connection", Snackbar.LENGTH_SHORT).show();

                } else {
                    String str = "http://tnea.a4n.in/home/live_counselling_support";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
                }

            }
        });
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(550);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        t2.setAnimation(anim);
        Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Top Colleges - Zone Wise");
        ab.setTypeface(Ty1);
        ab.setTextSize(20.0f);

        zone1=(LinearLayout)getActivity().findViewById(R.id.lt_zone1);
        zone2=(LinearLayout)getActivity().findViewById(R.id.lt_zone2);
        zone3=(LinearLayout)getActivity().findViewById(R.id.lt_zone3);
        zone4=(LinearLayout)getActivity().findViewById(R.id.lt_zone4);
        zone5=(LinearLayout)getActivity().findViewById(R.id.lt_zone5);
        zone6=(LinearLayout)getActivity().findViewById(R.id.lt_zone6);
        zone7=(LinearLayout)getActivity().findViewById(R.id.lt_zone7);
        zone8=(LinearLayout)getActivity().findViewById(R.id.lt_zone8);

        txtTitle=(TextView)getActivity().findViewById(R.id.txtContentZone);


        group1=(TextView)getActivity().findViewById(R.id.textz1);
        group2=(TextView)getActivity().findViewById(R.id.textz2);
        group3=(TextView)getActivity().findViewById(R.id.textz3);
        group4=(TextView)getActivity().findViewById(R.id.textz4);
        group5=(TextView)getActivity().findViewById(R.id.textz5);
        group6=(TextView)getActivity().findViewById(R.id.textz6);
        group7=(TextView)getActivity().findViewById(R.id.textz7);
        group8=(TextView)getActivity().findViewById(R.id.textz8);

        Target viewTarget5 = new ViewTarget(R.id.lt_zone1, getActivity());
        showcaseView5=new ShowcaseView.Builder(getActivity())
                .setStyle(R.style.CustomShowcaseTheme)
                .setTarget(viewTarget5)
                .setContentTitle("Institutions")
                .setContentText("Detailed Analysis of institutions and ranked them based on Placements, Faculty Quality, Industry Interface and Facilities ")
                .singleShot(20)
                .setOnClickListener(this)
                .build();
        zone1.setClickable(false);
        showcaseView5.setShowcaseX(1);
        showcaseView5.setShowcaseY(1);
        showcaseView5.setBlocksTouches(false);
//        group1.setText("CHENNAI ZONE");
//        group2.setText("COIMBATORE ZONE");
//        group3.setText("VILUPURAM ZONE");
//        group4.setText("SALEM ZONE");
//        group5.setText("TRICHY ZONE");
//        group6.setText("MADURAI ZONE");
//        group7.setText("TIRUNELVELI ZONE");
//        group8.setText("OVERALL RANKING");

//        group1.setTextSize(12.0f);
//        group1.setTypeface(Ty1);
//
//
//        group2.setTextSize(12.0f);
//        group2.setTypeface(Ty1);
//
//        group3.setTextSize(12.0f);
//        group3.setTypeface(Ty1);
//
//        group4.setTextSize(12.0f);
//        group4.setTypeface(Ty1);
//
//        group5.setTextSize(12.0f);
//        group5.setTypeface(Ty1);
//
//        group6.setTextSize(12.0f);
//        group6.setTypeface(Ty1);
//
//        group7.setTextSize(12.0f);
//        group7.setTypeface(Ty1);
//
//        group8.setTextSize(12.0f);
//        group8.setTypeface(Ty1);



        txtTitle.setText("The best colleges of TN - Zone wise");
        txtTitle.setTypeface(Ty1);
        txtTitle.setTextSize(15.5f);

        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        zone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcaseView5.hide();
                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE1");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        zone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE2");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });



        zone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE3");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        zone4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE4");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        zone5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE5");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        zone6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE6");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        zone7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE7");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        zone8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CollegeTopList();
                final Bundle b1 = new Bundle();
                b1.putString("zone", "ZONE8");

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView5.setTarget(Target.NONE);
                showcaseView5.setContentTitle("TNEA 2017");
                showcaseView5.setContentText("Search for top engineering college by filtering \nthrough the Cutoff’s of previous years.\n For eg: If your cutoff projection is 185.45, \nthen the colleges & courses which had seats vacant \nin the previous years for 185.45, will be displayed");
                showcaseView5.setButtonText("Close");
//                setAlpha(0.4f, Cutofflayout, Coursecategorylayout, Collegelayout, Districtlayout, Collegecatelayout);
                break;

            case 1:
                showcaseView5.hide();
                zone1.setClickable(true);
//                setAlpha(0.4f, Cutofflayout, Coursecategorylayout, Collegelayout, Districtlayout, Collegecatelayout);
                // snackHome.setClickable(true);

                break;
        }
        counter++;
    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA Zone Rank");
    }

    private void fullBanner() {
        interAd = new InterstitialAd(getActivity());
        interAd.setAdUnitId("ca-app-pub-5508938394844540/9000930511");

        AdRequest adReq = new AdRequest.Builder().build();
        interAd.loadAd(adReq);


        interAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded(){
                if(interAd.isLoaded()){
                    interAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                //startTimer();
            }
        });

    }
}
