package com.contactbackup.cloud.keyboard.keyboard;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class FontsOverride {
    public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
        replaceFont(staticTypefaceFieldName, Typeface.createFromAsset(context.getAssets(), fontAssetName));
    }

    protected static void replaceFont(String staticTypefaceFieldName, Typeface newTypeface) {
        try {
            Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }
}
