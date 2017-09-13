package com.in22labs.tneaapp.HomeFragments;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

/**
 * Created by Ratan on 7/29/2015.
 */
public class AboutusFragment extends Fragment {
    ConnectionDetector cd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aboutus_layout,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("About US");
        ab.setTypeface(Ty1);
        ab.setTextSize(18.0f);
        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewAbout);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        }
        TextView tv1 = (TextView) getActivity().findViewById(R.id.textaaa);

        tv1.setText("Apples 4 Newtons is the first holistic, complete guide for Higher education in Tamilnadu. " +
                "We bring you detailed analysis on everything related to your higher education choice. " +
                "\nWe bring in more than 30 years of experience in Indian Higher Education Industry and we are the only Higher education counselors, " +
                "who has extensive coverage on Institutions and corporate.");
        tv1.setTypeface(Ty1);
        tv1.setTextSize(14.0f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("A4N About Us");
    }
}
