package com.in22labs.tneaapp.Course;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Adapter.ListCollegeAdapter;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.CollegeDetails.CollegeData;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;


public class SubCateCollegeList extends Fragment {
    ListView listView ;
    String Category,city;
    ArrayList<String> course1;
    DatabaseHelper db;
    ArrayList<String> Location,RankList,Local;
    EditText e1;
    String[] ArrCollegeName;
    String[] ArrLocation, ArrRank;
    RelativeLayout lt1;
    LinearLayout lt3;
    TextView txtCollegeCout, Coursechoose;
    ImageView im1;
    ConnectionDetector cd;
    String CourseName,CollegeCode,CollegeName,CourseCode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subcollegelist,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CourseName=getArguments().getString("course").toString();
      //  Toast.makeText(getActivity(), CourseName, Toast.LENGTH_SHORT).show();
        listView = (ListView)getActivity().findViewById(R.id.collegesublist);
        lt1=(RelativeLayout)getActivity().findViewById(R.id.t1);
        lt3=(LinearLayout)getActivity().findViewById(R.id.lt_autocity1);
        im1=(ImageView)getActivity().findViewById(R.id.close2);

        txtCollegeCout=(TextView)getActivity().findViewById(R.id.collegCount1);
        Coursechoose=(TextView)getActivity().findViewById(R.id.txtCourseName1);
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Search College By Course");
        ab.setTextSize(20.0f);

        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewSubCollegeList);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }




        final AutoCompleteTextView City=(AutoCompleteTextView)getActivity().findViewById(R.id.autoDistsubCollege);
        e1=(EditText)getActivity().findViewById(R.id.autoDistsubCollege);

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
            }
        });

        db.College_cate=CourseName;
        course1=db.getCourseCollegeNewSub();
        Location=db.getLocationCollegeNewSub();
        RankList=db.getRankCollegeNewSub();
        CourseCode=db.getBrachCode(CourseName);
        if(course1.isEmpty()){
            Snackbar.make(lt1," No Results Found", Snackbar.LENGTH_SHORT).show();
        }
            ArrCollegeName = new String[course1.size()];
            ArrCollegeName = course1.toArray(ArrCollegeName);

            ArrLocation = new String[Location.size()];
            ArrLocation = Location.toArray(ArrLocation);

            ArrRank = new String[RankList.size()];
            ArrRank = RankList.toArray(ArrRank);

            listView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
        txtCollegeCout.setText("  College: " + course1.size());
        Coursechoose.setText("CourseCode: "+CourseCode+"  ");
        Local=db.getCityNew();
        ArrayAdapter<String> adpDistrict = new ArrayAdapter<String>
                (getActivity(), R.layout.autolist_college,R.id.dist_list, Local);
        City.setThreshold(1);
        City.setAdapter(adpDistrict);
        lt3.setVisibility(View.VISIBLE);
        City.addTextChangedListener(filterTextWatcher);
        City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                city = City.getText().toString();
                //Toast.makeText(getActivity(), city, Toast.LENGTH_SHORT).show();
                //lt1.setVisibility(View.INVISIBLE);
                db.tempLoc = city;
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(City.getWindowToken(), 0);
                course1 = db.getCourseCollegeAutoNewSub();
                Location = db.getLocationCollegeAutoNewSub();
                RankList = db.getRankCollegeAutoNewSub();


                if (course1.isEmpty()) {
                    Snackbar.make(lt1, " No Results Found", Snackbar.LENGTH_SHORT).show();
                }
                ArrCollegeName = new String[course1.size()];
                ArrCollegeName = course1.toArray(ArrCollegeName);

                ArrLocation = new String[Location.size()];
                ArrLocation = Location.toArray(ArrLocation);

                ArrRank = new String[RankList.size()];
                ArrRank = RankList.toArray(ArrRank);

                listView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
                txtCollegeCout.setText("  College: " + course1.size());
                Coursechoose.setText("CourseCode: "+CourseCode+"  ");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                // int itemPosition = position;

                // ListView Clicked item value
                int Position = position;
                CollegeName = course1.get(Position).toString();

                CollegeCode=db.getCollegeCodeV1(CollegeName).toString();                // Show Alert

                Fragment mFragment = new CollegeData();
                final Bundle b1 = new Bundle();
//                b1.putString("city", Location);
                String Index;
                Index="1";
                b1.putString("Index", Index);
                b1.putString("clgcode", CollegeCode);
//                b1.putString("community", Community);
//                b1.putString("cutoff", Cutoff);
                b1.putString("course", CourseName);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



               // Toast.makeText(getActivity(), itemValue, Toast.LENGTH_LONG).show();

            }

        });
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
//Do your stuff
            String text;
            text=e1.getText().toString();
            if(text!=null)
            {
//                showText.setText(text);
            }
            else {
//                showText.setText("You Have Not type anything");

            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            course1=db.getCourseCollegeNewSub();
            Location=db.getLocationCollegeNewSub();
            RankList=db.getRankCollegeNewSub();

            if(course1.isEmpty()){
                Snackbar.make(lt1," No Results Found", Snackbar.LENGTH_SHORT).show();
            }
            ArrCollegeName = new String[course1.size()];
            ArrCollegeName = course1.toArray(ArrCollegeName);

            ArrLocation = new String[Location.size()];
            ArrLocation = Location.toArray(ArrLocation);

            ArrRank = new String[RankList.size()];
            ArrRank = RankList.toArray(ArrRank);

            listView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
            txtCollegeCout.setText("  College: " + course1.size());
            Coursechoose.setText("CourseCode: "+CourseCode+"  ");
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
// do your stuff

        }

    };

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA Category College List");
    }
}
