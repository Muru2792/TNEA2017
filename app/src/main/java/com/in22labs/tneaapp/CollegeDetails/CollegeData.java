package com.in22labs.tneaapp.CollegeDetails;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;

import java.io.IOException;


public class CollegeData extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4 ;
    public String CollegeName= null;
    public String CollegeCode = null;
    DatabaseHelper db;

    String Index;
    InterstitialAd interAd;
    ConnectionDetector cd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("College Profile");

        ab.setTextSize(20.0f);
        cd=new ConnectionDetector(getActivity());


        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        try {
            db = new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Index = getArguments().getString("Index");
        if(Index.equals("1")){
            CollegeCode= getArguments().getString("clgcode");

            if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                if (!cd.isConnectingToInternet()) {
                    // Internet Connection is not present

                } else {
                    //fullBanner();
                }
            }


        }else if(Index.equals("2")) {
            CollegeName = getArguments().getString("clgname");
            CollegeCode= db.getCollegeCode(CollegeName);
        }
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 :
                    CollegeAddressFragment p1 = new CollegeAddressFragment();

                    final Bundle b1 = new Bundle();
                    b1.putString("clgcode", CollegeCode);
                    p1.setArguments(b1);
                    return p1;
                case 1 :
                    CollegeCutProfile p2 = new CollegeCutProfile();

                    final Bundle b2 = new Bundle();
                    b2.putString("clgcode", CollegeCode);
                    p2.setArguments(b2);
                    return p2;
                case 2 :
                    CourseIntakeFragment p3 = new CourseIntakeFragment();

                    final Bundle b3 = new Bundle();
                    b3.putString("clgcode", CollegeCode);
                    p3.setArguments(b3);
                    return p3;

                case 3 :
                    CollegeRank p4 = new CollegeRank();

                    final Bundle b4 = new Bundle();
                    b4.putString("clgcode", CollegeCode);
                    p4.setArguments(b4);
                    return p4;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "College\nAddress";
                case 1 :
                    return "Previous Year\nCutoff";
                case 2 :
                    return "Course\nIntake";
                case 3:
                    return "College\nRank";
            }
            return null;
        }
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
