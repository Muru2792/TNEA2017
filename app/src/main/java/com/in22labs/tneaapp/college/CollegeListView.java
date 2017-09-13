package com.in22labs.tneaapp.college;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.in22labs.tneaapp.CollegeDetails.CollegeData;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CollegeListView extends Fragment {
    String cutoff, course, community, CollegeCode, City;
    LinearLayout lt1, lt2;
    RelativeLayout main;
    DatabaseHelper db;
    String CollegeName, CourseCode;

    TextView txtCollegeCout, Coursechoose;

    EditText e1;
    ImageView im1;
    ListView expListView;
    List<String> listDataHeader = new ArrayList<>();


    ConnectionDetector cd;

    HashMap<String, List<String>> listDataChild;
    ArrayList<String> CollegeOrderList,LocationList, Local, RankList;
    String[] ArrCollegeName, ArrLocation, ArrRank;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.college_category,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expListView = (ListView)getActivity().findViewById(R.id.expand_college);

        txtCollegeCout=(TextView)getActivity().findViewById(R.id.collegCount);
        Coursechoose=(TextView)getActivity().findViewById(R.id.txtCourseName);
        im1=(ImageView)getActivity().findViewById(R.id.close1);
        lt1=(LinearLayout)getActivity().findViewById(R.id.lt_autocity);

        final AutoCompleteTextView Location=(AutoCompleteTextView)getActivity().findViewById(R.id.autoDist);
        e1=(EditText)getActivity().findViewById(R.id.autoDist);
        cd = new ConnectionDetector(getActivity());

        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else{
                AdView mAdView = (AdView)getActivity().findViewById(R.id.adViewCollegeCate);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
            }
        });
        //final Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText(R.string.toolCollegeList);
        ab.setTextSize(20.0f);


        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }



        main=(RelativeLayout)getActivity().findViewById(R.id.distcollegelay);
        lt1.setVisibility(View.VISIBLE);
        lt2=(LinearLayout)getActivity().findViewById(R.id.lt_coursecount);
        lt2.setVisibility(View.VISIBLE);
        cutoff=getArguments().getString("cutoff");
        course =getArguments().getString("course");
        community =getArguments().getString("community");
       // Toast.makeText(getActivity(), cutoff+" "+ course +" "+ community, Toast.LENGTH_SHORT).show();
        // preparing list data
       // prepareListData();
        CourseCode=db.getBrachCode(course);
        Snackbar.make(main, CourseCode, Snackbar.LENGTH_LONG).show();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        db.course = course;
        db.cutoff = cutoff;
        Location.addTextChangedListener(filterTextWatcher);
        if(community.equals("OC")){
            //listDataHeader=db.getNewClgListOC();
            CollegeOrderList=db.getCollegeOrderListOC();
            LocationList=db.getLocationOrderListOC();
            RankList=db.getRankOrderListOC();

        }else if(community.equals("BCM")){
         //listDataHeader=db.getNewClgListBCM();
            CollegeOrderList=db.getCollegeOrderListBCM();
            LocationList=db.getLocationOrderListBCM();
            RankList=db.getRankOrderListBCM();

        }else if(community.equals("BC")){
            //listDataHeader=db.getNewClgListBC();
            CollegeOrderList=db.getCollegeOrderListBC();
            LocationList=db.getLocationOrderListBC();
            RankList=db.getRankOrderListBC();

        }else if(community.equals("MBC")){
            //listDataHeader=db.getNewClgListMBC();
            CollegeOrderList=db.getCollegeOrderListMBC();
            LocationList=db.getLocationOrderListMBC();
            RankList=db.getRankOrderListMBC();

        }else if (community.equals("SC")){
            //listDataHeader=db.getNewClgListSC();
            CollegeOrderList=db.getCollegeOrderListSC();
            LocationList=db.getLocationOrderListSC();
            RankList=db.getRankOrderListSC();

        }else if(community.equals("SCA")){
           // listDataHeader=db.getNewClgListSCA();
            CollegeOrderList=db.getCollegeOrderListSCA();
            LocationList=db.getLocationOrderListSCA();
            RankList=db.getRankOrderListSCA();

        }else if(community.equals("ST")){
           // listDataHeader=db.getNewClgListST();
            CollegeOrderList=db.getCollegeOrderListST();
            LocationList=db.getLocationOrderListST();
            RankList=db.getRankOrderListST();

        }




        Local=db.getCityNew();

        if(CollegeOrderList.isEmpty()){
            Snackbar.make(main, "No Search List Available", Snackbar.LENGTH_LONG).show();
        }



        ArrCollegeName=new String[CollegeOrderList.size()];
        ArrCollegeName=CollegeOrderList.toArray(ArrCollegeName);

        ArrLocation=new String[LocationList.size()];
        ArrLocation=LocationList.toArray(ArrLocation);

        ArrRank=new String[RankList.size()];
        ArrRank=RankList.toArray(ArrRank);
        expListView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));

        txtCollegeCout.setText("  College: " + CollegeOrderList.size());
        Coursechoose.setText("CourseCode: "+CourseCode+"  ");

        ArrayAdapter<String> adpDistrict = new ArrayAdapter<>
                (getActivity(), R.layout.autolist_college, R.id.dist_list, Local);
        Location.setThreshold(1);
        Location.setTextSize(15.5f);
        Location.setAdapter(adpDistrict);
        Location.setVisibility(View.VISIBLE);



        Location.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                City = Location.getText().toString();
               // Toast.makeText(getActivity(), City, Toast.LENGTH_SHORT).show();
                //lt1.setVisibility(View.INVISIBLE);

                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(Location.getWindowToken(), 0);

                db.tempLoc=City;
//
                //expListView.setVisibility(View.INVISIBLE);
                if(community.equals("OC")){
                    //listDataHeader=db.getNewClgListOC();
                    CollegeOrderList=db.getSpecCollegeOrderListOC();
                    LocationList=db.getSpecLocationOrderListOC();
                    RankList=db.getSpecRankOrderListOC();

                }else if(community.equals("BCM")){
                    //listDataHeader=db.getNewClgListBCM();
                    CollegeOrderList=db.getSpecCollegeOrderListBCM();
                    LocationList=db.getSpecLocationOrderListBCM();
                    RankList=db.getSpecRankOrderListBCM();
                }else if(community.equals("BC")){
                    //listDataHeader=db.getNewClgListBC();
                    CollegeOrderList=db.getSpecCollegeOrderListBC();
                    LocationList=db.getSpecLocationOrderListBC();
                    RankList=db.getSpecRankOrderListBC();
                }else if(community.equals("MBC")){
                    //listDataHeader=db.getNewClgListMBC();
                    CollegeOrderList=db.getSpecCollegeOrderListMBC();
                    LocationList=db.getSpecLocationOrderListMBC();
                    RankList=db.getSpecRankOrderListMBC();
                }else if (community.equals("SC")){
                    //listDataHeader=db.getNewClgListSC();
                    CollegeOrderList=db.getSpecCollegeOrderListSC();
                    LocationList=db.getSpecLocationOrderListSC();
                    RankList=db.getSpecRankOrderListSC();
                }else if(community.equals("SCA")){
                    // listDataHeader=db.getNewClgListSCA();
                    CollegeOrderList=db.getSpecCollegeOrderListSCA();
                    LocationList=db.getSpecLocationOrderListSCA();
                    RankList=db.getSpecRankOrderListSCA();
                }else if(community.equals("ST")){
                    // listDataHeader=db.getNewClgListST();
                    CollegeOrderList=db.getSpecCollegeOrderListST();
                    LocationList=db.getSpecLocationOrderListST();
                    RankList=db.getSpecRankOrderListST();
                }

                ArrCollegeName=new String[CollegeOrderList.size()];
                ArrCollegeName=CollegeOrderList.toArray(ArrCollegeName);

                ArrLocation=new String[LocationList.size()];
                ArrLocation=LocationList.toArray(ArrLocation);

                ArrRank=new String[RankList.size()];
                ArrRank=RankList.toArray(ArrRank);
                txtCollegeCout.setText("College:"+CollegeOrderList.size());
                //explist.setVisibility(View.VISIBLE);
                expListView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation,ArrRank));

            }
        });




//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.listview_college, R.id.textlist, CollegeOrderList);
//        expListView.setAdapter(adapter);

        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int Position = position;
                CollegeName = CollegeOrderList.get(Position);
                //getCourseChildList(position);
                //Toast.makeText(getActivity(),CollegeName,Toast.LENGTH_SHORT).show();
                Location.setText("");
                CollegeCode=db.getCollegeCodeV1(CollegeName);
                Fragment mFragment = new CollegeData();
                final Bundle b1 = new Bundle();
                b1.putString("clgcode", CollegeCode);
                String Index;
                Index="1";
               // Toast.makeText(getActivity(),Index,Toast.LENGTH_SHORT).show();
                b1.putString("Index", Index);
                mFragment.setArguments(b1);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerView, mFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
// do your stuff
            if(community.equals("OC")){
                //listDataHeader=db.getNewClgListOC();
                CollegeOrderList=db.getCollegeOrderListOC();
                LocationList=db.getLocationOrderListOC();
                RankList=db.getRankOrderListOC();

            }else if(community.equals("BCM")){
                //listDataHeader=db.getNewClgListBCM();
                CollegeOrderList=db.getCollegeOrderListBCM();
                LocationList=db.getLocationOrderListBCM();
                RankList=db.getRankOrderListBCM();

            }else if(community.equals("BC")){
                //listDataHeader=db.getNewClgListBC();
                CollegeOrderList=db.getCollegeOrderListBC();
                LocationList=db.getLocationOrderListBC();
                RankList=db.getRankOrderListBC();

            }else if(community.equals("MBC")){
                //listDataHeader=db.getNewClgListMBC();
                CollegeOrderList=db.getCollegeOrderListMBC();
                LocationList=db.getLocationOrderListMBC();
                RankList=db.getRankOrderListMBC();

            }else if (community.equals("SC")){
                //listDataHeader=db.getNewClgListSC();
                CollegeOrderList=db.getCollegeOrderListSC();
                LocationList=db.getLocationOrderListSC();
                RankList=db.getRankOrderListSC();

            }else if(community.equals("SCA")){
                // listDataHeader=db.getNewClgListSCA();
                CollegeOrderList=db.getCollegeOrderListSCA();
                LocationList=db.getLocationOrderListSCA();
                RankList=db.getRankOrderListSCA();

            }else if(community.equals("ST")){
                // listDataHeader=db.getNewClgListST();
                CollegeOrderList=db.getCollegeOrderListST();
                LocationList=db.getLocationOrderListST();
                RankList=db.getRankOrderListST();

            }





            Local=db.getCityNew();

            if(CollegeOrderList.isEmpty()){
                Snackbar.make(main, "No Search List Available", Snackbar.LENGTH_LONG).show();
            }



            ArrCollegeName=new String[CollegeOrderList.size()];
            ArrCollegeName=CollegeOrderList.toArray(ArrCollegeName);

            ArrLocation=new String[LocationList.size()];
            ArrLocation=LocationList.toArray(ArrLocation);

            ArrRank=new String[RankList.size()];
            ArrRank=RankList.toArray(ArrRank);
            expListView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
            txtCollegeCout.setText("  College: " + CollegeOrderList.size());
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
        MyApplication.getInstance().trackScreenView("Cutoff Search Results");
    }
}
