package com.in22labs.tneaapp.CollegeDetails;



import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Adapter.ListCutoffAdapter;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;


public class CollegeCutProfile extends Fragment {

    ArrayList<String> CourseList, OcList, Bcm, Bc, Mbc, Sca, Sc, St;
    String CollegeCode;
    DatabaseHelper db;
    ListView listView;
    ConnectionDetector cd;
    String[] ArrCourse;
    String[] ArrOC, ArrBCM, ArrBC, ArrMBC, ArrSCA, ArrSC, ArrST;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.pastyear_layout,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getActivity().findViewById(R.id.lt_pastcutoff);
        CollegeCode=getArguments().getString("clgcode");
        //Toast.makeText(getActivity(), CollegeName,Toast.LENGTH_SHORT).show();
        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewPastProfile);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }
        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.clg_code=CollegeCode;
        CourseList=db.getCourseDetails();
        OcList=db.getOCDetails();
        Bcm=db.getBCMDetails();
        Bc=db.getBCDetails();
        Mbc=db.getMBCDetails();
        Sc=db.getSCDetails();
        St=db.getSTDetails();
        Sca=db.getSCADetails();


        ArrCourse = new String[CourseList.size()];
        ArrCourse = CourseList.toArray(ArrCourse);

        ArrOC = new String[OcList.size()];
        ArrOC = OcList.toArray(ArrOC);

        ArrBCM = new String[Bcm.size()];
        ArrBCM = Bcm.toArray(ArrBCM);

        ArrBC = new String[Bc.size()];
        ArrBC = Bc.toArray(ArrBC);

        ArrMBC = new String[Mbc.size()];
        ArrMBC = Mbc.toArray(ArrMBC);

        ArrSC = new String[Sc.size()];
        ArrSC = Sc.toArray(ArrSC);

        ArrST = new String[St.size()];
        ArrST = St.toArray(ArrST);

        ArrSCA = new String[Sca.size()];
        ArrSCA = Sca.toArray(ArrSCA);
        listView.setAdapter(new ListCutoffAdapter(getActivity(), ArrCourse, ArrOC, ArrBCM, ArrBC, ArrMBC, ArrSC, ArrSCA, ArrST));

    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA College Cut");
    }
}
