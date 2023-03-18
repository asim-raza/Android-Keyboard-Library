package com.keyboard.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.keyboard.R;

public class InfoFragment extends Fragment {

    private String TAG = "InfoFragment";

    private static Context mContext;



    public static InfoFragment newInstance(Context context) {
        mContext = context;
        Bundle args = new Bundle();
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        this.init(view);
        return view;
    }

    private void init(View view){

    }



}
