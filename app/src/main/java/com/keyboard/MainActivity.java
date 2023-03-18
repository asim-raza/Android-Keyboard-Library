package com.keyboard;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.keyboard.fragments.MainFragment;
import com.keyboard.fragments.SettingFragment;
import com.keyboard.fragments.ThemeFragment;
import com.keyboard.R;
import com.keyboard.helpers.Utilities;

public class MainActivity extends AppCompatActivity implements InterstitialAds.OnAdListener {

    private String TAG = "MainActivity";
    private Context mContext;

    private FrameLayout fragemntContainer;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainFragment homeFragment;
    private SettingFragment settingFragment;
    static String extra = "";

    Integer shareApp = 0;

    private InterstitialAds interstitialAds;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    shareApp = 0;
                    interstitialAds.showAd();
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    homeFragment = new MainFragment();
                    fragmentTransaction.replace(R.id.fragemntContainer, homeFragment);
                    fragmentTransaction.commit();
                    fragmentTransaction.addToBackStack(null);
                    extra = "";
                    return true;
                case R.id.navigation_setting:
                    shareApp = 0;
                    interstitialAds.showAd();
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    settingFragment = SettingFragment.newInstance(mContext);
                    fragmentTransaction.replace(R.id.fragemntContainer, settingFragment);
                    fragmentTransaction.commit();
                    fragmentTransaction.addToBackStack(null);
                    extra = "setting";
                    return true;
                case R.id.navigation_share:
                    shareApp = 1;
                    if(!interstitialAds.showAd()){
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Hey check out this amazing keyboard at: " + marketUri);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                    return true;
                case R.id.navigation_moreapps:
                    shareApp = 2;
                    if(!interstitialAds.showAd()){
                        try{
                            Uri marketUri = Uri.parse("market://details?id=" + getPackageName());
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                            startActivity(marketIntent);
                        }catch(ActivityNotFoundException e) {
                            Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                            startActivity(marketIntent);
                        }
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "Test");
        setContentView(R.layout.activity_main);
        mContext = this;


        fragemntContainer =  (FrameLayout) findViewById( R.id.fragemntContainer) ;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        extra = getIntent().getStringExtra("val");

        interstitialAds = MainApplication.getAd(this);

        if(extra!= null){
            Log.e(TAG, "TAG IS: " + extra.toString());
            switch (extra){
                case "theme":
                    Log.e(TAG,"Calling Theme Fragment");
                    ThemeFragment themeFragment = ThemeFragment.newInstance(this);
                    fragmentTransaction.replace(R.id.fragemntContainer, themeFragment);
                    break;
                case "setting":
                    Log.e(TAG,"Calling Setting Fragment");
                    SettingFragment settingFragment = SettingFragment.newInstance(this);
                    fragmentTransaction.replace(R.id.fragemntContainer, settingFragment);
                    break;
                default:
                    Log.e(TAG,"Calling Home Fragment");
                    homeFragment = MainFragment.newInstance();
                    fragmentTransaction.replace(R.id.fragemntContainer, homeFragment);
            }
        }else{
            Log.e(TAG, "Extra is null");
            homeFragment = MainFragment.newInstance();
            fragmentTransaction.replace(R.id.fragemntContainer, homeFragment);
        }
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG, "Resume");
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//
//
//        if(extra!= null){
//            Log.e(TAG, "EXTRA in RESUME IS: " + extra.toString());
//            switch (extra){
//                case "theme":
//                    Log.e(TAG,"Calling Theme Fragment");
//                    ThemeFragment themeFragment = ThemeFragment.newInstance(this);
//                    fragmentTransaction.replace(R.id.fragemntContainer, themeFragment);
//                    break;
//                case "setting":
//                    Log.e(TAG,"Calling Setting Fragment");
//                    SettingFragment settingFragment = SettingFragment.newInstance(this);
//                    fragmentTransaction.replace(R.id.fragemntContainer, settingFragment);
//                    break;
//                default:
//                    Log.e(TAG,"Calling Home Fragment");
//                    homeFragment = HomeFragment.newInstance();
//                    fragmentTransaction.replace(R.id.fragemntContainer, homeFragment);
//            }
//        }else{
//            Log.e(TAG, "Extra is null");
//            homeFragment = HomeFragment.newInstance();
//            fragmentTransaction.replace(R.id.fragemntContainer, homeFragment);
//        }
//        fragmentTransaction.commit();
//        fragmentTransaction.addToBackStack(null);

    }

    @Override
    public void onAdClose() {

        Log.e("SettingFragment","From MainActivity");
        Uri marketUri;

        switch (shareApp){

            case 0:
                break;
            case 1:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out this amazing keyboard at: " + marketUri);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case 2:
                try{
                    marketUri = Uri.parse("market://details?id=" + getPackageName());
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }catch(ActivityNotFoundException e) {
                    marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }
                break;
        }


        Log.e(TAG, "Ad Closed");
    }

    public static void setExtra(String str){
        extra = str;
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragemntContainer);
        if(fragment == null){
            finish();
        }
    }

    public interface IOnBackPressed {
        boolean onBackPressed();
    }



}
