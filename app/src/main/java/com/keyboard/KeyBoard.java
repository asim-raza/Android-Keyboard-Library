package com.keyboard;

import android.util.Log;

import com.contactbackup.cloud.keyboard.keyboard.LatinKeyboard;
import com.contactbackup.cloud.keyboard.keyboard.SoftKeyboard;

public class KeyBoard extends SoftKeyboard {

    private String TAG = "KeyBoard";

    @Override
    public void onInitializeInterface() {
        try {
            if (this.mQwertyKeyboard != null) {
                int displayWidth = getMaxWidth();
                if (displayWidth != this.mLastDisplayWidth) {
                    this.mLastDisplayWidth = displayWidth;
                } else {
                    return;
                }
            }
            this.mQwertyKeyboard = new LatinKeyboard(this, R.xml.eng);
            Log.d("COUNT", this.mQwertyKeyboard.getKeys().size() + " size");
            this.mSymbolsKeyboard = new LatinKeyboard(this, R.xml.symbols);
            this.mSymbolsShiftedKeyboard = new LatinKeyboard(this, R.xml.symbols_second);
            this.mUrduKeyboard = new LatinKeyboard(this, R.xml.lang);
            this.mUrduKeyboardC = new LatinKeyboard(this, R.xml.lang_second);
            this.mNumbersKeyboard = new LatinKeyboard(this, R.xml.numbers);
            this.mSymbolsUrKeyboard = new LatinKeyboard(this, R.xml.lang_symbols);
            this.mSymbolsShiftedUrKeyboard = new LatinKeyboard(this, R.xml.lang_symbols_second);
            this.mPhoneKeyboard = new LatinKeyboard(this, R.xml.phone);


        } catch (RuntimeException e) {
            Log.d(TAG, e.toString());
        }

    }
}
