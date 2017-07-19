package com.example.city_picker.utils;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by shixi_tianrui1 on 16-9-15.
 * Log工具类
 */
public class L {

    public static String TAG = "LOG";

    public static void d(String msg) {
        String trace = getTraceMessage();
        Log.d(TAG, trace + "  " + msg);
    }

    @NonNull
    private static String getTraceMessage() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String trace = null;
        int i = 0;
        // 和MyLog相同的下一个栈帧为MyLog调用的栈帧信息
        for (i = 0; i < elements.length; i++) {
            if (elements[i].getClassName().equals(L.class.getName())) {
                break;
            }
        }
        StackTraceElement targetElement = elements[++i];
        trace = "(" + targetElement.getFileName() + ":" + targetElement.getLineNumber() + ")";
        return trace;
    }
}
