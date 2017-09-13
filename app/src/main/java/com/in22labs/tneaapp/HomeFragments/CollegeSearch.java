package com.in22labs.tneaapp.HomeFragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.CollegeDetails.CollegeData;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

/**
 * Created by In22lab on 4/25/2016.
 */
public class CollegeSearch extends Fragment {
    EditText edtClgCode;
    ArrayList<String> city, clgname, list, list1;

    Button bt_search;
    String  temp;
    DatabaseHelper db;
    public RadioGroup RadioCollege;
    RadioButton RadioName, RadioCode;
    LinearLayout lt_auto;
    LinearLayout lt_code;
    RelativeLayout lt1;
    final Handler handler = new Handler();
    TextView t2;
    int i;
    ConnectionDetector cd;
    TextView txtContent;
    TextView txtHint, txtCodeTitle, txtOr, txtNameTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.collegesearch,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewClgSearch);
                AdRequest adRequest = new AdRequest.Builder().build();

                mAdView.loadAd(adRequest);
            }
        }

        t2=(TextView)getActivity().findViewById(R.id.liveCollege);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(lt1,"No Internet Connection", Snackbar.LENGTH_SHORT).show();

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
        ab.setText("Search By College");
        ab.setTypeface(Ty1);
        ab.setTextSize(20.0f);
        lt1=(RelativeLayout)getActivity().findViewById(R.id.search_college);
        lt_auto=(LinearLayout)getActivity().findViewById(R.id.lt_autoName);
        lt_code=(LinearLayout)getActivity().findViewById(R.id.lt_autoCode);
//        edtClgCode=(EditText)getActivity().findViewById(R.id.edt_clgcode);
        final AutoCompleteTextView CollegeName=(AutoCompleteTextView)getActivity().findViewById(R.id.autoCollegeName);
        final AutoCompleteTextView CollegeCode=(AutoCompleteTextView)getActivity().findViewById(R.id.autoCollegeCode);
        bt_search=(Button)getActivity().findViewById(R.id.btn_search);

        txtHint=(TextView)getActivity().findViewById(R.id.txtClgHint);
        txtCodeTitle=(TextView)getActivity().findViewById(R.id.txtClgCollegeTitle);
        txtOr=(TextView)getActivity().findViewById(R.id.txtclgOrTitle);
        txtNameTitle=(TextView)getActivity().findViewById(R.id.txtclgNameTitle);

        //lt1=(LinearLayout)getActivity().findViewById(R.id.ltalign);
        txtHint.setTypeface(Ty1);
        txtCodeTitle.setTypeface(Ty1);
        txtOr.setTypeface(Ty1);
        txtNameTitle.setTypeface(Ty1);
        bt_search.setTypeface(Ty1);


        txtContent=(TextView)getActivity().findViewById(R.id.textContentCollege);
        txtContent.setText("Colleges - Ranking, Rating & Cut-offs");
        txtContent.setTypeface(Ty1);
        txtContent.setTextSize(15.5f);

        RadioCollege = (RadioGroup)getActivity().findViewById(R.id.radioGroup);
        RadioName = (RadioButton)getActivity().findViewById(R.id.nameSearch);
        RadioCode = (RadioButton)getActivity().findViewById(R.id.codeSearch);
        RadioCollege.clearCheck();



        try {
            db = new DatabaseHelper(getActivity());
//            db.copyDataBase();

        } catch (IOException e) {

            e.printStackTrace();
        }
//        final String Stw1;
        clgname=db.getClgNameNew();
        list = db.getCollegeCodeNew();
        //Toast.makeText(getContext(), clgname.get(0),Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(),R.layout.list_courseauto, R.id.list_course_auto,clgname);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_spinner_dropdown_item,list);

        CollegeName.setThreshold(0);//will start working from first character
        CollegeName.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//        CollegeName.setTextColor(Color.RED);

//        if(RadioCode.isChecked()){
//            CollegeCode.setEnabled(true);
//            CollegeName.setEnabled(false);
//            CollegeName.setText("");
//            CollegeCode.requestFocus();
////
//        }else if(RadioName.isChecked()){
//            CollegeCode.setEnabled(false);
//            CollegeName.setEnabled(true);
//            CollegeCode.setText("");
//            CollegeName.requestFocus();
////
//        }else{
//            CollegeCode.setEnabled(false);
//            CollegeName.setEnabled(false);
//            CollegeName.setText("");
//            CollegeCode.setText("");
//
//        }


        if(!RadioName.isChecked() && !RadioCode.isChecked()){
            CollegeCode.setEnabled(false);
            CollegeName.setEnabled(false);
            CollegeName.setText("");
            CollegeCode.setText("");
        }

        RadioCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollegeCode.setEnabled(true);
                CollegeName.setEnabled(false);
                CollegeName.setText("");
                CollegeCode.requestFocus();
//

            }
        });

        RadioName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollegeCode.setEnabled(false);
                CollegeName.setEnabled(true);
                CollegeCode.setText("");
                CollegeName.requestFocus();
//
            }
        });

        lt_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(false == CollegeName.isEnabled()){
                    RadioName.requestFocus();
                    Snackbar.make(lt1, "Enable Name Search",Snackbar.LENGTH_LONG).show();
                }
               // Snackbar.make(lt1, "Enable Name Search",Snackbar.LENGTH_LONG).show();
            }
        });


        lt_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(false == CollegeCode.isEnabled()){
                    Snackbar.make(lt1, "Enable Code Search",Snackbar.LENGTH_LONG).show();
                }
               // Snackbar.make(lt1, "Enable Name Search",Snackbar.LENGTH_LONG).show();
            }
        });



        CollegeName.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();

//                String stw1 = CollegeName.getText().toString();
//                Toast.makeText(getActivity(), stw1, Toast.LENGTH_SHORT).show();
//                CollegeCode.setEnabled(false);
//                CollegeName.setEnabled(true);

            }
        });

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        CollegeCode.setThreshold(0);//will start working from first character
        CollegeCode.setAdapter(adapter1);//setting the adapter data into the AutoCompleteTextView
//        CollegeName.setTextColor(Color.RED);
        CollegeCode.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();

//                String stw1 = CollegeCode.getText().toString();
//                Toast.makeText(getActivity(), stw1, Toast.LENGTH_SHORT).show();
                //edtClgCode.setEnabled(false);
//                CollegeCode.setEnabled(true);
//                CollegeName.setEnabled(false);

            }
        });


        //        final String s11=CollegeName.getText().toString();
//        final String s2=edtClgCode.getText().toString();
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String s11=CollegeName.getText().toString();
                final String s2=CollegeCode.getText().toString();
                Boolean con;
                int sucess = 0;
                String Index;
                for(int i =0; i<list.size();i++)
                {
                    if(s2.equals(list.get(i).toString()))
                        sucess = 1;

                }

                if(s11.isEmpty() && s2.isEmpty()){

                    Snackbar.make(lt1, "Enter College code or College Name",Snackbar.LENGTH_LONG).show();
                   // Toast.makeText(getContext(), "Enter College code or College Name", Toast.LENGTH_SHORT).show();
                }else if(s11.isEmpty()){


                    if(sucess==1){
                        RadioCollege.clearCheck();
                        CollegeName.setText("");
                        CollegeCode.setText("");
                        Fragment mFragment = new CollegeData();
                        Index = "1";
                        final Bundle b1 = new Bundle();
                        b1.putString("Index", Index);
                        b1.putString("clgcode", s2);
                        mFragment.setArguments(b1);
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, mFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }else {
                        Snackbar.make(lt1, "Enter Correct College Code", Snackbar.LENGTH_SHORT).show();
                        CollegeCode.requestFocus();
                    }
                }else if(s2.isEmpty()) {
                    //Toast.makeText(getContext(), s11,Toast.LENGTH_SHORT).show();
                    RadioCollege.clearCheck();
                    CollegeName.setText("");
                    CollegeCode.setText("");
                    Fragment mFragment = new CollegeData();
                    Index = "2";
                    final Bundle b1= new Bundle();
                    b1.putString("Index", Index);
                    b1.putString("clgname", s11);
                    mFragment.setArguments(b1);
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, mFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else{
                    Snackbar.make(lt1, "Enter Either One of Field", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "Enter Either One Of Field",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA College By Search");
    }
    private void blink(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                final int color= Color.parseColor("#FFFFFF");
                int timeToBlink = 700;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        TextView txt = (TextView) getActivity().findViewById(R.id.liveCollege);
                        if(t2.getCurrentTextColor() == color){
                            t2.setTextColor(Color.parseColor("#66000000"));
                        }else{
                            t2.setTextColor(color);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
}
