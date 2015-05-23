package org.bitoo.abit.ui;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import org.bitoo.abit.R;
import org.bitoo.abit.utils.FileHandler;

import java.io.IOException;

public class MainApp extends Application {
    private static final String TAG = "MainApp";
    public static final String VERSION_KEY = "first_launch";

    boolean first = false;

    @Override
    public void onCreate() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int checkNum = preferences.getInt(VERSION_KEY, 0);
        if (checkNum == 0) {
            initApp();
            first = true;
            preferences.edit().putInt(VERSION_KEY, 1).apply();
        }

        super.onCreate();
    }

    /**
     * Called when app first lauched.
     */
    private void initApp(){
        // Create database and table
        SQLiteDatabase db = this.openOrCreateDatabase("ABit.db", Context.MODE_PRIVATE, null);
        /**
         * CREATE TABLE mission(
         * id INTEGER PRIMARY KEY AUTOINCRECEMENT,
         * title TEXT NOT NULL,
         * image_name VARCHAR(20) NOT NULL,
         * progress_mask VARCHAR(100) NOT NULL,
         * first_day DATE NOT NULL,
         * last_day DATE NOT NULL
         * );
         */
        db.execSQL("CREATE TABLE mission(\n" +
                " id INTEGER PRIMARY KEY AUTOINCRECEMENT,\n" +
                " title TEXT NOT NULL,\n" +
                " image_name VARCHAR(20) NOT NULL,\n" +
                " progress_mask VARCHAR(100) NOT NULL,\n" +
                " first_day DATE NOT NULL,\n" +
                " last_day DATE NOT NULL);");

        //save raw images into internal storage
        try {
            // FIXME : All the source files should be moved,is there a way to traversal R.raw?
            FileHandler.copyFile(this, getResources().openRawResource(R.raw.mario), "mario.xml");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Can't load image source");
        }
    }

    public boolean isFirst() {
        return first;
    }

}
