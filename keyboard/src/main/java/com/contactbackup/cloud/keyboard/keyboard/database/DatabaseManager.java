package com.contactbackup.cloud.keyboard.keyboard.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.util.Log;

import com.contactbackup.cloud.keyboard.R;

import java.util.ArrayList;

public class DatabaseManager {
    private Cursor cursor;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private final Context mContext;

    public DatabaseManager(Context context) {
        if (this.db != null) {
            this.db.close();
        }
        this.mContext = context;
        this.dbHelper = new DatabaseHelper(this.mContext, getDatabaseName());
        this.db = this.dbHelper.openDataBase();
        Log.d("Create Database", "Database");
    }

    public ArrayList<String> getAllRow(String str, String subType) {
        ArrayList<String> wordList = new ArrayList();
        try {
            queryString(str, subType);
            this.cursor.moveToFirst();
            if (!this.cursor.isAfterLast()) {
                do {
                    wordList.add(String.valueOf(Html.fromHtml(String.valueOf(this.cursor.getString(0)))));
                } while (this.cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB ERROR", e.toString());
        }
        return wordList;
    }

    private Cursor queryString(String str, String subType) {
        int a = 0;
        switch (subType.hashCode()) {
            case -1603757456:
                if (subType.equals("english")) {
                    a = 0;
                    break;
                }
                break;
            case -995391231:
                if (subType.equals("pashto")) {
                    a = 1;
                    break;
                }
                break;
        }
        switch (a) {
            case 0:
                this.cursor = this.db.rawQuery("SELECT " + getWordColumnName() + " FROM " + getEnglishTableName() + " WHERE " + getWordColumnName() + " LIKE '" + str + "%' ORDER BY " + getFreqColumnName() + " DESC LIMIT 3", null);
                break;
            case 1:
                this.cursor = this.db.rawQuery("SELECT " + getWordColumnName() + " FROM " + getEnglishTableName() + " WHERE " + getWordColumnName() + " LIKE '" + str + "%' ORDER BY " + getWordColumnName() + " LIMIT 3", null);
                break;
        }
        return this.cursor;
    }

    public void insertNewRecord(String str, String tableName) {
        String insertQuery = "INSERT INTO " + tableName + "(" + getFreqColumnName() + ", " + getWordColumnName() + " VALUES ('" + 200 + "', '" + str + "' )";
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.execSQL(insertQuery);
        db.close();
    }

    public void updateFreq(String str) {
        try {
            String insertQuery = "UPDATE " + getEnglishTableName() + " SET freq = freq + 1 WHERE " + getWordColumnName() + " = '" + str + "'";
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            db.execSQL(insertQuery);
            db.close();
        } catch (Exception e) {
            Log.d("TAG", e.toString());
        }
    }

    private String getDatabaseName() {
        return this.mContext.getResources().getString(R.string.database_name);
    }

    private String getEnglishTableName() {
        return this.mContext.getResources().getString(R.string.en_table_name);
    }

    private String getPashtoTableName() {
        return this.mContext.getResources().getString(R.string.pa_table_name);
    }

    private String getFreqColumnName() {
        return this.mContext.getResources().getString(R.string.freq_column);
    }

    private String getWordColumnName() {
        return this.mContext.getResources().getString(R.string.word_column);
    }

    public void close() {
        if (this.cursor != null) {
            this.cursor.close();
        }
        if (this.db != null) {
            this.db.close();
        }
        Log.d("Close", "Database");
    }
}
