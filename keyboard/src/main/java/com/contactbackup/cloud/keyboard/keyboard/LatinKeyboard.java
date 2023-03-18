package com.contactbackup.cloud.keyboard.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;

import com.contactbackup.cloud.keyboard.R;


public class LatinKeyboard extends Keyboard {
    private Key mEnterKey;
    private Key mLanguageSwitchKey;
    private Key mModeChangeKey;
    private Key mSavedLanguageSwitchKey;
    private Key mSavedModeChangeKey;
    private Key mSpaceKey;

    private static class LatinKey extends Key {
        LatinKey(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
            super(res, parent, x, y, parser);
        }

        public boolean isInside(int x, int y) {
            if (this.codes[0] == -3) {
                y -= 10;
            }
            return super.isInside(x, y);
        }
    }

    public LatinKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public LatinKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = new LatinKey(res, parent, x, y, parser);
        if (key.codes[0] == 10) {
            this.mEnterKey = key;
        } else if (key.codes[0] == 32) {
            this.mSpaceKey = key;
        } else if (key.codes[0] == -2) {
            this.mModeChangeKey = key;
            this.mSavedModeChangeKey = new LatinKey(res, parent, x, y, parser);
        } else if (key.codes[0] == -101) {
            this.mLanguageSwitchKey = key;
            this.mSavedLanguageSwitchKey = new LatinKey(res, parent, x, y, parser);
        }
        return key;
    }

    void setLanguageSwitchKeyVisibility(boolean visible) {
        if (visible) {
            if (!(this.mModeChangeKey == null || this.mSavedModeChangeKey == null)) {
                this.mModeChangeKey.width = this.mSavedModeChangeKey.width;
                this.mModeChangeKey.x = this.mSavedModeChangeKey.x;
            }
            if (this.mLanguageSwitchKey != null && this.mSavedLanguageSwitchKey != null) {
                this.mLanguageSwitchKey.width = this.mSavedLanguageSwitchKey.width;
                this.mLanguageSwitchKey.icon = this.mSavedLanguageSwitchKey.icon;
                this.mLanguageSwitchKey.iconPreview = this.mSavedLanguageSwitchKey.iconPreview;
                return;
            }
            return;
        }
        this.mModeChangeKey.width = this.mSavedModeChangeKey.width + this.mSavedLanguageSwitchKey.width;
        this.mLanguageSwitchKey.width = 0;
        this.mLanguageSwitchKey.icon = null;
        this.mLanguageSwitchKey.iconPreview = null;
    }

    void setImeOptions(Resources res, int options) {
        if (this.mEnterKey != null) {
            switch (1073742079 & options) {
                case 2:
                    this.mEnterKey.iconPreview = null;
                    this.mEnterKey.icon = null;
                    this.mEnterKey.label = res.getText(R.string.label_go_key);
                    return;
                case 3:
                    this.mEnterKey.icon = res.getDrawable(R.drawable.ic_search1);
                    this.mEnterKey.label = null;
                    return;
                case 4:
                    this.mEnterKey.iconPreview = null;
                    this.mEnterKey.icon = null;
                    this.mEnterKey.label = res.getText(R.string.label_send_key);
                    return;
                case 5:
                    this.mEnterKey.iconPreview = null;
                    this.mEnterKey.icon = res.getDrawable(R.drawable.ic_search1);
                    this.mEnterKey.label = res.getText(R.string.label_next_key);
                    return;
                default:
                    this.mEnterKey.icon = res.getDrawable(R.drawable.ic_enter);
                    this.mEnterKey.label = null;
                    return;
            }
        }
    }

    void setSpaceIcon(Drawable icon) {
        if (this.mSpaceKey != null) {
            this.mSpaceKey.icon = icon;
        }
    }
}
