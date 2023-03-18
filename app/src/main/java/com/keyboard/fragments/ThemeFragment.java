package com.keyboard.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.keyboard.MainActivity;
import com.keyboard.MainApplication;
import com.keyboard.R;
import com.keyboard.adapter.ThemeAdapter;
import com.keyboard.helpers.MyPrafrances;

@SuppressLint("ValidFragment")
public class ThemeFragment extends Fragment {

    RecyclerView.Adapter mAdpater;
    private int[] mDataset = new int[]{R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3, R.drawable.ic_4,
            R.drawable.ic_5, R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9,
            R.drawable.ic_10, R.drawable.ic_11, R.drawable.ic_12, R.drawable.ic_13, R.drawable.ic_14,
            R.drawable.ic_15, R.drawable.ic_16, R.drawable.ic_17, R.drawable.ic_18, R.drawable.ic_19,
            R.drawable.ic_20, R.drawable.ic_21, R.drawable.ic_22, R.drawable.ic_23, R.drawable.ic_24,
            R.drawable.ic_25, R.drawable.ic_26, R.drawable.ic_27, R.drawable.ic_28, R.drawable.ic_29,
            R.drawable.ic_30, R.drawable.ic_31, R.drawable.ic_32, R.drawable.ic_33
    };
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRcyclerView;

    private MyPrafrances prefs;
    private static Context mContext;



    public static ThemeFragment newInstance(Context context) {

        mContext = context;

        Bundle args = new Bundle();

        ThemeFragment fragment = new ThemeFragment();
        fragment.setArguments(args);

        MainActivity.setExtra("theme");

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        this.init(view);
        return view;
    }

    private void init(View view){

        //getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MainApplication main = (MainApplication)getActivity().getApplicationContext();

        this.prefs = main.getPrefrences();

        this.mRcyclerView = (RecyclerView) view.findViewById(R.id.themeRecycer);
        this.mLayoutManager = new LinearLayoutManager(mContext);
        this.mRcyclerView.setLayoutManager(this.mLayoutManager);
        this.mAdpater = new ThemeAdapter(mContext, this.mDataset);
        this.mRcyclerView.setAdapter(this.mAdpater);


    }


}