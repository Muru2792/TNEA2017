package com.in22labs.tneaapp.HomeFragments;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.R;

/**
 * Created by In22lab on 5/19/2016.
 */
public class LiveRegister extends Fragment {
    ConnectionDetector cd;
    TextView txtTilte;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_liveupdate, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Typeface Ty1 = Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Live Support");
        ab.setTypeface(Ty1);
        ab.setTextSize(18.0f);
        cd = new ConnectionDetector(getActivity());

        txtTilte=(TextView)getActivity().findViewById(R.id.txtautolink);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewRegister);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }
//        txtTilte.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str ="www.a4n.in/home/live_counselling_support";
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
//
//            }
//        });
    }
}