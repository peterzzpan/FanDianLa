package com.huaimikeji.fandianla.presenter;

import android.app.Activity;
import android.content.Intent;
import android.widget.BaseExpandableListAdapter;

import com.huaimikeji.fandianla.framework.BasePresenter;
import com.huaimikeji.fandianla.view.AddPrinterActivity;
import com.huaimikeji.fandianla.view.IEmptyPrinterView;
import com.huaimikeji.fandianla.view.SearchPrinterActivity;

/**
 * Created by Administrator on 2016/4/12.
 */
public class EmptyPrinterPresenter extends BasePresenter{

    private IEmptyPrinterView iEmptyPrinterView;

    public EmptyPrinterPresenter(IEmptyPrinterView emptyPrinterView){
        this.iEmptyPrinterView = emptyPrinterView;
    }

    public void addPrinter(int flag,Activity activity){
//        Intent intent = AddPrinterActivity.getIntent(flag);
//        intent.setClass(activity,AddPrinterActivity.class);
//        activity.startActivity(intent);

        Intent intent = SearchPrinterActivity.getIntent(flag);
        intent.setClass(activity,SearchPrinterActivity.class);
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
