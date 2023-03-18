package com.keyboard;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.keyboard.helpers.MyPrafrances;

public class MainApplication extends Application {

    private static MyPrafrances myPrafrances;

    private static InterstitialAds mInterstitialAds;


    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        myPrafrances = new MyPrafrances(getApplicationContext());
        // TODO: Move this to where you establish a user session
        logUser();

    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
//        Crashlytics.setUserIdentifier("12345");
//        Crashlytics.setUserEmail("user@fabric.io");
//        Crashlytics.setUserName("Test User");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }




    public static MyPrafrances getPrefrences(){
        return myPrafrances;

    }

    public static InterstitialAds getAd(Context mContext){
        if(mInterstitialAds == null)
            mInterstitialAds = new InterstitialAds(mContext);
        return mInterstitialAds;
    }


}


