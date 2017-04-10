package com.sunny.xianyuenews.Utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.sunny.xianyuenews.MyApplication;


/**
 * Created by Administrator on 2017/2/20.
 */

public class ToastUtils {
    public static void showLong(String value){
        if (!TextUtils.isEmpty(value)){
            Toast.makeText(MyApplication.getContext(), value, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShort(String value){
        if (!TextUtils.isEmpty(value)){
            Toast.makeText(MyApplication.getContext(), value, Toast.LENGTH_SHORT).show();
        }
    }
}
