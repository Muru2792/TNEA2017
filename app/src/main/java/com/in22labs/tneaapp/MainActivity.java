package com.in22labs.tneaapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.in22labs.tneaapp.Course.CourseCategoryList;
import com.in22labs.tneaapp.HomeFragments.AboutusFragment;
import com.in22labs.tneaapp.HomeFragments.CollegeSearch;
import com.in22labs.tneaapp.HomeFragments.CutoffFragment;
import com.in22labs.tneaapp.HomeFragments.DistrictFragment;
import com.in22labs.tneaapp.HomeFragments.LiveRegister;
import com.in22labs.tneaapp.HomeFragments.OtherFragment;
import com.in22labs.tneaapp.Notification.NotificationCenter;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.TopRank.CateRank;
import com.in22labs.tneaapp.TopRank.CateRatingCollege;
import com.in22labs.tneaapp.TopRank.TopRankZone;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.pushbots.push.Pushbots;

import java.io.IOException;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    int count;
    public static String Temail;
    DatabaseHelper db;
    static String email;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();

        Pushbots.sharedInstance().init(this);
        getEmail();

        try {
            db = new DatabaseHelper(MainActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_home) {
                    FragmentTransaction fT = mFragmentManager.beginTransaction();
                    fT.replace(R.id.containerView, new HomeFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_college) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new CollegeSearch()).addToBackStack(null).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_cutoff) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new CutoffFragment()).addToBackStack(null).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_course) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new CourseCategoryList()).addToBackStack(null).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_about) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new AboutusFragment()).addToBackStack(null).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_other) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new OtherFragment()).addToBackStack(null).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_district) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new DistrictFragment()).addToBackStack(null).commit();
                }
                if (menuItem.getItemId() == R.id.nav_item_topcollege) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new TopRankZone()).addToBackStack(null).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_ranking) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new CateRatingCollege()).addToBackStack(null).commit();
                }
                if (menuItem.getItemId() == R.id.nav_item_rating) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new CateRank()).addToBackStack(null).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_live) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new LiveRegister()).addToBackStack(null).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_notification) {
                    Intent i = new Intent(MainActivity.this, NotificationCenter.class);
                    i.putExtra("a4ncontent", "not");
                    i.putExtra("message", "not");
                    startActivity(i);
                }

                if (menuItem.getItemId() == R.id.nav_item_share) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Really Amazing Application");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.in22labs.counsel&hl=en");
                    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                }
                if (menuItem.getItemId() == R.id.nav_item_terms) {
                    String str = "http://tnea.a4n.in/Legal/terms_and_conditions";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
                }
                if (menuItem.getItemId() == R.id.nav_item_rate) {
                    String str = "https://play.google.com/store/apps/details?id=com.in22labs.counsel&hl=en";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
                }

                if (menuItem.getItemId() == R.id.nav_item_policy) {
                    String str = "http://tnea.a4n.in/Legal/privacy_policy";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);

                return false;


            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

    }

    private void getEmail() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                email = account.name;

            }

        }

        Temail = email;
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        count = mFragmentManager.getBackStackEntryCount();
//        Toast.makeText(MainActivity.this, "Count   "+count, Toast.LENGTH_SHORT).show();
        if (count == 0) {
//            closeMenu();
            close1();
//                    Toast.makeText(MainActivity.this, "Back pressed", Toast.LENGTH_SHORT).show();

        } else {
//            getFragmentManager().popBackStack();
            super.onBackPressed();
//            Toast.makeText(MainActivity.this, "Back pressed 22333", Toast.LENGTH_SHORT).show();

        }
//
    }


    public void close1(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(getResources().getString(R.string.title));
        builder1.setMessage("Are you really want to exit?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                });
        builder1.setNeutralButton("Rate Us",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" +getPackageName())));
                }

            }
        });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}