package com.contactbackup.cloud.keyboard.keyboard.Keyboard_inputMethod;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public abstract class InputMethodSettingsFragment extends PreferenceFragment implements InputMethodSettingsInterface {
    private final InputMethodSettingsImpl mSettings = new InputMethodSettingsImpl();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getActivity();
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(context));
        this.mSettings.init(context, getPreferenceScreen());
    }

    public void setInputMethodSettingsCategoryTitle(int resId) {
        this.mSettings.setInputMethodSettingsCategoryTitle(resId);
    }

    public void setInputMethodSettingsCategoryTitle(CharSequence title) {
        this.mSettings.setInputMethodSettingsCategoryTitle(title);
    }

    public void setSubtypeEnablerTitle(int resId) {
        this.mSettings.setSubtypeEnablerTitle(resId);
    }

    public void setSubtypeEnablerTitle(CharSequence title) {
        this.mSettings.setSubtypeEnablerTitle(title);
    }

    public void setSubtypeEnablerIcon(int resId) {
        this.mSettings.setSubtypeEnablerIcon(resId);
    }

    public void setSubtypeEnablerIcon(Drawable drawable) {
        this.mSettings.setSubtypeEnablerIcon(drawable);
    }

    public void onResume() {
        super.onResume();
        this.mSettings.updateSubtypeEnabler();
    }
}
