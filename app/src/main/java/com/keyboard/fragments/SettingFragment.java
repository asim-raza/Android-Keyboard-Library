package com.keyboard.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.keyboard.InterstitialAds;
import com.keyboard.MainApplication;
import com.keyboard.R;
import com.keyboard.helpers.MyPrafrances;
import com.keyboard.helpers.Utilities;
import com.keyboard.helpers.WebViewClass;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

@SuppressLint("ValidFragment")
public class SettingFragment extends Fragment implements InterstitialAds.OnAdListener{

    String TAG = "SettingFragment";

    static Context mContext;
    MyPrafrances prefs;

    LinearLayout layout_keyboard_themes, layout_keyboard_photo, layout_key_sound, layout_privacy, layout_help;
    Switch switch_show_suggestions, switch_key_sound, switch_key_vibration;

    private InterstitialAds interstitialAds;

    private int action;




    public static SettingFragment newInstance(Context context) {

        mContext = context;

        Bundle args = new Bundle();
        
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        this.init(view);

        interstitialAds = MainApplication.getAd(mContext);
        return view;
    }

    private void init(View view){
        layout_keyboard_themes = view.findViewById(R.id.layout_keyboard_themes);
        layout_keyboard_photo = view.findViewById(R.id.layout_keyboard_photo);
        layout_key_sound = view.findViewById(R.id.layout_key_sound);
        layout_privacy = view.findViewById(R.id.layout_privacy);
        layout_help = view.findViewById(R.id.layout_help);

        switch_show_suggestions = view.findViewById(R.id.switch_show_suggestions);
        switch_key_sound = view.findViewById(R.id.switch_key_sound);
        switch_key_vibration = view.findViewById(R.id.switch_key_vibration);

        prefs = MainApplication.getPrefrences();
        switch_show_suggestions.setChecked(prefs.getShowSugession());
        switch_key_sound.setChecked(prefs.getSound());
        switch_key_vibration.setChecked(prefs.getVibration());

//        interstitialAds = InterstitialAds.getInstance(mContext);

        layout_keyboard_themes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAds.showAd();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ThemeFragment themeFragment = ThemeFragment.newInstance(mContext);
                fragmentTransaction.replace(R.id.fragemntContainer, themeFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                action = -1;
            }
        });

        layout_keyboard_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean yes= Utilities.isStoragePermissionGranted(getActivity());
                if(yes)
                {
                    Intent intent = new Intent("android.intent.action.PICK");
                    intent.setType("image/*");
                    SettingFragment.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                }
                else{

//                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
//                    builder.setMessage(getActivity().getString(R.string.storage_permission_dialog))
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    ActivityCompat.requestPermissions(((Activity) mContext,
//                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                            1);
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();

                }
            }
        });

        layout_key_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAds.showAd();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SoundFragment themeFragment = SoundFragment.newInstance(mContext);
                fragmentTransaction.replace(R.id.fragemntContainer, themeFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                action = -1;
            }
        });

        switch_show_suggestions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                interstitialAds.showAd();
                prefs.setShowSugession(isChecked);
                action = -1;
            }
        });
        switch_key_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                interstitialAds.showAd();
                prefs.setSound(isChecked);
                action = -1;
            }
        });
        switch_key_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                interstitialAds.showAd();
                prefs.setVibration(isChecked);
                action = -1;
            }
        });

        layout_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "Action 6");
                    //Show URL now
                interstitialAds.showAd();
                Intent intent1=new Intent(getActivity(), WebViewClass.class);
                startActivity(intent1);
            }
        });

        layout_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, String.valueOf(action));
                interstitialAds.showAd();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HelpFragment helpFragment = HelpFragment.newInstance(mContext);
                fragmentTransaction.replace(R.id.fragemntContainer, helpFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });




    }


    @Override
    public void onAdClose() {

        Log.e(TAG, String.valueOf(action));

        switch (action){
            case 6:
                Intent intent1=new Intent(getActivity(), WebViewClass.class);
                startActivity(intent1);
                break;
            case 7:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HelpFragment helpFragment = HelpFragment.newInstance(mContext);
                fragmentTransaction.replace(R.id.fragemntContainer, helpFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
        }
    }

    @SuppressLint("WrongConstant")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e(TAG,  "requestCode: " + String.valueOf(requestCode));
//        Log.e(TAG, "resultCode: "+ String.valueOf(resultCode));
//        Log.e(TAG, "getData: " + String.valueOf(data.getData()));

//        if (resultCode == -1 && requestCode == 1) {
//            Log.e(TAG, "onActivityResult Setting Theme");
//            Uri _uri = data.getData();
//            prefs.setCustomTheme(true);
//            prefs.setTheme(String.valueOf(_uri));
//            Log.e(TAG,_uri.toString());
//            Toast.makeText(mContext, "Theme has been updated successfully", Toast.LENGTH_SHORT).show();
//        }
        if (requestCode == 1 && resultCode == -1) {

//            CropImage
//                    .activity(data.getData())
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(16, 9)
//                    .setAutoZoomEnabled(true)
//                    .setFixAspectRatio(true)
//                    .start(getContext(), this);
//
//        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == -1) {
//            Uri _uri = CropImage.getActivityResult(data).getUri();
//            prefs.setCustomTheme(true);
//            prefs.setTheme(_uri.toString());
//            Toast.makeText(getActivity(), "Theme has been updated successfully", Toast.LENGTH_SHORT).show();
        }
//        interstitialAds.showAd();
    }
}