package com.in22labs.tneaapp.HomeFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.District.DistrictCollegeList;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by In22lab on 4/25/2016.
 */
public class DistrictFragment extends Fragment {
    EditText cutoff;
    String cutoff1, district1, course1;
    DatabaseHelper db;
    TextView txtContent,txtfab,t2;
    Button btn_distSearch;
    public RadioGroup RadioCollege;
    int marks;
    final Handler handler = new Handler();
    public float s4;
    RadioButton RadioName, RadioCode;
    ArrayList<String> list, coursename, district;
    private ShowcaseView showcaseView3;
    private int counter = 0;
    RelativeLayout rt1;
    ConnectionDetector cd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.districtfragment,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cd= new ConnectionDetector(getActivity());
        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present
            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adViewHomeDist);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);

                }
            }
        t2=(TextView)getActivity().findViewById(R.id.liveDistrict);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(rt1,"No Internet Connection", Snackbar.LENGTH_SHORT).show();

                } else {
                    String str = "http://tnea.a4n.in/home/live_counselling_support";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
                }

            }
        });
//        blink();
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(550);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        t2.setAnimation(anim);

        Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Search College By District");
        ab.setTypeface(Ty1);
        ab.setTextSize(20.0f);
        cutoff=(EditText)getActivity().findViewById(R.id.edt_discutoff);
        btn_distSearch=(Button)getActivity().findViewById(R.id.btn_districtsearch);
        FloatingActionButton fabDist =(FloatingActionButton)getActivity().findViewById(R.id.fab_dist);
        rt1=(RelativeLayout)getActivity().findViewById(R.id.dist_snack);
        fabDist.setBackgroundColor(Color.parseColor("#ffffff"));

        Target viewTarget3 = new ViewTarget(R.id.fab_dist, getActivity());
        showcaseView3=new ShowcaseView.Builder(getActivity())
                .setStyle(R.style.CustomShowcaseTheme)
                .setTarget(viewTarget3)
                .setContentTitle("District")
                .setContentText("You can search colleges based on your cutoff and courses availability as well.")
                .singleShot(10)
                .build();

        showcaseView3.setShowcaseX(1);
        showcaseView3.setShowcaseY(1);


        txtContent=(TextView)getActivity().findViewById(R.id.txtContenDistrict);
        txtfab=(TextView)getActivity().findViewById(R.id.distfab_title);
        txtContent.setText("Best colleges - District wise - Ratings and Rankings");

        txtfab.setText("Check your cutoff with our cutoff calculator  →");
        txtContent.setTypeface(Ty1);
        txtContent.setTextSize(15.5f);
        txtfab.setTextSize(16.0f);
        final AutoCompleteTextView edtCity = (AutoCompleteTextView)getActivity().findViewById(R.id.autoDistrict1);
        final AutoCompleteTextView edtCourse = (AutoCompleteTextView)getActivity().findViewById(R.id.autoCoursName);
        RadioName = (RadioButton)getActivity().findViewById(R.id.cutoffSearch);
        RadioCode = (RadioButton)getActivity().findViewById(R.id.courseSearch);
        RadioCollege = (RadioGroup)getActivity().findViewById(R.id.radioGroup1);
        RadioCollege.clearCheck();
        try {
            db = new DatabaseHelper(getActivity());
//            db.copyDataBase();

        } catch (IOException e) {

            e.printStackTrace();
        }
        if(RadioCode.isChecked()){
            cutoff.setEnabled(false);
            edtCourse.setEnabled(true);
            cutoff.setText("");
            edtCourse.requestFocus();
//
        }else if(RadioName.isChecked()){

            cutoff.setEnabled(true);
            edtCourse.setEnabled(false);
            edtCourse.setText("");
            cutoff.requestFocus();
//
        }else{
            cutoff.setEnabled(false);
            edtCourse.setEnabled(false);
            cutoff.setText("");
            edtCourse.setText("");

        }

        RadioCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutoff.setEnabled(false);
                edtCourse.setEnabled(true);
                cutoff.setText("");
                edtCourse.requestFocus();
//

            }
        });

        RadioName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cutoff.setEnabled(true);
                edtCourse.setEnabled(false);
                edtCourse.setText("");
                cutoff.requestFocus();
//
            }
        });
        district= db.getCityNew();
        coursename=db.getCourseNew();

//        cutoff.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId==EditorInfo.IME_ACTION_DONE){
//
//                    return true;
//                }
//                return false;
//            }
//        });

        ArrayAdapter<String> adpDistrict = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_dropdown_item_1line, district);

        ArrayAdapter<String> adpCourse = new ArrayAdapter<String>
                (getActivity(), R.layout.list_courseauto, R.id.list_course_auto, coursename);


        edtCity.setThreshold(1);
        edtCity.setAdapter(adpDistrict);

        edtCourse.setThreshold(1);
        edtCourse.setAdapter(adpCourse);
//        fabDi
        fabDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioName.setChecked(true);
                cutoff.setEnabled(true);
                marks = showCutOffCalc(0);

            }
        });

        btn_distSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float temp=0.0f;
                district1=edtCity.getText().toString();
                course1=edtCourse.getText().toString();
                 cutoff1=cutoff.getText().toString();
                if(!cutoff1.isEmpty()){
                    temp=Float.valueOf(cutoff1);
                }

                if(!RadioCode.isEnabled() ||!RadioName.isEnabled()){
                    Snackbar.make(rt1,"Select Either Choice", Snackbar.LENGTH_SHORT).show();
                }else if(cutoff1.isEmpty() == false && course1.isEmpty() == false) {
                        Snackbar.make(rt1, "Enter Either One Field of Course name or cutoff", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(), "Enter Either One Field of Course name or cutoff", Toast.LENGTH_SHORT).show();
                    }else if(district1.isEmpty() == false && course1.isEmpty() == false){
                        RadioCollege.clearCheck();
                        edtCourse.setText("");
                        cutoff.setText("");
                        edtCity.setText("");
                        Fragment mFragment = new DistrictCollegeList();
                        final Bundle b1 = new Bundle();
//                        b1.putString("cutoff", cutoff1);
                        b1.putString("city", district1);
                        b1.putString("Course", course1);
                        b1.putString("switch", "1");
                        mFragment.setArguments(b1);
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, mFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        //Toast.makeText(getActivity(), course1+" "+ district1+" "+1, Toast.LENGTH_SHORT).show();
                    }else if (cutoff1.isEmpty()==false  && district1.isEmpty() == false && temp<=200.00) {
                        RadioCollege.clearCheck();

                        edtCourse.setText("");
                        cutoff.setText("");
                        edtCity.setText("");
                        Fragment mFragment = new DistrictCollegeList();
                        final Bundle b1 = new Bundle();
                        b1.putString("cutoff", cutoff1);
                        b1.putString("city", district1);
//                        b1.putString("Course", course1);
                        b1.putString("switch", "2");
                        mFragment.setArguments(b1);
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, mFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                            //Toast.makeText(getActivity(), cutoff1+" "+ district1+" "+1, Toast.LENGTH_SHORT).show();
                    }else if(district1.isEmpty()){
                        Snackbar.make(rt1, "Enter City Details", Snackbar.LENGTH_SHORT).show();
                       // Toast.makeText(getActivity(), "Enter City Details", Toast.LENGTH_SHORT).show();
                        edtCity.requestFocus();
                    }else if(temp>200.00){
                        Snackbar.make(rt1, "Marks should be below 201", Snackbar.LENGTH_SHORT).show();
                        // Toast.makeText(getActivity(), "Enter City Details", Toast.LENGTH_SHORT).show();
                        cutoff.setText("");
                        cutoff.requestFocus();
                    }else{
                        Snackbar.make(rt1,"Fill All Data", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(), "Fill All Data", Toast.LENGTH_SHORT).show();
                        edtCity.requestFocus();
                    }

                }

            });



    }
    public int showCutOffCalc(int i) {

        final EditText m1, p1, c1;
        Button btn1;


        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.cutcalc, null);

        final Dialog popup = new Dialog(getActivity(), R.style.FullHeightDialog);
        popup.setContentView(layout);
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        popup.show();

        m1=(EditText)layout.findViewById(R.id.et_maths);
        p1=(EditText)layout.findViewById(R.id.et_phy);
        c1=(EditText)layout.findViewById(R.id.et_che);
        btn1=(Button)layout.findViewById(R.id.btn_calc);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp1, t2, t3,t4;
                temp1 = m1.getText().toString();
                t2 = p1.getText().toString();
                t3 = c1.getText().toString();
                if(temp1.isEmpty()){
                    m1.requestFocus();
                    Snackbar.make(layout, "Enter Mathmatics Marks", Snackbar.LENGTH_SHORT).show();
                }else if(t2.isEmpty()){
                    p1.requestFocus();
                    Snackbar.make(layout, "Enter Physics Marks", Snackbar.LENGTH_SHORT).show();
                } else if(t3.isEmpty()){
                    c1.requestFocus();
                    Snackbar.make(layout, "Enter Chemistry Marks", Snackbar.LENGTH_SHORT).show();
                }else{
                    float s1 = Float.parseFloat(temp1);
                    float s2 = Float.parseFloat(t2);
                    float s3 = Float.parseFloat(t3);
                    if(s1>200.00){
                        m1.requestFocus();
                        m1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        m1.setText("");
                        Snackbar.make(layout, "Marks Should be Below 201", Snackbar.LENGTH_SHORT).show();
                    }

                    else if(s2>200.00){
                        p1.requestFocus();
                        p1.setText("");
                        Snackbar.make(layout, "Marks Should be Below 201", Snackbar.LENGTH_SHORT).show();
                        p1.setImeOptions(EditorInfo.IME_ACTION_DONE);

                    }

                    else if(s3>200.00){
                        c1.requestFocus();
                        c1.setText("");
                        Snackbar.make(layout, "Marks Should be Below 201", Snackbar.LENGTH_SHORT).show();
                        c1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    }
                    else {
                        s4 = (s1 / 2) + (s2 / 4) + (s3 / 4);
                        t4 = String.valueOf(s4);
                        cutoff.setText(t4);
                        popup.dismiss();
                    }
                }

            }

        });
        //Toast.makeText(getActivity(), s4, Toast.LENGTH_SHORT).show();
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Search By District");
    }
    private void blink(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                final int color=Color.parseColor("#FFFFFF");
                int timeToBlink = 700;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        TextView txt = (TextView) getActivity().findViewById(R.id.liveDistrict);
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
