package com.example.city_picker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.city_picker.R;
import com.example.city_picker.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixi_tianrui1 on 16-9-18.
 * 城市选择列表侧边栏
 */
public class LetterSideBar extends View {

    // 字母列表
    private List<String> mLetters = new ArrayList<>();
    private OnTouchLetterListener mOnTouchLetterListener;
    private TextView mOverLayTextView; // 显示的字母

    private int mWidth;
    private int mHeight;
    private int mEachLetterHeight;
    private int mEachLetterWidth;

    private Paint mPaint;

    private boolean maskShown; // 是否显示背景
    private int mSelectedIndex = -1; // 已选择的字母

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();

        // for test
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('A' + i);
            Log.v("LOG", letter + "");
            mLetters.add(String.valueOf(letter));
        }
    }


    /**
     * 测量并绘制所有的大写字母
     *
     * @param canvas 系统画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setTextSize(getResources().getDimension(R.dimen.side_bar_letter));
        if (mLetters.size() > 0) {
            mEachLetterHeight = mHeight / mLetters.size();
        } else {
            mEachLetterHeight = mHeight;
        }
        // 绘制所有显示的字母
        mEachLetterWidth = (int) (mWidth / 2 - mPaint.measureText(mLetters.get(0)) / 2);
//        Log.v("LOG", "eachLetterWidth:" + mEachLetterWidth);
        for (int i = 0; i < mLetters.size(); i++) {
            mPaint.setColor(Color.RED);
            if (mSelectedIndex == i && mSelectedIndex != -1) {
                mPaint.setColor(Color.CYAN); // 设置选中时的颜色
            }
            canvas.drawText(mLetters.get(i), mEachLetterWidth, i * mEachLetterHeight + mEachLetterHeight, mPaint);
        }
    }


    /**
     * 处理触摸事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        // 获取点击的字母
        int letterIndex = (int) ((y / mHeight) * mLetters.size());
        switch (action) {
            // 选中字母高亮
            case MotionEvent.ACTION_DOWN:
                L.d("Letter is : " + mLetters.get(letterIndex));
                selectedLetter(letterIndex);
                break;
            // 在字母范围内,仍视为选中字母
            case MotionEvent.ACTION_MOVE:
                selectedLetter(letterIndex);
                break;
            case MotionEvent.ACTION_UP:
                mSelectedIndex = -1;
                invalidate();
                hideOverLayText();
                break;
        }
        return true;
    }

    public void setLetterList(List<String> list) {
        this.mLetters = list;
    }


    public void setOnTouchLetterListener(OnTouchLetterListener onTouchLetterListener) {
        this.mOnTouchLetterListener = onTouchLetterListener;
    }


    /**
     * 通过此接口设置ListView的position
     */
    public interface OnTouchLetterListener {
        void onLetterSelected(String letter);
    }


    /**
     * 高亮显示选中的字母,并显示mask
     */
    private void selectedLetter(int letterIndex) {
        if (letterIndex >= 0 && letterIndex < mLetters.size()) {
            // 显示遮罩
            maskShown = true;
            if (mOnTouchLetterListener != null) {
                mOnTouchLetterListener.onLetterSelected(mLetters.get(letterIndex));
            }
            mSelectedIndex = letterIndex;
            // 通知重绘
            invalidate();
            // 显示字母
            showOverLayText();
        }
    }

    /**
     * 显示选择的字母
     */
    public void showOverLayText() {
        if (mOverLayTextView != null) {
            mOverLayTextView.setVisibility(View.VISIBLE);
            mOverLayTextView.setText(mLetters.get(mSelectedIndex));
        }
    }

    public void hideOverLayText() {
        if (mOverLayTextView != null) {
            mOverLayTextView.setVisibility(View.GONE);
        }
    }

    public TextView getOverLayTextView() {
        return mOverLayTextView;
    }

    public void setOverLayTextView(TextView overLayTextView) {
        this.mOverLayTextView = overLayTextView;
    }


}
