package com.keyboard.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.keyboard.InterstitialAds;
import com.keyboard.MainActivity;
import com.keyboard.MainApplication;
import com.keyboard.R;

@SuppressLint("ValidFragment")
public class HelpFragment extends Fragment implements MainActivity.IOnBackPressed {

    static Context mContext;



    public static HelpFragment newInstance(Context context) {

        Bundle args = new Bundle();

        mContext = context;

        HelpFragment fragment = new HelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        this.init(view);
        return view;
    }

    private void init(View view){



    }

    @Override
    public boolean onBackPressed() {
        InterstitialAds intersititial = MainApplication.getAd(mContext);
        intersititial.showAd();
        //action not popBackStack
        return true;
    }


}