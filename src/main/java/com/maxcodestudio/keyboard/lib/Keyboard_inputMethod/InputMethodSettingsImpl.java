package com.maxcodestudio.keyboard.lib.Keyboard_inputMethod;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import java.util.List;

class InputMethodSettingsImpl implements InputMethodSettingsInterface {
    private Context mContext;
    private InputMethodInfo mImi;
    private InputMethodManager mImm;
    private CharSequence mInputMethodSettingsCategoryTitle;
    private int mInputMethodSettingsCategoryTitleRes;
    private Drawable mSubtypeEnablerIcon;
    private int mSubtypeEnablerIconRes;
    private Preference mSubtypeEnablerPreference;
    private CharSequence mSubtypeEnablerTitle;
    private int mSubtypeEnablerTitleRes;

    InputMethodSettingsImpl() {
    }

    public boolean init(final Context context, PreferenceScreen prefScreen) {
        this.mContext = context;
        this.mImm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        this.mImi = getMyImi(context, this.mImm);
        if (this.mImi == null || this.mImi.getSubtypeCount() <= 1) {
            return false;
        }
        this.mSubtypeEnablerPreference = new Preference(context);
        this.mSubtypeEnablerPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                CharSequence title = InputMethodSettingsImpl.this.getSubtypeEnablerTitle(context);
                Intent intent = new Intent("android.settings.INPUT_METHOD_SUBTYPE_SETTINGS");
                intent.putExtra("input_method_id", InputMethodSettingsImpl.this.mImi.getId());
                if (!TextUtils.isEmpty(title)) {
                    intent.putExtra("android.intent.extra.TITLE", title);
                }
                intent.setFlags(337641472);
                context.startActivity(intent);
                return true;
            }
        });
        prefScreen.addPreference(this.mSubtypeEnablerPreference);
        updateSubtypeEnabler();
        return true;
    }

    private static InputMethodInfo getMyImi(Context context, InputMethodManager imm) {
        List<InputMethodInfo> imis = imm.getInputMethodList();
        for (int i = 0; i < imis.size(); i++) {
            InputMethodInfo imi = (InputMethodInfo) imis.get(i);
            if (((InputMethodInfo) imis.get(i)).getPackageName().equals(context.getPackageName())) {
                return imi;
            }
        }
        return null;
    }

    private static String getEnabledSubtypesLabel(Context context, InputMethodManager imm, InputMethodInfo imi) {
        if (context == null || imm == null || imi == null) {
            return null;
        }
        List<InputMethodSubtype> subtypes = imm.getEnabledInputMethodSubtypeList(imi, true);
        StringBuilder sb = new StringBuilder();
        int N = subtypes.size();
        for (int i = 0; i < N; i++) {
            InputMethodSubtype subtype = (InputMethodSubtype) subtypes.get(i);
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(subtype.getDisplayName(context, imi.getPackageName(), imi.getServiceInfo().applicationInfo));
        }
        return sb.toString();
    }

    public void setInputMethodSettingsCategoryTitle(int resId) {
        this.mInputMethodSettingsCategoryTitleRes = resId;
        updateSubtypeEnabler();
    }

    public void setInputMethodSettingsCategoryTitle(CharSequence title) {
        this.mInputMethodSettingsCategoryTitleRes = 0;
        this.mInputMethodSettingsCategoryTitle = title;
        updateSubtypeEnabler();
    }

    public void setSubtypeEnablerTitle(int resId) {
        this.mSubtypeEnablerTitleRes = resId;
        updateSubtypeEnabler();
    }

    public void setSubtypeEnablerTitle(CharSequence title) {
        this.mSubtypeEnablerTitleRes = 0;
        this.mSubtypeEnablerTitle = title;
        updateSubtypeEnabler();
    }

    public void setSubtypeEnablerIcon(int resId) {
        this.mSubtypeEnablerIconRes = resId;
        updateSubtypeEnabler();
    }

    public void setSubtypeEnablerIcon(Drawable drawable) {
        this.mSubtypeEnablerIconRes = 0;
        this.mSubtypeEnablerIcon = drawable;
        updateSubtypeEnabler();
    }

    private CharSequence getSubtypeEnablerTitle(Context context) {
        if (this.mSubtypeEnablerTitleRes != 0) {
            return context.getString(this.mSubtypeEnablerTitleRes);
        }
        return this.mSubtypeEnablerTitle;
    }

    public void updateSubtypeEnabler() {
        if (this.mSubtypeEnablerPreference != null) {
            if (this.mSubtypeEnablerTitleRes != 0) {
                this.mSubtypeEnablerPreference.setTitle(this.mSubtypeEnablerTitleRes);
            } else if (!TextUtils.isEmpty(this.mSubtypeEnablerTitle)) {
                this.mSubtypeEnablerPreference.setTitle(this.mSubtypeEnablerTitle);
            }
            String summary = getEnabledSubtypesLabel(this.mContext, this.mImm, this.mImi);
            if (!TextUtils.isEmpty(summary)) {
                this.mSubtypeEnablerPreference.setSummary(summary);
            }
            if (this.mSubtypeEnablerIconRes != 0) {
                this.mSubtypeEnablerPreference.setIcon(this.mSubtypeEnablerIconRes);
            } else if (this.mSubtypeEnablerIcon != null) {
                this.mSubtypeEnablerPreference.setIcon(this.mSubtypeEnablerIcon);
            }
        }
    }
}
