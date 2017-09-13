package com.in22labs.tneaapp.HomeFragments;


import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {

    ConnectionDetector cd;

    public OtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other, container, false);
        Typeface Ty1 = Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Other Products");
        ab.setTypeface(Ty1);
        ab.setTextSize(18.0f);


        LinearLayout LLT=(LinearLayout)rootView.findViewById(R.id.ltother);
        LinearLayout LLT1=(LinearLayout)rootView.findViewById(R.id.ltother1);
        LinearLayout LLT2=(LinearLayout)rootView.findViewById(R.id.ltother2);
        LinearLayout LLT3=(LinearLayout)rootView.findViewById(R.id.ltother3);
        LinearLayout LLT4=(LinearLayout)rootView.findViewById(R.id.ltother4);

        LLT.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String str ="https://play.google.com/store/apps/details?id=com.in22labs.aptitudelp";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            }

        });

        LLT1.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String str ="https://play.google.com/store/apps/details?id=com.in22labs.ibpspro";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            }

        });

        LLT2.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String str ="https://play.google.com/store/apps/details?id=com.in22labs.companypattern";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            }

        });


        LLT3.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String str ="https://play.google.com/store/apps/details?id=com.in22labs.ibpsplusinapp";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            }

        });
        LLT4.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String str ="https://play.google.com/store/apps/details?id=com.in22labs.ResumeBuilder";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            }

        });

        return rootView;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

		cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adViewothers);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Our Other Products");
    }

}
