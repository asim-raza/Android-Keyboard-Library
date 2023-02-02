package com.maxcodestudio.keyboard.lib.Keyboard_inputMethod;

import android.graphics.drawable.Drawable;

public interface InputMethodSettingsInterface {
    void setInputMethodSettingsCategoryTitle(int i);

    void setInputMethodSettingsCategoryTitle(CharSequence charSequence);

    void setSubtypeEnablerIcon(int i);

    void setSubtypeEnablerIcon(Drawable drawable);

    void setSubtypeEnablerTitle(int i);

    void setSubtypeEnablerTitle(CharSequence charSequence);
}
