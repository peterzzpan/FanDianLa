package com.huaimikeji.fandianla.framework;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MainApplication extends Application {

    public static MainApplication sInstance;


    public static MainApplication getInstance(){
        return sInstance;
    }
    public static Context getContext(){

        return getInstance().getBaseContext();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
    }
}
