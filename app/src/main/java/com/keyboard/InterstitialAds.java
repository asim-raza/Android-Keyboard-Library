package com.keyboard;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.keyboard.R;
//import com.keyboard.R;

public class InterstitialAds {

    String TAG  = "InterstitialAds";
    Context mContext;
    private InterstitialAd mInterstitialAd;

    private static InterstitialAds sSoleInstance;
    private OnAdListener mOnAdListener;

    private int adCallCount = 3;


    public InterstitialAds(Context context){

        Log.e(TAG, "Loading");

        mContext = context;

        mOnAdListener = (OnAdListener) context;

        //MobileAds.initialize(mContext,mContext.getResources().getString(R.string.ad_app_id));
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(mContext.getString(R.string.ad_interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e(TAG,"Ad Loaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e(TAG,"Ad onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                Log.e(TAG,"Ad onAdOpened");
            }

            @Override
            public void onAdClicked() {
                Log.e(TAG,"Ad onAdClicked");
            }

            @Override
            public void onAdLeftApplication() {
                Log.e(TAG,"Ad onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Log.e("SettingFragment","onAdClosed");
                mOnAdListener.onAdClose();
            }
        });

    }

    public static InterstitialAds getInstance(Context context){
        if (sSoleInstance == null){
            sSoleInstance = new InterstitialAds(context);
        }
        return sSoleInstance;
    }

    public boolean showAd(){

        if(adCallCount++ % 3 != 0){
            Log.e(TAG, "Not 3rd call");
            return false;
        }
        Log.e(TAG, "Its 3rd call");

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            return true;
        } else {
            Log.e(TAG, "The interstitial wasn't loaded yet.");
        }
        return false;
    }

    public interface OnAdListener {
        void onAdClose();
    }

}
