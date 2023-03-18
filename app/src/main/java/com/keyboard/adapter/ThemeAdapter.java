package com.keyboard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.keyboard.InterstitialAds;
import com.keyboard.MainApplication;
import com.keyboard.R;
import com.keyboard.helpers.MyPrafrances;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.MyViewHolder> {

    private String TAG = "ThemeAdapter";
    private Context mContext;
    int[] mDataset;
    public MyPrafrances prefs;
//    private InterstitialAdSingleton interstitialAdSingleton;
    private InterstitialAds interstitialAds;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView themeName;
        public TextView themeSelect;

        public MyViewHolder(View v) {
            super(v);
            this.mImageView = (ImageView) v.findViewById(R.id.themeImage);
        }
    }

    public ThemeAdapter(Context ctx, int[] mData) {
        this.mContext = ctx;
        prefs = MainApplication.getPrefrences();
        this.mDataset = mData;
        interstitialAds = MainApplication.getAd(ctx);

    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.keyboard_theme_perview, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.e(TAG, "Theme Updated");

        Glide.with(this.mContext).load(Integer.valueOf(this.mDataset[position])).into(holder.mImageView);

        holder.mImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                interstitialAds.showAd();

                switch (position) {
                    case 0:
                        ThemeAdapter.this.prefs.setTheme("ic_1");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 1:
                        ThemeAdapter.this.prefs.setTheme("ic_2");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 2:
                        ThemeAdapter.this.prefs.setTheme("ic_3");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 3:
                        ThemeAdapter.this.prefs.setTheme("ic_4");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 4:
                        ThemeAdapter.this.prefs.setTheme("ic_5");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 5:
                        ThemeAdapter.this.prefs.setTheme("ic_6");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 6:
                        ThemeAdapter.this.prefs.setTheme("ic_7");
                        ThemeAdapter.this.prefs.setThemeColor("red");
                        break;
                    case 7:
                        ThemeAdapter.this.prefs.setTheme("ic_8");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 8:
                        ThemeAdapter.this.prefs.setTheme("ic_9");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 9:
                        ThemeAdapter.this.prefs.setTheme("ic_10");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 10:
                        ThemeAdapter.this.prefs.setTheme("ic_11");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 11:
                        ThemeAdapter.this.prefs.setTheme("ic_12");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 12:
                        ThemeAdapter.this.prefs.setTheme("ic_13");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 13:
                        ThemeAdapter.this.prefs.setTheme("ic_14");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 14:
                        ThemeAdapter.this.prefs.setTheme("ic_15");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 15:
                        ThemeAdapter.this.prefs.setTheme("ic_16");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 16:
                        ThemeAdapter.this.prefs.setTheme("ic_17");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 17:
                        ThemeAdapter.this.prefs.setTheme("ic_18");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 18:
                        ThemeAdapter.this.prefs.setTheme("ic_19");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 19:
                        ThemeAdapter.this.prefs.setTheme("ic_20");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 20:
                        ThemeAdapter.this.prefs.setTheme("ic_21");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 21:
                        ThemeAdapter.this.prefs.setTheme("ic_22");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 22:
                        ThemeAdapter.this.prefs.setTheme("ic_23");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 23:
                        ThemeAdapter.this.prefs.setTheme("ic_24");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 24:
                        ThemeAdapter.this.prefs.setTheme("ic_25");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 25:
                        ThemeAdapter.this.prefs.setTheme("ic_26");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 26:
                        ThemeAdapter.this.prefs.setTheme("ic_27");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 27:
                        ThemeAdapter.this.prefs.setTheme("ic_28");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 28:
                        ThemeAdapter.this.prefs.setTheme("ic_29");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 29:
                        ThemeAdapter.this.prefs.setTheme("ic_30");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 30:
                        ThemeAdapter.this.prefs.setTheme("ic_31");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 31:
                        ThemeAdapter.this.prefs.setTheme("ic_32");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                    case 32:
                        ThemeAdapter.this.prefs.setTheme("ic_33");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;

                    default:
                        ThemeAdapter.this.prefs.setTheme("ic_1");
                        ThemeAdapter.this.prefs.setThemeColor("white");
                        break;
                }

                ThemeAdapter.this.prefs.setCustomTheme(false);
                Toast.makeText(ThemeAdapter.this.mContext, "Theme has been updated successfully", Toast.LENGTH_SHORT).show();
//                interstitialAdSingleton.showInterstitial();
            }
        });
    }

    public int getItemCount() {
        return this.mDataset.length;
    }
}
