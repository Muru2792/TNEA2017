package com.in22labs.tneaapp.CollegeDetails;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;


public class CollegeAddressFragment extends Fragment {
    TextView txtAddress;

    TextView txtPhone;

    TextView txtFax;

    TextView txtWeb;

    TextView txtEmail;
    TextView txtCollege;

    ImageView txtBoys;
    ImageView txtGirls;
    ImageView txtTrans;
    ImageView txtChoic;
    ImageView txtMinority;

    ConnectionDetector cd;

    ImageView grade;
    DatabaseHelper db;

    ArrayList<String> CollegeList;

    String CollegeCode;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return inflater.inflate(R.layout.primary_layout,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setRetainInstance(true);
        CollegeCode=getArguments().getString("clgcode");
        cd=new ConnectionDetector(getActivity());
        txtAddress=(TextView)getActivity().findViewById(R.id.txtAddress);
        txtEmail=(TextView)getActivity().findViewById(R.id.txtMail);
        txtPhone=(TextView)getActivity().findViewById(R.id.txtPhone);
        txtFax=(TextView)getActivity().findViewById(R.id.txtFax);
        txtWeb=(TextView)getActivity().findViewById(R.id.txtSite);
        txtCollege=(TextView)getActivity().findViewById(R.id.txtClgTitle);

        txtBoys=(ImageView)getActivity().findViewById(R.id.txtBoys);
        txtGirls=(ImageView)getActivity().findViewById(R.id.txtGirls);
        txtTrans=(ImageView)getActivity().findViewById(R.id.txtTrans);
        txtChoic=(ImageView)getActivity().findViewById(R.id.txtTransChoice);
        txtMinority=(ImageView)getActivity().findViewById(R.id.txtMinorityChoice);

        grade=(ImageView)getActivity().findViewById(R.id.grade1);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewAddPro);
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
        CollegeList=db.getCollegeDetails();
        if(!CollegeList.isEmpty()){
            txtCollege.setText(CollegeCode+" - "+CollegeList.get(0));
            txtAddress.setText(CollegeList.get(1));
            txtPhone.setText(CollegeList.get(2));
            txtFax.setText(CollegeList.get(3));
            txtWeb.setText(CollegeList.get(4));
            txtEmail.setText(CollegeList.get(5));

            if(CollegeList.get(6).equals("Yes")){
                txtBoys.setBackground(getResources().getDrawable(R.drawable.done));
            }else if(CollegeList.get(6).equals("No")){
                txtBoys.setBackground(getResources().getDrawable(R.drawable.clear));
            }else if(CollegeList.get(6).equals("NA")){
                txtBoys.setBackground(getResources().getDrawable(R.drawable.na));
            }else if(CollegeList.get(6).equals("Optional")){
                txtBoys.setBackground(getResources().getDrawable(R.drawable.opt));
            }else if(CollegeList.get(6).equals("Compulsory")){
                txtBoys.setBackground(getResources().getDrawable(R.drawable.comp));
            }

//            txtGirls.setText(CollegeList.get(7));
            if(CollegeList.get(7).equals("Yes")){
                txtGirls.setBackground(getResources().getDrawable(R.drawable.done));
            }else if(CollegeList.get(7).equals("No")){
                txtGirls.setBackground(getResources().getDrawable(R.drawable.clear));
            }else if(CollegeList.get(7).equals("NA")){
                txtGirls.setBackground(getResources().getDrawable(R.drawable.na));
            }else if(CollegeList.get(7).equals("Optional")){
                txtGirls.setBackground(getResources().getDrawable(R.drawable.opt));
            }else if(CollegeList.get(7).equals("Compulsory")){
                txtGirls.setBackground(getResources().getDrawable(R.drawable.comp));
            }
//            txtTrans.setText(CollegeList.get(8));
            if(CollegeList.get(8).equals("Yes")){
                txtTrans.setBackground(getResources().getDrawable(R.drawable.done));
            }else if(CollegeList.get(8).equals("No")){
                txtTrans.setBackground(getResources().getDrawable(R.drawable.clear));
            }else if(CollegeList.get(8).equals("NA")){
                txtTrans.setBackground(getResources().getDrawable(R.drawable.na));
            }else if(CollegeList.get(8).equals("Optional")){
                txtTrans.setBackground(getResources().getDrawable(R.drawable.opt));
            }else if(CollegeList.get(8).equals("Compulsory")){
                txtTrans.setBackground(getResources().getDrawable(R.drawable.comp));
            }
//            txtChoic.setText(CollegeList.get(9));
            if(CollegeList.get(9).equals("Yes")){
                txtChoic.setBackground(getResources().getDrawable(R.drawable.done));
            }else if(CollegeList.get(9).equals("No")){
                txtChoic.setBackground(getResources().getDrawable(R.drawable.clear));
            }else if(CollegeList.get(9).equals("NA")){
                txtChoic.setBackground(getResources().getDrawable(R.drawable.na));
            }else if(CollegeList.get(9).equals("Optional")){
                txtChoic.setBackground(getResources().getDrawable(R.drawable.opt));
            }else if(CollegeList.get(9).equals("Compulsory")){
                txtChoic.setBackground(getResources().getDrawable(R.drawable.comp));
            }
//            txtMinority.setText(CollegeList.get(10));
            if(CollegeList.get(10).equals("Yes")){
                txtMinority.setBackground(getResources().getDrawable(R.drawable.done));
            }else if(CollegeList.get(10).equals("No")){
                txtMinority.setBackground(getResources().getDrawable(R.drawable.clear));
            }else if(CollegeList.get(10).equals("NA")){
                txtMinority.setBackground(getResources().getDrawable(R.drawable.na));
            }else if(CollegeList.get(10).equals("Optional")){
                txtMinority.setBackground(getResources().getDrawable(R.drawable.opt));
            }else if(CollegeList.get(10).equals("Compulsory")){
                txtMinority.setBackground(getResources().getDrawable(R.drawable.comp));
            }


        }else{
          //  Toast.makeText(getActivity(),"No College Data",Toast.LENGTH_SHORT).show();
        }



    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA College Address");
    }
}
