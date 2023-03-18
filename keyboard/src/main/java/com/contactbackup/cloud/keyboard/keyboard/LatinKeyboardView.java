package com.contactbackup.cloud.keyboard.keyboard;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;

import androidx.annotation.RequiresApi;

public class LatinKeyboardView extends KeyboardView {
    static final int KEYCODE_LANGUAGE_SWITCH = -101;
    static final int KEYCODE_OPTIONS = -100;
    Paint paint = new Paint();

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @RequiresApi(api = 19)
    protected boolean onLongPress(Key key) {
        this.paint.setTypeface(Typeface.DEFAULT_BOLD);
        if (key.codes[0] == -3) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else if (key.codes[0] == 48) {
            getOnKeyboardActionListener().onKey(43, null);
            return true;
        } else if (key.codes[0] == 113 || key.codes[0] == 81 || key.codes[0] == 1777) {
            getOnKeyboardActionListener().onKey(49, null);
            return true;
        } else if (key.codes[0] == 119 || key.codes[0] == 87 || key.codes[0] == 1778) {
            getOnKeyboardActionListener().onKey(50, null);
            return true;
        } else if (key.codes[0] == 101 || key.codes[0] == 69 || key.codes[0] == 1779) {
            getOnKeyboardActionListener().onKey(51, null);
            return true;
        } else if (key.codes[0] == 114 || key.codes[0] == 82 || key.codes[0] == 1780) {
            getOnKeyboardActionListener().onKey(52, null);
            return true;
        } else if (key.codes[0] == 116 || key.codes[0] == 84 || key.codes[0] == 1781) {
            getOnKeyboardActionListener().onKey(53, null);
            return true;
        } else if (key.codes[0] == 121 || key.codes[0] == 89 || key.codes[0] == 1782) {
            getOnKeyboardActionListener().onKey(54, null);
            return true;
        } else if (key.codes[0] == 117 || key.codes[0] == 85 || key.codes[0] == 1783) {
            getOnKeyboardActionListener().onKey(55, null);
            return true;
        } else if (key.codes[0] == 105 || key.codes[0] == 73 || key.codes[0] == 1784) {
            getOnKeyboardActionListener().onKey(56, null);
            return true;
        } else if (key.codes[0] == 111 || key.codes[0] == 79 || key.codes[0] == 1785) {
            getOnKeyboardActionListener().onKey(57, null);
            return true;
        } else if (key.codes[0] == 112 || key.codes[0] == 80 || key.codes[0] == 1776) {
            getOnKeyboardActionListener().onKey(48, null);
            return true;
        } else if (key.codes[0] == 97 || key.codes[0] == 65) {
            getOnKeyboardActionListener().onKey(64, null);
            return true;
        } else if (key.codes[0] == 115 || key.codes[0] == 83) {
            getOnKeyboardActionListener().onKey(35, null);
            return true;
        } else if (key.codes[0] == 100 || key.codes[0] == 68) {
            getOnKeyboardActionListener().onKey(36, null);
            return true;
        } else if (key.codes[0] == 102 || key.codes[0] == 70) {
            getOnKeyboardActionListener().onKey(37, null);
            return true;
        } else if (key.codes[0] == 103 || key.codes[0] == 71) {
            getOnKeyboardActionListener().onKey(38, null);
            return true;
        } else if (key.codes[0] == 104 || key.codes[0] == 72) {
            getOnKeyboardActionListener().onKey(45, null);
            return true;
        } else if (key.codes[0] == 106 || key.codes[0] == 74) {
            getOnKeyboardActionListener().onKey(43, null);
            return true;
        } else if (key.codes[0] == 107 || key.codes[0] == 75) {
            getOnKeyboardActionListener().onKey(40, null);
            return true;
        } else if (key.codes[0] == 108 || key.codes[0] == 76) {
            getOnKeyboardActionListener().onKey(41, null);
            return true;
        } else if (key.codes[0] == 122 || key.codes[0] == 90) {
            getOnKeyboardActionListener().onKey(42, null);
            return true;
        } else if (key.codes[0] == 120 || key.codes[0] == 88) {
            getOnKeyboardActionListener().onKey(34, null);
            return true;
        } else if (key.codes[0] == 99 || key.codes[0] == 67) {
            getOnKeyboardActionListener().onKey(39, null);
            return true;
        } else if (key.codes[0] == 118 || key.codes[0] == 86) {
            getOnKeyboardActionListener().onKey(58, null);
            return true;
        } else if (key.codes[0] == 98 || key.codes[0] == 66) {
            getOnKeyboardActionListener().onKey(59, null);
            return true;
        } else {
            if (key.codes[0] == 110 || key.codes[0] == 78) {
                getOnKeyboardActionListener().onKey(33, null);
            } else if (key.codes[0] == 109 || key.codes[0] == 77) {
                getOnKeyboardActionListener().onKey(63, null);
                return true;
            } else if (key.codes[0] == 1575) {
                getOnKeyboardActionListener().onKey(1570, null);
                return true;
            } else if (key.codes[0] == 1590) {
                getOnKeyboardActionListener().onKey(1633, null);
                return true;
            } else if (key.codes[0] == 1589) {
                getOnKeyboardActionListener().onKey(1634, null);
                return true;
            } else if (key.codes[0] == 1579) {
                getOnKeyboardActionListener().onKey(1635, null);
                return true;
            } else if (key.codes[0] == 1602) {
                getOnKeyboardActionListener().onKey(1636, null);
                return true;
            } else if (key.codes[0] == 1601) {
                getOnKeyboardActionListener().onKey(1637, null);
                return true;
            } else if (key.codes[0] == 1594) {
                getOnKeyboardActionListener().onKey(1638, null);
                return true;
            } else if (key.codes[0] == 1593) {
                getOnKeyboardActionListener().onKey(1639, null);
                return true;
            } else if (key.codes[0] == 1607) {
                getOnKeyboardActionListener().onKey(1640, null);
                return true;
            } else if (key.codes[0] == 1582) {
                getOnKeyboardActionListener().onKey(1641, null);
                return true;
            } else if (key.codes[0] == 1581) {
                getOnKeyboardActionListener().onKey(1632, null);
                return true;
            }
            return super.onLongPress(key);
        }
    }

    void setSubtypeOnSpaceKey(InputMethodSubtype subtype) {
        LatinKeyboard keyboard = (LatinKeyboard) getKeyboard();
        invalidateAllKeys();
    }
}
