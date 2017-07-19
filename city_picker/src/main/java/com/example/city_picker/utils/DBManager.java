package com.example.city_picker.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.city_picker.CityBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixi_tianrui1 on 16-9-25.
 */
public class DBManager {
    private static DBManager sInstance;
    private Context mContext;
    public final String DB_NAME = "china_cities.db";
    public final String DB_PATH;

    public interface CITY_COLUMN {
        String TAB_NAME = "city";
        String COL_ID = "id";
        String COL_NAME = "name";
        String COL_PINYIN = "pinyin";
    }

    public static DBManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DBManager.class) {
                if (sInstance == null) {
                    sInstance = new DBManager(context);
                }
            }
        }
        return sInstance;
    }


    private DBManager(Context context) {
        mContext = context.getApplicationContext();
        DB_PATH = File.separator + "data" + Environment.getDataDirectory().getAbsolutePath() +
                File.separator + mContext.getPackageName() + File.separator + "databases" + File.separator;
        L.d(DB_PATH);
        loadDefaultCityList();
    }


    private void loadDefaultCityList() {
        File databaseFile = new File(DB_PATH + DB_NAME);
        if (databaseFile.exists()) {
            return;
        }
        try {
            InputStream is = mContext.getResources().getAssets().open(DB_NAME);
            File path = new File(DB_PATH);
            if (path.mkdirs()) {
                FileOutputStream fos = new FileOutputStream(databaseFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.close();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CityBean> getAllCities() {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = database.query(CITY_COLUMN.TAB_NAME, null, null, null, null, null, "pinyin asc");
        List<CityBean> allCities = new ArrayList<>();
        while (cursor.moveToNext()) {
            allCities.add(cursor2Pojo(cursor));
        }
        cursor.close();
        L.d(allCities.toString());
        return allCities;
    }


    private CityBean cursor2Pojo(Cursor cursor) {
        if (cursor == null) return null;
        CityBean cityBean = new CityBean();
        cityBean.setId(cursor.getInt(cursor.getColumnIndex(CITY_COLUMN.COL_ID)));
        cityBean.setName(cursor.getString(cursor.getColumnIndex(CITY_COLUMN.COL_NAME)));
        cityBean.setPinyin(cursor.getString(cursor.getColumnIndex(CITY_COLUMN.COL_PINYIN)));
        return cityBean;
    }


}
