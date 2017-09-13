package com.in22labs.tneaapp.Notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class NotificationManage extends AppCompatActivity {
    String title;
    TextView nTitle, nDate, nTime, nMessage;
    DatabaseHelper db;
    ArrayList<String> notifyMessageList;
    ConnectionDetector cd;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationcontent);
        Typeface Ty1= Typeface.createFromAsset(getAssets(), "RobotoSlab-Bold.ttf");
        title=getIntent().getStringExtra("messageTitile");


        Toolbar tool=(Toolbar)findViewById(R.id.toolbar_contentnotification);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_contentnotificationmessage);
        ab.setText("Notification Center");
        ab.setTextSize(20.0f);
        tool.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationManage.this, NotificationCenter.class);
                intent.putExtra("testNotify","not");
                intent.putExtra("message", "not");
                startActivity(intent);
                finish();
            }
        });

        nTitle=(TextView)findViewById(R.id.activityTitle);
        nMessage=(TextView)findViewById(R.id.activityContent);
        nDate=(TextView)findViewById(R.id.activityDate);
        nTime=(TextView)findViewById(R.id.activityTime);
        cd=new ConnectionDetector(NotificationManage.this);
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else {
                AdView mAdView = (AdView) findViewById(R.id.adViewNotiManage);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);
            }
        }
        try {
            db = new DatabaseHelper(NotificationManage.this);

        } catch (IOException e) {

            e.printStackTrace();
        }
        notifyMessageList=db.getNotificationMessage(title);
        if(!notifyMessageList.isEmpty()){
            nTitle.setText(notifyMessageList.get(0));
            nTitle.setTypeface(Ty1);
            nMessage.setText(notifyMessageList.get(1));
            nDate.setText(notifyMessageList.get(2));
            nTime.setText(notifyMessageList.get(3));
//            Toast.makeText(NotificationManage.this,notifyMessageList.get(1),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotificationManage.this, NotificationCenter.class);
        intent.putExtra("testNotify","not");
        intent.putExtra("message", "not");
        startActivity(intent);
        finish();
    }
}
