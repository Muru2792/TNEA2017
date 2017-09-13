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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

/**
 * Created by In22lab on 5/16/2016.
 */
public class CateRank extends Fragment {
    ConnectionDetector cd;
    LinearLayout im1, im2, im3, im4;
    TextView t2;
    RelativeLayout tle;
    InterstitialAd interAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lay_caterank,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        tle=(RelativeLayout)getActivity().findViewById(R.id.snackrank);

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adViewcateRank);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
                fullBanner();
            }
        }

        t2=(TextView)getActivity().findViewById(R.id.liveRank);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(tle, "No Internet Connection", Snackbar.LENGTH_SHORT).show();

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
        ab.setText("A4N College Ranking");
        ab.setTypeface(Ty1);
        ab.setTextSize(20.0f);

        im1=(LinearLayout)getActivity().findViewById(R.id.plcame_rating);
        im2=(LinearLayout)getActivity().findViewById(R.id.indust_rating);
        im3=(LinearLayout)getActivity().findViewById(R.id.qual_rating);
        im4=(LinearLayout)getActivity().findViewById(R.id.facility_rating);


        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RatingsCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "1");
                b1.putString("Rating", "Placement");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RatingsCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "2");
                b1.putString("Rating", "Industry");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RatingsCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "3");
                b1.putString("Rating", "Quality");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RatingsCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "4");
                b1.putString("Rating", "Facilities");
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
