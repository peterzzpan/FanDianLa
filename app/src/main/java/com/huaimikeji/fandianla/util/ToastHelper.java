package com.huaimikeji.fandianla.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ToastHelper {

    public static void showToast(Context context,String msg){
        if(TextUtils.isEmpty(msg)){
           return;
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context,int msgId){
        Toast.makeText(context,msgId,Toast.LENGTH_SHORT).show();
    }
}
