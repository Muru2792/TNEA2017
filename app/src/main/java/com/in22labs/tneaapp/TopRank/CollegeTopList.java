package com.in22labs.tneaapp.TopRank;

import android.graphics.Typeface;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Adapter.ListCollegeAdapter;
import com.in22labs.tneaapp.CollegeDetails.CollegeData;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by In22lab on 5/15/2016.
 */
public class CollegeTopList extends Fragment {

    ListView listView ;
    String ZoneId,city;
    ArrayList<String> College;
    DatabaseHelper db;
    TextView Zone;
    EditText e1;
    ImageView im1;
    LinearLayout lt_auto;
    ArrayList<String> Location,RankList,Local;
    String temp1, temp2, temp3;
    String[] ArrCollegeName;
    String[] ArrLocation, ArrRank;
    RelativeLayout lt1;
    String[] zoneChennai = {"0001", "0002", "0004", "1321", "1315", "1219", "1211", "1113", "1317", "1304", "1419", "1120", "1210"};
    String[] zoneCoimbatore = {"2006", "2005", "2007", "2712", "2377", "2709", "2702", "2711", "2718", "2719", "2706", "2710"};
    String[] zoneVilupuram = {"1516" ,
            "3425" ,
            "1013" ,
            "1510" ,
            "1014" ,
            "1015" ,
            "3019" ,
            "3019"};
    String[] zoneSalem = {"2615" ,
            "2618" ,
            "2607" ,
            "2653" ,
            "2345" ,
            "2613" ,
            "2620" ,
            "2603" ,
            "2369"};
    String[] zoneTrichy = {"3819" ,
            "2608" ,
            "3826" ,
            "3011" ,
            "3021" ,
            "3016" ,
            "3018"};
    String[] zoneMadurai = {"5012" ,
            "5008" ,
            "5901" ,
            "5904" ,
            "5910" ,
            "5017" ,
            "5022"};
    String[] zoneTirunelveli = {"4960" ,
            "4962" ,
            "4974" ,
            "4959" ,
            "4917" ,
            "4678" ,
            "4023" ,
            "4024"};
    String[] zoneTN = {"0001", "0002","0004", "2006","2005", "2007","5012","5008","1321", "1315", "1219", "2712", "2377", "2615", "5901", "2709"};
    String CourseName,CollegeCode,CollegeName;
    ConnectionDetector cd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.topcollegelist,null);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ZoneId = getArguments().getString("zone").toString();
        //  Toast.makeText(getActivity(), CourseName, Toast.LENGTH_SHORT).show();
        listView = (ListView) getActivity().findViewById(R.id.collegetoplist);
        lt_auto=(LinearLayout)getActivity().findViewById(R.id.lt_top_autocity);
        im1=(ImageView)getActivity().findViewById(R.id.close3);
        Typeface Ty1= Typeface.createFromAsset(getActivity().getAssets(), "RobotoSlab-Regular.ttf");
        Toolbar tool=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_title);
        ab.setText("Top Colleges - Zone Wises");
        ab.setTypeface(Ty1);
        ab.setTextSize(20.0f);
        lt1 = (RelativeLayout) getActivity().findViewById(R.id.t1);
        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {

                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewTopCollegeList);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }
        try {
            db = new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Zone=(TextView)getActivity().findViewById(R.id.Top1);
        final AutoCompleteTextView City = (AutoCompleteTextView) getActivity().findViewById(R.id.autoTopCollege);
        e1=(EditText)getActivity().findViewById(R.id.autoTopCollege);
//        db.College_cate = CourseName;
        College = new ArrayList<>();
        Location = new ArrayList<>();
        RankList = new ArrayList<>();
        if(ZoneId=="ZONE1"){
            for(int i=0;i<zoneChennai.length;i++){
            db.clg_code = zoneChennai[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);
            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText("Chennai Zone \n(Chennai, Kanchipuram, Thiruvallur)");
        }else if(ZoneId=="ZONE4"){
            for(int i=0;i<zoneCoimbatore.length;i++){
                db.clg_code = zoneCoimbatore[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);
            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText("Coimbatore Zone \n(Coimbatore, Erode, Tiruppur, Ooty)");
        }else if(ZoneId=="ZONE3"){
            for(int i=0;i<zoneVilupuram.length;i++){
                db.clg_code = zoneVilupuram[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);
            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText("Villupuram Zone \n(Vilupuram, Vellore, TV Malai, Chidambaram)");
        }else if(ZoneId=="ZONE5"){
            for(int i=0;i<zoneSalem.length;i++){
                db.clg_code = zoneSalem[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);
            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText(" Salem Zone \n(Salem, Namakkal, Krishnagiri)");
        }else if(ZoneId=="ZONE6"){
            for(int i=0;i<zoneTrichy.length;i++){
                db.clg_code = zoneTrichy[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);
            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText("Trichy Zone \n(Trichy, Karur, Tanjore, Pudukottai, Nagapatnam)");
        }else if(ZoneId=="ZONE2"){
            for(int i=0;i<zoneMadurai.length;i++){
                db.clg_code = zoneMadurai[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);
            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText("Madurai Zone \n(Madurai, Sivaganga, Ramnad, Dindigul)");
        }else if(ZoneId=="ZONE7"){
            for(int i=0;i<zoneTirunelveli.length;i++){
                db.clg_code = zoneTirunelveli[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);

            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText("Tirunlveli Zone \n(Tirunelveli, Virudhunagar, Nagerkoil, kanyakumari, Thoothukudi)");
        }else if(ZoneId=="ZONE8"){


            for(int i=0;i<zoneTN.length;i++){
                db.clg_code = zoneTN[i];
                temp1=db.getCollegeTopRank();
                temp2=db.getLocationTopRank();
                temp3=db.getRankTopRank();
                College.add(i, temp1);
                Location.add(i, temp2);
                RankList.add(i, temp3);

            }
//            City.setVisibility(View.INVISIBLE);
            Zone.setText("We have assessed the engineering colleges in Tamilnadu and have categorized and ranked them");

//            College = db.getCollegeRank();
//            Location=db.getLocationRank();
//            RankList=db.getRankListRank();
//            lt_auto.setVisibility(View.VISIBLE);
//
//            Zone.setText(" 546 colleges scored based on major parameters");
//            if (College.isEmpty()) {
//                Snackbar.make(lt1, " No Results Found", Snackbar.LENGTH_SHORT).show();
//            }
//            ArrCollegeName = new String[College.size()];
//            ArrCollegeName = College.toArray(ArrCollegeName);
//
//            ArrLocation = new String[Location.size()];
//            ArrLocation = Location.toArray(ArrLocation);
//
//            ArrRank = new String[RankList.size()];
//            ArrRank = RankList.toArray(ArrRank);
//
//            listView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
//            Local = db.getLocationRankTemp();
//            ArrayAdapter<String> adpDistrict = new ArrayAdapter<String>
//                    (getActivity(), R.layout.autolist_college,R.id.dist_list, Local);
//            City.setThreshold(1);
//            City.setAdapter(adpDistrict);
//            City.addTextChangedListener(filterTextWatcher);
//            City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
//                    city = City.getText().toString();
//                    //Toast.makeText(getActivity(), city, Toast.LENGTH_SHORT).show();
//                    //lt1.setVisibility(View.INVISIBLE);
//                    db.tempLoc = city;
//                    InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    keyboard.hideSoftInputFromWindow(City.getWindowToken(), 0);
//                    College = db.getTopCollegeAutoNewSub();
//                    Location = db.getTopLocationCollegeAutoNewSub();
//                    RankList = db.getTopRankAutoNewSub();
//
//
//                    if (College.isEmpty()) {
//                        Snackbar.make(lt1, " No Results Found", Snackbar.LENGTH_SHORT).show();
//                    }
//                    ArrCollegeName = new String[College.size()];
//                    ArrCollegeName = College.toArray(ArrCollegeName);
//
//                    ArrLocation = new String[Location.size()];
//                    ArrLocation = Location.toArray(ArrLocation);
//
//                    ArrRank = new String[RankList.size()];
//                    ArrRank = RankList.toArray(ArrRank);
//
//                    listView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
//                }
//            });
//
//            im1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    e1.setText("");
//                }
//            });

        }


//        course1 = db.getCourseCollegeNewSub();
//        Location = db.getLocationCollegeNewSub();
//        RankList = db.getRankCollegeNewSub();

        if (College.isEmpty()) {
            Snackbar.make(lt1, " No Results Found", Snackbar.LENGTH_SHORT).show();
        }
        ArrCollegeName = new String[College.size()];
        ArrCollegeName = College.toArray(ArrCollegeName);

        ArrLocation = new String[Location.size()];
        ArrLocation = Location.toArray(ArrLocation);

        ArrRank = new String[RankList.size()];
        ArrRank = RankList.toArray(ArrRank);

        listView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));


       // City.setVisibility(View.VISIBLE);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                // int itemPosition = position;

                // ListView Clicked item value
                int Position = position;
                CollegeName = College.get(Position).toString();

                CollegeCode = db.getCollegeCodeV1(CollegeName).toString();                // Show Alert

                Fragment mFragment = new CollegeData();
                final Bundle b1 = new Bundle();
//                b1.putString("city", Location);
                String Index;
                Index = "1";
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
            College = db.getCollegeRank();
            Location=db.getLocationRank();
            RankList=db.getRankListRank();
            if (College.isEmpty()) {
                Snackbar.make(lt1, " No Results Found", Snackbar.LENGTH_SHORT).show();
            }
            ArrCollegeName = new String[College.size()];
            ArrCollegeName = College.toArray(ArrCollegeName);

            ArrLocation = new String[Location.size()];
            ArrLocation = Location.toArray(ArrLocation);

            ArrRank = new String[RankList.size()];
            ArrRank = RankList.toArray(ArrRank);

            listView.setAdapter(new ListCollegeAdapter(getActivity(), ArrCollegeName, ArrLocation, ArrRank));
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
        MyApplication.getInstance().trackScreenView("TNEA Zone College List");
    }
}
