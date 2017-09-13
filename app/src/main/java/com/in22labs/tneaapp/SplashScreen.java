package com.in22labs.tneaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.in22labs.tneaapp.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScreen extends Activity {

    protected int _splashTime = 4000;
    private static final String STORAGE_IN_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String STORAGE_OUT_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String NETWORK_PERMISSION = "android.permission.ACCESS_NETWORK_STATE";
    private static final String WAKE_LOCK = "android.permission.WAKE_LOCK";
    private static final String RDCONTACT_PERMISSION = "android.permission.READ_CONTACTS";
    private static final String GETCONTACT_PERMISSION = "android.permission.GET_ACCOUNTS";
    private static final String RDPHONESTATE_PERMISSION = "android.permission.READ_PHONE_STATE";

    private static final String INTERNET = "android.permission.INTERNET";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {	// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
//
        ImageView img=(ImageView)findViewById(R.id.imgsplash);
        TextView tv=(TextView)findViewById(R.id.tvsplash);

        int permission1 = checkCallingOrSelfPermission(STORAGE_OUT_PERMISSION);
        List<String> permissions = new ArrayList<>();

        if (permission1 == PackageManager.PERMISSION_DENIED) {
            permissions.add(STORAGE_OUT_PERMISSION);
            permissions.add(STORAGE_IN_PERMISSION);
            permissions.add(NETWORK_PERMISSION);
            permissions.add(WAKE_LOCK);
            permissions.add(RDCONTACT_PERMISSION);
            permissions.add(GETCONTACT_PERMISSION);
            permissions.add(RDPHONESTATE_PERMISSION);
            permissions.add(INTERNET);
        }

        if (permissions.isEmpty()) {


        } else {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), 11);

        }

        final Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        final Animation rtol = AnimationUtils.loadAnimation(this, R.anim.lefttoright);

        img.startAnimation(alpha);
        tv.startAnimation(alpha);



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(i);

                SplashScreen.this.finish();
                // overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        }, _splashTime);

    }
}
