package com.in22labs.tneaapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Course.CourseCategoryList;
import com.in22labs.tneaapp.HomeFragments.AboutusFragment;
import com.in22labs.tneaapp.HomeFragments.CollegeSearch;
import com.in22labs.tneaapp.HomeFragments.CutoffFragment;
import com.in22labs.tneaapp.HomeFragments.DistrictFragment;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.TopRank.CateRank;
import com.in22labs.tneaapp.TopRank.CateRatingCollege;
import com.in22labs.tneaapp.TopRank.TopRankZone;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment implements View.OnClickListener{

    LinearLayout Cutofflayout, Coursecategorylayout, Collegelayout, Districtlayout, Collegecatelayout, Topranklayout, Collegeratinglayout, Aboutuslayout;
    TextView txtCut, txtCollege, txtCourse, txtDistrict, txtLive, txtAbout, txtHome;
    ImageView im_cut;
    ImageView im_district, im_course, im_college, im4;
    private ShowcaseView showcaseView1;
    TextView t1;
    private int counter = 0;
    RelativeLayout snackHome;
    ConnectionDetector cd;

    String date, time, id="1";
    DatabaseHelper db;
    final Handler handler = new Handler();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_screen, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        final Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);

        ab.setText("TNEA");
        ab.setTypeface(Ty1);
        ab.setTextSize(20.0f);
        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewHome);
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        }

        Cutofflayout =(LinearLayout)getActivity().findViewById(R.id.lt_cutoff);
        Coursecategorylayout =(LinearLayout)getActivity().findViewById(R.id.lt_career);
        Collegelayout =(LinearLayout)getActivity().findViewById(R.id.lt_clg);
        Districtlayout =(LinearLayout)getActivity().findViewById(R.id.lt_district);
        Aboutuslayout =(LinearLayout)getActivity().findViewById(R.id.lt_about);
        Topranklayout =(LinearLayout)getActivity().findViewById(R.id.lt_live);
        Collegecatelayout =(LinearLayout)getActivity().findViewById(R.id.lt_ranking);
        Collegeratinglayout =(LinearLayout)getActivity().findViewById(R.id.lt_rating);

        snackHome=(RelativeLayout)getActivity().findViewById(R.id.snackhome);

        im_cut =(ImageView)getActivity().findViewById(R.id.im_cut);
        im_district = (ImageView)getActivity().findViewById(R.id.im_dis);
        im_course = (ImageView)getActivity().findViewById(R.id.im_crse);
        im_college = (ImageView)getActivity().findViewById(R.id.im_clg);
        im4 = (ImageView)getActivity().findViewById(R.id.im_top);

        txtCut=(TextView)getActivity().findViewById(R.id.textcutoff);
        txtDistrict=(TextView)getActivity().findViewById(R.id.textdistrict);
        txtCourse=(TextView)getActivity().findViewById(R.id.textclg);
        txtCollege=(TextView)getActivity().findViewById(R.id.textlive);
        txtAbout=(TextView)getActivity().findViewById(R.id.textabout1);
        txtLive=(TextView)getActivity().findViewById(R.id.textcareer);
        txtHome=(TextView)getActivity().findViewById(R.id.contentHome);

        //v1=(View)getActivity().findViewById(R.id.view12);
        txtCut.setTypeface(Ty1);
        txtDistrict.setTypeface(Ty1);
        txtCourse.setTypeface(Ty1);
        txtCollege.setTypeface(Ty1);
        txtAbout.setTypeface(Ty1);
        txtLive.setTypeface(Ty1);

        txtHome.setText(R.string.home);
        txtHome.setTypeface(Ty1);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            db = new DatabaseHelper(getActivity());

        } catch (IOException e) {

            e.printStackTrace();
        }

        t1=(TextView)getActivity().findViewById(R.id.liveHome);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(snackHome,"No Internet Connection", Snackbar.LENGTH_SHORT).show();

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
        t1.setAnimation(anim);
       // blink();
        Cutofflayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mFragment = new CutoffFragment();

                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                handler.removeMessages(0);
                //handler.removeCallbacks(temp);

            }
        });

        Coursecategorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new CourseCategoryList();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        Collegelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new CollegeSearch();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        Districtlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new DistrictFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        Aboutuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new AboutusFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        Topranklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new TopRankZone();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        Collegecatelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new CateRank();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        Collegeratinglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new CateRatingCollege();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        Target viewTarget1 = new ViewTarget(R.id.lt_live, getActivity());
        showcaseView1=new ShowcaseView.Builder(getActivity())
                .setStyle(R.style.CustomShowcaseTheme)
                .setTarget(viewTarget1)
                .setContentTitle("Best colleges- Ranking and Rating")
                .setContentText("We have rated the best colleges based on their Placements, Quality, Industry Interface & Facilities.")
//                .hideOnTouchOutside()
                .singleShot(20)

                .setOnClickListener(this)
                .build();
        //Topranklayout.setClickable(false);
        showcaseView1.setShowcaseX(1);
        showcaseView1.setShowcaseY(1);
        showcaseView1.setBlocksTouches(true);
        date=getDate();
        time=getTime();
        //Toast.makeText(getActivity(), date+"  "+time,Toast.LENGTH_SHORT).show();

    }




    @Override
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView1.setShowcase(new ViewTarget(im_cut), true);
                showcaseView1.setContentTitle("Check your Cut-off");
                showcaseView1.setContentText("Check ur cutoff and choose best colleges for your score!");
                db.updateNotiDateTime(id, date, time);
               // snackHome.setClickable(false);
                Cutofflayout.setClickable(false);
                break;

            case 1:
                showcaseView1.setShowcase(new ViewTarget(im_district), true);
                showcaseView1.setContentTitle("District wise colleges");
                showcaseView1.setContentText("Check colleges & Courses available for your cut-off in your district of choice");
               // Coursecategorylayout.setClickable(false);
                Districtlayout.setClickable(false);
                break;


            case 2:
                showcaseView1.setShowcase(new ViewTarget(im_course), true);
                showcaseView1.setContentTitle("Course wise List");
                showcaseView1.setContentText("Choose the course you want to study and know the list of colleges having that course and their cut-off range");
                Coursecategorylayout.setClickable(false);
                break;

            case 3:
                showcaseView1.setShowcase(new ViewTarget(im_college), true);
                showcaseView1.setContentTitle("College wise List");
                showcaseView1.setContentText("Choose the college you want to study and know the courses if offers and their cut-off range");
                Collegelayout.setClickable(false);
                break;

            case 4:
                showcaseView1.setTarget(Target.NONE);
                showcaseView1.setContentTitle("Check it out");
                showcaseView1.setContentText("Register for our Counselling Assistant. \nWe will send you Details of good colleges \non your counselling day!");
                showcaseView1.setButtonText("Close");
//                setAlpha(0.4f, Cutofflayout, Coursecategorylayout, Collegelayout, Districtlayout, Collegecatelayout);
                break;

            case 5:
                showcaseView1.hide();
//                setAlpha(0.4f, Cutofflayout, Coursecategorylayout, Collegelayout, Districtlayout, Collegecatelayout);
               // snackHome.setClickable(true);
                Cutofflayout.setClickable(true);
                Districtlayout.setClickable(true);
                Coursecategorylayout.setClickable(true);
                Collegelayout.setClickable(true);
                Topranklayout.setClickable(true);
                break;
        }
        counter++;
    }
    public String getDate(){
        String date = null;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        date=ft.format(dNow);
//        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
        return date;
    }

    public String getTime(){
        String time = null;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss a");
        time=ft.format(dNow);
//        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
        return time;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA Home Screen");
    }

}
