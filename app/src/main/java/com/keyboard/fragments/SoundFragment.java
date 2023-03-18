package com.keyboard.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.keyboard.InterstitialAds;
import com.keyboard.MainApplication;
import com.keyboard.R;
import com.keyboard.helpers.MyPrafrances;

public class SoundFragment extends Fragment {

    private String TAG = "InfoFragment";

    private static Context mContext;

    MyPrafrances mPrefs;
    TextView mTextAndroid, mTextHard, mTextIphone, mTextSamsung, mTextTypeWritter, mTextWater, mTextWood;

    private InterstitialAds interstitialAds;


    public static SoundFragment newInstance(Context context) {
        mContext = context;
        Bundle args = new Bundle();
        SoundFragment fragment = new SoundFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sound, container, false);
        this.init(view);
        return view;
    }

    private void init(View view){

//        MainApplication app = (MainApplication) mContext;

        mPrefs = new MyPrafrances(mContext);

        mTextSamsung = (TextView) view.findViewById(R.id.text_samsung);
        mTextAndroid = (TextView) view.findViewById(R.id.text_android);
        mTextIphone = (TextView) view.findViewById(R.id.text_iphone);
        mTextWood = (TextView) view.findViewById(R.id.text_wood);
        mTextWater = (TextView) view.findViewById(R.id.text_water);
        mTextHard = (TextView) view.findViewById(R.id.text_hard);
        mTextTypeWritter = (TextView) view.findViewById(R.id.text_typewriter);

        interstitialAds = MainApplication.getAd(mContext);


        mTextSamsung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                interstitialAds.showAd();
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (mTextSamsung.getRight() - mTextSamsung.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MediaPlayer mp;
                        mp=MediaPlayer.create(getActivity(), R.raw.samsung);
                        mp.start();

                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.stop();
                                if (mediaPlayer != null) {
                                    mediaPlayer.release();
                                }
                            }
                        });
                        return true;
                    }else{
                        mPrefs.setSoundName("samsung.mp3");
                        Toast.makeText(mContext,"Key sound has been updated",Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        mTextAndroid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                interstitialAds.showAd();
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (mTextAndroid.getRight() - mTextAndroid.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MediaPlayer mp;
                        mp=MediaPlayer.create(getActivity(), R.raw.android);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.stop();
                                if (mediaPlayer != null) {
                                    mediaPlayer.release();
                                }
                            }
                        });
                        return true;
                    }else{
                        mPrefs.setSoundName("android.mp3");
                        Toast.makeText(mContext,"Key sound has been updated",Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        mTextIphone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                interstitialAds.showAd();
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (mTextIphone.getRight() - mTextIphone.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MediaPlayer mp;
                        mp=MediaPlayer.create(getActivity(), R.raw.iphone);
                        mp.start();
                        return true;
                    }else{
                        mPrefs.setSoundName("iphone.mp3");
                        Toast.makeText(mContext,"Key sound has been updated",Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        mTextWood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                interstitialAds.showAd();
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (mTextWood.getRight() - mTextWood.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MediaPlayer mp;
                        mp=MediaPlayer.create(getActivity(), R.raw.wood);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.stop();
                                if (mediaPlayer != null) {
                                    mediaPlayer.release();
                                }
                            }
                        });
                        return true;
                    }else{
                        mPrefs.setSoundName("wood.mp3");
                        Toast.makeText(mContext,"Key sound has been updated",Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        mTextWater.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                interstitialAds.showAd();
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (mTextWater.getRight() - mTextWater.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MediaPlayer mp;
                        mp=MediaPlayer.create(getActivity(), R.raw.water);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.stop();
                                if (mediaPlayer != null) {
                                    mediaPlayer.release();
                                }
                            }
                        });
                        return true;
                    }else{
                        mPrefs.setSoundName("water.mp3");
                        Toast.makeText(mContext,"Key sound has been updated",Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        mTextHard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                interstitialAds.showAd();
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (mTextHard.getRight() - mTextHard.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MediaPlayer mp;
                        mp=MediaPlayer.create(getActivity(), R.raw.hard);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.stop();
                                if (mediaPlayer != null) {
                                    mediaPlayer.release();
                                }
                            }
                        });
                        return true;
                    }else{
                        mPrefs.setSoundName("hard.mp3");
                        Toast.makeText(mContext,"Key sound has been updated",Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        mTextTypeWritter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                interstitialAds.showAd();
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (mTextTypeWritter.getRight() - mTextTypeWritter.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        MediaPlayer mp;
                        mp=MediaPlayer.create(getActivity(), R.raw.typewriter);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.stop();
                                if (mediaPlayer != null) {
                                    mediaPlayer.release();
                                }
                            }
                        });
                        return true;
                    }else{
                        mPrefs.setSoundName("typewriter.mp3");
                        Toast.makeText(mContext,"Key sound has been updated",Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

    }
}
