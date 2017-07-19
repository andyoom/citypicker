package com.example.city_picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CityListActivity extends AppCompatActivity {

    private CityPickController mController;
    public static final String RESULT_KEY = "city";
    public static final int REQUEST_CODE =101;
    public static final int RESULT_CODE =102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);
        mController = new CityPickController(this, findViewById(android.R.id.content));
    }

    public static void startCityActivityForResult(Activity activity){
        Intent intent = new Intent(activity, CityListActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void setResult2City(CityBean bean){
        Intent intent=new Intent();
        intent.putExtra(RESULT_KEY,bean.getName());
        setResult(RESULT_CODE,intent);
        finish();
    }
}
