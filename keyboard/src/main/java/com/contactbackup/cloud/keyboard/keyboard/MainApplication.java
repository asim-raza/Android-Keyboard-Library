package com.contactbackup.cloud.keyboard.keyboard;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;


public class MainApplication extends Application {

    private static MyPrafrances myPrafrances;



    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
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

}


