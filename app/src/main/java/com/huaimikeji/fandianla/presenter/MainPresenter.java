package com.huaimikeji.fandianla.presenter;

import android.app.Activity;
import android.content.Intent;

import com.huaimikeji.fandianla.framework.BasePresenter;
import com.huaimikeji.fandianla.view.PrinterActivity;
import com.huaimikeji.fandianla.view.IMainView;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MainPresenter extends BasePresenter{
    private IMainView mMianView;

    public MainPresenter(IMainView mainView){
        this.mMianView = mainView;
    }

    public void clickPrinterBtn(Activity activity){
        Intent intent = new Intent(activity, PrinterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestory() {

    }
}
