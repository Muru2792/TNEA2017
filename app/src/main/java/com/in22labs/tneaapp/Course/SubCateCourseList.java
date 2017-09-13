package com.in22labs.tneaapp.Course;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;


public class SubCateCourseList extends Fragment {
    ListView listView ;
    String Category;
    ArrayList<String> course;
    DatabaseHelper db;
    TextView txtContent;

    ConnectionDetector cd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subcourselist,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Category=getArguments().getString("category").toString();
       // Toast.makeText(getActivity(), Category, Toast.LENGTH_SHORT).show();
        listView = (ListView)getActivity().findViewById(R.id.coursesublist);
        txtContent=(TextView)getActivity().findViewById(R.id.txtContentCourse);
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText(Category);
        ab.setTextSize(20.0f);

        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.Cate_course=Category;
        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewSubCourseList);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);

            }
        }
//        txtContent.setText(Category);
        if(Category=="MECHANICAL"){
            txtContent.setText("  For students who are passionate about machines and automotives to excel in these fields, you should learn Design applications/packages");
        }else if(Category=="AGRI/FOOD"){
            txtContent.setText("  Engineering the way food is produced and processed holds a great future for a practical/purposeful engineering");
        }else if(Category=="SPECIALIZED"){
            txtContent.setText(" IF you are looking for a niche career different from others and create a contrarian path for your career");
        }else if(Category=="TEXTILE"){
            txtContent.setText("  Know about the machineries and technology used in Textile Industry fabric experiments and exposure are greater advantages of this program");
        }else if(Category=="CIVIL"){
            txtContent.setText("  A civil engineer is required for every sq ft of building built in any part of the world go beyond the colleges and get practical field exposures");
        }else if(Category=="COMPUTER"){
            txtContent.setText("  You are a step short of designing your own future get t learn beyond Java/C++.. machine Language and Data analytics are the future");
        }else if(Category=="BIO-TECH"){
            txtContent.setText("  A combination of Life science and technology and Being Nano is the future of Bio-tech a highly research oriented course.. So choose the college wisely");
        }else if(Category=="CHEMICAL"){
            txtContent.setText("  A complex program, but the only potential program which has the need & space in every other field higher scope for Research and development");
        }else if(Category=="ELECTRONICS"){
            txtContent.setText("  A course which holds the future of IOT but what you get from 95% of colleges is only about 10% of futuristic need get yourselves exposed beyond the curriculum");
        }



        course=db.getCourseNewSub();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.listview_course, R.id.crse_list, course);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
               // int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                Fragment mFragment = new SubCateCollegeList();
                final Bundle b1 = new Bundle();
                b1.putString("course", itemValue);

                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                // Show Alert
                //Toast.makeText(getActivity(), itemValue, Toast.LENGTH_LONG).show();

            }

        });
    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA Category Course List");
    }
}
