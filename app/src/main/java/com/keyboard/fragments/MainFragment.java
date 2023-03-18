package com.keyboard.fragments;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.keyboard.R;
import com.contactbackup.cloud.keyboard.keyboard.receiver.DialogReceiver;

import static android.content.Context.INPUT_METHOD_SERVICE;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    private String TAG = "MainFragment";
    InputMethodManager mInputMethodManager;

    RelativeLayout rel_enable, rel_activate;
    ImageView img_enable, img_active;


    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG,"HomeFragment");
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        rel_enable = view.findViewById(R.id.rel_enable);
        rel_activate = view.findViewById(R.id.rel_activate);

        img_enable = view.findViewById(R.id.img_enable);
        img_active = view.findViewById(R.id.img_active);

//
//        layout_enable = view.findViewById(R.id.layout_enable);
//        layout_switch = view.findViewById(R.id.layout_switch);
//
//        if(isKeyboardEnable()){
//            enable_layout.setVisibility(View.GONE);
//            switch_layout.setVisibility(View.VISIBLE);
//        }else{
//            switch_layout.setVisibility(View.GONE);
//            enable_layout.setVisibility(View.VISIBLE);
//        }
//
//        YoYo.with(Techniques.Bounce)
//                .duration(700)
//                .repeat(1000)
//                .playOn(view.findViewById(R.id.layout_enable));
//
//        YoYo.with(Techniques.Bounce)
//                .duration(700)
//                .repeat(1000)
//                .playOn(view.findViewById(R.id.layout_switch));
//
        rel_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Enable");

                if(isKeyboardEnable()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getActivity().getString(R.string.already_enabled))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    showInputMethodPicker();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }else{
                    showKeyboardListToEnable();
                }
            }
        });
//
        rel_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isKeyboardEnable()){
                    showInputMethodPicker();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getActivity().getString(R.string.not_activated))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    showKeyboardListToEnable();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
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
            img_enable.setImageResource(R.drawable.ic_enabled);
        }else{
            img_enable.setImageResource(R.drawable.ic_enable);
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

    private void showKeyboardListToEnable(){
        MainFragment.this.startActivityForResult(new Intent("android.settings.INPUT_METHOD_SETTINGS"), 0);
        Intent intent = new Intent(getActivity(), DialogReceiver.class);
        intent.setAction("com.receiver.dialog");
        getActivity().sendBroadcast(intent);
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
