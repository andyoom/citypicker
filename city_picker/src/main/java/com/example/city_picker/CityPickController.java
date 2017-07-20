package com.example.city_picker;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.city_picker.utils.DBManager;
import com.example.city_picker.view.LetterSideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixi_tianrui1 on 16-9-25.
 */
public class CityPickController implements LetterSideBar.OnTouchLetterListener {

    private View mRootView;
    private TextView mTvMask;
    private LetterSideBar mLsSidebar;
    private ListView mLvCityList;
    private CityListActivity mActivity;

    private CityAdapter mAdapter;

    private List<CityBean> mCities = new ArrayList<>();
//    private Handler mHandler=new Handler();


    public CityPickController(CityListActivity context, View root) {
        mRootView = root;
        mActivity = context;
        initView();
        mCities = DBManager.getInstance(mActivity).getAllCities();
        mAdapter = new CityAdapter(mActivity, mCities);
        mLvCityList.setAdapter(mAdapter);
    }

    private void initView() {
        mTvMask = (TextView) mRootView.findViewById(R.id.id_tv_mask);
        mLsSidebar = (LetterSideBar) mRootView.findViewById(R.id.id_ls_sidebar);
        mLvCityList = (ListView) mRootView.findViewById(R.id.id_lv_citys);
        mLsSidebar.setOverLayTextView(mTvMask);
        mLsSidebar.setOnTouchLetterListener(this);

        mLvCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityBean bean =(CityBean) mAdapter.getItem(position);
                mActivity.setResult2City(bean);
            }
        });
//        mLvCityList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    mHandler.postAtFrontOfQueue(new Runnable() {
//                        @Override
//                        public void run() {
//                            mLvCityList.setSelection(0);
//                        }
//                    });
//                }
//            }
//        });
    }


    /**
     * 处理选择字母时的回调
     *
     * @param letter
     */
    @Override
    public void onLetterSelected(String letter) {
        int position = mAdapter.getPosition(letter);
        if (position != -1)
            mLvCityList.setSelection(position);
    }
}
