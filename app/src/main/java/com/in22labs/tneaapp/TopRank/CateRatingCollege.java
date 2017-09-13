package com.in22labs.tneaapp.TopRank;

import android.content.Intent;
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
 * Created by In22lab on 5/18/2016.
 */
public class CateRatingCollege extends Fragment {

    ConnectionDetector cd;
    TextView t2;
    RelativeLayout tlll;
    LinearLayout lt_aaplus, lt_aa, lt_a, lt_bplus, lt_b, lt_c, lt_d;
    InterstitialAd interAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_rating,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tlll=(RelativeLayout)getActivity().findViewById(R.id.snackRating);
        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
                fullBanner();
            }
        }

        t2=(TextView)getActivity().findViewById(R.id.liveRating);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(tlll, "No Internet Connection", Snackbar.LENGTH_SHORT).show();

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

        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("A4N College Rating");
        ab.setTextSize(20.0f);


        lt_aaplus=(LinearLayout)getActivity().findViewById(R.id.lt_aplus);
        lt_aa=(LinearLayout)getActivity().findViewById(R.id.lt_aa);
        lt_a=(LinearLayout)getActivity().findViewById(R.id.lt_a);
        lt_bplus=(LinearLayout)getActivity().findViewById(R.id.lt_bplus);
        lt_b=(LinearLayout)getActivity().findViewById(R.id.lt_b);
        lt_c=(LinearLayout)getActivity().findViewById(R.id.lt_c);
        lt_d=(LinearLayout)getActivity().findViewById(R.id.lt_d);


        lt_aaplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RankingCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "1");
                b1.putString("Ranking", "AAPLUS");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });lt_aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RankingCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "2");
                b1.putString("Ranking", "AA");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });lt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RankingCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "3");
                b1.putString("Ranking", "A");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });lt_bplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RankingCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "4");
                b1.putString("Ranking", "BPLUS");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });lt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RankingCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "5");
                b1.putString("Ranking", "B");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });lt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RankingCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "6");
                b1.putString("Ranking", "C");
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });lt_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new RankingCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("Index", "7");
                b1.putString("Ranking", "D");
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
        MyApplication.getInstance().trackScreenView("TNEA Rating Search");
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
