package com.contactbackup.cloud.keyboard.keyboard;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.contactbackup.cloud.keyboard.R;
import com.contactbackup.cloud.keyboard.keyboard.Keyboard_inputMethod.InputMethodSettingsFragment;


public class ImePreferences extends PreferenceActivity {

    class C03961 implements OnClickListener {
        C03961() {
        }

        public void onClick(View v) {
            ImePreferences.this.finish();
        }
    }

    public static class Settings extends InputMethodSettingsFragment {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setInputMethodSettingsCategoryTitle((int) R.string.language_selection_title);
            setSubtypeEnablerTitle((int) R.string.select_language);
            addPreferencesFromResource(R.xml.ime_preferences);
        }
    }

    public Intent getIntent() {
        Intent modIntent = new Intent(super.getIntent());
        modIntent.putExtra(":android:show_fragment", Settings.class.getName());
        modIntent.putExtra(":android:no_headers", true);
        return modIntent;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.settings_name);
    }

    protected boolean isValidFragment(String fragmentName) {
        return Settings.class.getName().equals(fragmentName);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        LinearLayout root = (LinearLayout) findViewById(16908298).getParent().getParent().getParent();
//        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, root, false);
//        root.addView(bar, 0);
//        bar.setNavigationOnClickListener(new C03961());
    }
}
