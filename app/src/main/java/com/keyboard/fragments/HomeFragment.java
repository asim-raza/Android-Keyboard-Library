package com.keyboard.fragments;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.keyboard.R;
import com.contactbackup.cloud.keyboard.keyboard.receiver.DialogReceiver;

import static android.content.Context.INPUT_METHOD_SERVICE;

import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private String TAG = "HomeFragment";
    InputMethodManager mInputMethodManager;

    RelativeLayout switch_layout, enable_layout, layout_enable;
    LinearLayout layout_switch;


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG,"HomeFragment");
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        switch_layout = view.findViewById(R.id.switch_layout);
        enable_layout = view.findViewById(R.id.enable_layout);

        layout_enable = view.findViewById(R.id.layout_enable);
        layout_switch = view.findViewById(R.id.layout_switch);

        if(isKeyboardEnable()){
            enable_layout.setVisibility(View.GONE);
            switch_layout.setVisibility(View.VISIBLE);
        }else{
            switch_layout.setVisibility(View.GONE);
            enable_layout.setVisibility(View.VISIBLE);
        }

        YoYo.with(Techniques.Bounce)
                .duration(700)
                .repeat(1000)
                .playOn(view.findViewById(R.id.layout_enable));

        YoYo.with(Techniques.Bounce)
                .duration(700)
                .repeat(1000)
                .playOn(view.findViewById(R.id.layout_switch));

        layout_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Enable");
                HomeFragment.this.startActivityForResult(new Intent("android.settings.INPUT_METHOD_SETTINGS"), 0);
                Intent intent = new Intent(getActivity(), DialogReceiver.class);
                intent.setAction("com.receiver.dialog");
                getActivity().sendBroadcast(intent);
            }
        });

        layout_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputMethodPicker();
            }
        });



        return view;
    }


    @Override
    public void onResume(){
        super.onResume();

        Log.e(TAG, "Resume");

//        super.onResume();
        if(isKeyboardEnable()){
            enable_layout.setVisibility(View.GONE);
            switch_layout.setVisibility(View.VISIBLE);
        }else{
            switch_layout.setVisibility(View.GONE);
            enable_layout.setVisibility(View.VISIBLE);
        }
    }

    public boolean isKeyboardEnable() {
        String mPackageLocal =  getActivity().getPackageName();
        InputMethodManager mInputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        for (InputMethodInfo inputMethod : mInputMethodManager.getEnabledInputMethodList()) {
            if (inputMethod.getPackageName().equals(mPackageLocal)) {
                return true;
            }
        }
        return false;
    }


    @SuppressLint("WrongConstant")
    private void showInputMethodPicker() {
        this.mInputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (this.mInputMethodManager != null) {
            this.mInputMethodManager.showInputMethodPicker();
        }
    }

    public static boolean isThisKeyboardSetAsDefaultIME(Context context) {
        return isThisKeyboardSetAsDefaultIME(Settings.Secure.getString(context.getContentResolver(), "default_input_method"), context.getPackageName());
    }

    public static boolean isThisKeyboardSetAsDefaultIME(String defaultIME, String myPackageName) {
        if (!TextUtils.isEmpty(defaultIME) && ComponentName.unflattenFromString(defaultIME).getPackageName().equals(myPackageName)) {
            return true;
        }
        return false;
    }

}
