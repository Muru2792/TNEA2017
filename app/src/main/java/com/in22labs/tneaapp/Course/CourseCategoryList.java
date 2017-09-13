package com.in22labs.tneaapp.Course;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;
//import com.in22labs.tneaapp.college.CourseListView;

import java.io.IOException;


public class CourseCategoryList extends Fragment{
    DatabaseHelper db;
    String CategoryName;
    TextView txtTitle,t2;
    LinearLayout cate1, cate2, cate3, cate4, cate5, cate6, cate7, cate8, cate9;
    RelativeLayout ltHome;
    TextView group1, group2, group3, group4, group5, group6, group7, group8, group9;
    private ShowcaseView showcaseView4;

    ConnectionDetector cd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lay_coursecategory,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Search College By Course");
        ab.setTextSize(20.0f);
        ab.setTypeface(Ty1);
        ltHome=(RelativeLayout)getActivity().findViewById(R.id.snackcourse);

        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewHomeCurse);
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        }
        t2=(TextView)getActivity().findViewById(R.id.liveCourse);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(ltHome, "No Internet Connection", Snackbar.LENGTH_SHORT).show();

                } else {
                    String str = "http://www.a4n.in/home/live_counselling_support";
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
        cate1=(LinearLayout)getActivity().findViewById(R.id.lt_cate1);
        cate2=(LinearLayout)getActivity().findViewById(R.id.lt_cate2);
        cate3=(LinearLayout)getActivity().findViewById(R.id.lt_cate3);
        cate4=(LinearLayout)getActivity().findViewById(R.id.lt_cate4);
        cate5=(LinearLayout)getActivity().findViewById(R.id.lt_cate5);
        cate6=(LinearLayout)getActivity().findViewById(R.id.lt_cate6);
        cate7=(LinearLayout)getActivity().findViewById(R.id.lt_cate7);
        cate8=(LinearLayout)getActivity().findViewById(R.id.lt_cate8);
        cate9=(LinearLayout)getActivity().findViewById(R.id.lt_cate9);
        txtTitle=(TextView)getActivity().findViewById(R.id.txtContentCurse);


        group1=(TextView)getActivity().findViewById(R.id.textg1);
        group2=(TextView)getActivity().findViewById(R.id.textg2);
        group3=(TextView)getActivity().findViewById(R.id.textg3);
        group4=(TextView)getActivity().findViewById(R.id.textg4);
        group5=(TextView)getActivity().findViewById(R.id.textg5);
        group6=(TextView)getActivity().findViewById(R.id.textg6);
        group7=(TextView)getActivity().findViewById(R.id.textg7);
        group8=(TextView)getActivity().findViewById(R.id.textg8);
        group9=(TextView)getActivity().findViewById(R.id.textg9);


        group1.setText("MECHANICAL");
        group2.setText("TEXTILE");
        group3.setText("CIVIL");
        group4.setText("COMPUTER");
        group5.setText("BIO-TECH");
        group6.setText("CHEMICAL");
        group7.setText("AGRI/FOOD");
        group8.setText("ELECTRONICS");
        group9.setText("SPECIALIZED");

        group1.setTextSize(12.0f);
        group1.setTypeface(Ty1);


        group2.setTextSize(12.0f);
        group2.setTypeface(Ty1);

        group3.setTextSize(12.0f);
        group3.setTypeface(Ty1);

        group4.setTextSize(12.0f);
        group4.setTypeface(Ty1);

        group5.setTextSize(12.0f);
        group5.setTypeface(Ty1);

        group6.setTextSize(12.0f);
        group6.setTypeface(Ty1);

        group7.setTextSize(12.0f);
        group7.setTypeface(Ty1);

        group8.setTextSize(12.0f);
        group8.setTypeface(Ty1);

        group9.setTextSize(12.0f);
        group9.setTypeface(Ty1);

        txtTitle.setText("Choose from over 80 Engg Courses");
        txtTitle.setTypeface(Ty1);
        txtTitle.setTextSize(15.5f);

        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Target viewTarget4 = new ViewTarget(R.id.lt_cate1, getActivity());
        showcaseView4=new ShowcaseView.Builder(getActivity())
                .setStyle(R.style.CustomShowcaseTheme)
                .setTarget(viewTarget4)
                .setContentTitle("Course")
                .setContentText("Courses, Career opportunities for the courses in future, detailed course description and colleges offering the course")
                .singleShot(42)
                .build();
        showcaseView4.setShowcaseX(1);
        showcaseView4.setShowcaseY(1);


        cate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="MECHANICAL";
                CategoryName="MECHANICAL";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cate7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="AGRI/FOOD";
                CategoryName="AGRI/FOOD";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        cate9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="SPECIALIZED";
                CategoryName="SPECIALIZED";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        cate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="TEXTILE";
                CategoryName="TEXTILE";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="CIVIL";
                CategoryName="CIVIL";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="COMPUTER";
                CategoryName="COMPUTER";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="BIO-TECH";
                CategoryName="BIO-TECH";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cate6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="CHEMICAL";
                CategoryName="CHEMICAL";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cate8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Cate_course="ELECTRONICS";
                CategoryName="ELECTRONICS";
                Fragment mFragment = new SubCateCourseList();
                final Bundle b1 = new Bundle();
                b1.putString("category", CategoryName);

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
        MyApplication.getInstance().trackScreenView("TNEA Search Course");
    }
}
