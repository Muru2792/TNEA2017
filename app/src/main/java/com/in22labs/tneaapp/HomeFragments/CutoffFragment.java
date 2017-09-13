package com.in22labs.tneaapp.HomeFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;
import com.in22labs.tneaapp.college.CollegeListView;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class CutoffFragment extends Fragment implements OnClickListener{


    EditText e1;
    Spinner SpinCommunity;
    Button b1;
    TextView t1, txtCutTitle, txtCourseTitle, txtComTitle,t2;
    int b=0;
    String com, cutoff,course;
    ArrayList<String> list, temp, community;
    RelativeLayout Rt1;
    DatabaseHelper db;
    private ShowcaseView showcaseView2;
    private int counter = 0;
    public float s4;
    ConnectionDetector cd;
    FloatingActionButton fab;
    final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cutoff_layout,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        e1 = (EditText)getActivity().findViewById(R.id.cutoff);

        SpinCommunity=(Spinner)getActivity().findViewById(R.id.spin_community);

        b1 = (Button)getActivity().findViewById(R.id.btn_sub);

        t1 = (TextView)getActivity().findViewById(R.id.txtContentCutoff);

        Rt1=(RelativeLayout)getActivity().findViewById(R.id.lay_cutoff);
        txtCutTitle = (TextView)getActivity().findViewById(R.id.txtCutTitle1);
        txtCourseTitle = (TextView)getActivity().findViewById(R.id.txtCurseTitle1);
        txtComTitle = (TextView)getActivity().findViewById(R.id.txtComtitle1);

        t2=(TextView)getActivity().findViewById(R.id.liveCutoff);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(Rt1,"No Internet Connection", Snackbar.LENGTH_SHORT).show();

                } else {
                    String str = "http://tnea.a4n.in/home/live_counselling_support";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
                }

            }
        });
       // blink();
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(550);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        t2.setAnimation(anim);

        fab =(FloatingActionButton)getActivity().findViewById(R.id.fab_cut);
        final Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Search Colleges By Cutoff");
        ab.setTextSize(20.0f);
        ab.setTypeface(Ty1);

        Target viewTarget1 = new ViewTarget(R.id.fab_cut, getActivity());
        showcaseView2=new ShowcaseView.Builder(getActivity())
                .setStyle(R.style.CustomShowcaseTheme)
                .setTarget(viewTarget1)
                .setContentTitle("Cutoff")
                .setContentText("You can search colleges based on your cutoff and courses availability as well.")
                .singleShot(10)
                .setOnClickListener(this)
                .build();
        fab.setClickable(false);
        showcaseView2.setShowcaseX(1);
        showcaseView2.setShowcaseY(1);




        t1.setText("Check your cutoff with our cutoff calculator  →");
        t1.setTypeface(Ty1);
        t1.setTextSize(15.5f);
        txtCutTitle.setTypeface(Ty1);
        txtCourseTitle.setTypeface(Ty1);
        txtComTitle.setTypeface(Ty1);
        b1.setTypeface(Ty1);
        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present
                Snackbar.make(Rt1, "No Internet Connection", Snackbar.LENGTH_SHORT).show();

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewHomeCutoff);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }
        final AutoCompleteTextView Course=(AutoCompleteTextView)getActivity().findViewById(R.id.autoCourseList);
//        final AutoCompleteTextView Course=(AutoCompleteTextView)getActivity().findViewById(R.id.autoCourseList);
        try {
            db = new DatabaseHelper(getActivity());
//            db.copyDataBase();

        } catch (IOException e) {

            e.printStackTrace();
        }



        temp = db.getCourseNew();


//        list = new ArrayList();
//        list.add("Select City");
//        for(String j : pastyear_layout) {
//           list.add(j);
//        }
        community = new ArrayList<String>();
        community.add(0,"Select Community");
        community.add(1,"OC");
        community.add(2,"BCM");
        community.add(3,"BC");
        community.add(4, "MBC");
        community.add(5, "SC");
        community.add(6, "SCA");
        community.add(7, "ST");






        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_dropdown_item_1line, community);

        SpinCommunity.setAdapter(adp);



        SpinCommunity.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
                com = String.valueOf(SpinCommunity.getSelectedItem());
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(e1.getWindowToken(), 0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        e1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_NEXT){
                    cutoff = e1.getText().toString();
                    if(!cutoff.isEmpty()) {
                        float temp1 = Float.valueOf(cutoff);
                        if (temp1 <= 200.00) {
                            Course.requestFocus();
                        } else {
                            e1.setText("");
                            e1.requestFocus();
                            Snackbar.make(Rt1, "Cut Off Mark below 200", Snackbar.LENGTH_SHORT).show();
                        }

                    }else{
                        e1.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });

         ArrayAdapter<String> City = new ArrayAdapter<String>
                (getActivity(), R.layout.list_courseauto, R.id.list_course_auto, temp);
        Course.setThreshold(2);
//        Course.se
        Course.setAdapter(City);
        Course.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(Course, 0);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = showCutOffCalc(0);

            }
        });
        fab.isInEditMode();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float temp1=0.0f;

                cutoff = e1.getText().toString();
                course = Course.getText().toString();
                if(!cutoff.isEmpty()){
                    temp1=Float.valueOf(cutoff);

                }


                if (!cutoff.isEmpty() && com != "Select Community" && !course.isEmpty() && temp1<=200.00) {


//                    Toast.makeText(getActivity(), com + "  " + cutoff+ "  "+ course, Toast.LENGTH_SHORT).show();
//                db.cutoff = cutoff;
//                db.city = sp3;
                    Fragment mFragment = new CollegeListView();
                    final Bundle b1 = new Bundle();
                    b1.putString("cutoff", cutoff);
                    b1.putString("course", course);
                    b1.putString("community", com);
                    //b1.putInt("switch", 1);
                    mFragment.setArguments(b1);
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, mFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else if (cutoff.isEmpty()) {
//                    Toast.makeText(getActivity(), "Enter Cut Off Marks",Toast.LENGTH_SHORT).show();
                    Snackbar.make(Rt1, "Enter Cut Off Marks", Snackbar.LENGTH_SHORT).show();
                    e1.requestFocus();
                } else if(temp1>200.00){
                    e1.setText("");
                    Snackbar.make(Rt1, "Enter Cut Off Marks", Snackbar.LENGTH_SHORT).show();
                    e1.requestFocus();
                }else if (course.isEmpty()) {
//                    Toast.makeText(getActivity(), "Enter Course",Toast.LENGTH_SHORT).show();
                    Snackbar.make(Rt1, "Enter Course", Snackbar.LENGTH_SHORT).show();
                    Course.requestFocus();
                } else if (com == "Select Community") {
//                    Toast.makeText(getActivity(), "Enter Community",Toast.LENGTH_SHORT).show();
                    Snackbar.make(Rt1, "Enter Community", Snackbar.LENGTH_SHORT).show();
                    SpinCommunity.requestFocus();
                    SpinCommunity.performClick();
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


        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp1, t2, t3, t4;
                temp1 = m1.getText().toString();
                t2 = p1.getText().toString();
                t3 = c1.getText().toString();


                if (temp1.isEmpty()) {
                    m1.requestFocus();
                    Snackbar.make(layout, "Enter Mathmatics Marks", Snackbar.LENGTH_SHORT).show();
                } else if (t2.isEmpty()) {
                    p1.requestFocus();
                    Snackbar.make(layout, "Enter Physics Marks", Snackbar.LENGTH_SHORT).show();
                } else if (t3.isEmpty()) {
                    c1.requestFocus();
                    Snackbar.make(layout, "Enter Chemistry Marks", Snackbar.LENGTH_SHORT).show();
                } else {
                    float s1 = Float.parseFloat(temp1);
                    float s2 = Float.parseFloat(t2);
                    float s3 = Float.parseFloat(t3);
                    if (s1 > 200.00) {
                        m1.requestFocus();
                        m1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        m1.setText("");
                        Snackbar.make(layout, "Marks Should be Below 201", Snackbar.LENGTH_SHORT).show();
                    } else if (s2 > 200.00) {
                        p1.requestFocus();
                        p1.setText("");
                        Snackbar.make(layout, "Marks Should be Below 201", Snackbar.LENGTH_SHORT).show();
                        p1.setImeOptions(EditorInfo.IME_ACTION_DONE);

                    } else if (s3 > 200.00) {
                        c1.requestFocus();
                        c1.setText("");
                        Snackbar.make(layout, "Marks Should be Below 201", Snackbar.LENGTH_SHORT).show();
                        c1.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    }

                    else{
                        s4 = (s1 / 2) + (s2 / 4) + (s3 / 4);
                        t4 = String.valueOf(s4);
                        e1.setText(t4);
                        popup.dismiss();
                    }
                }

            }

        });


        //Toast.makeText(getActivity(), s4, Toast.LENGTH_SHORT).show();
        return 0;
    }


    public void onClick(View v) {
        switch (counter) {
            case 0:

                showcaseView2.hide();
                fab.setClickable(true);
//                setAlpha(0.4f, Cutofflayout, Coursecategorylayout, Collegelayout, Districtlayout, Collegecatelayout);
                // snackHome.setClickable(true);

                break;
        }
        counter++;
    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA Search by Cutoff");

//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                    // handle back button's click listener
//                    return true;
//                }
//                return false;
//            }
//        });

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
//                        TextView txt = (TextView) getActivity().findViewById(R.id.liveCutoff);
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
