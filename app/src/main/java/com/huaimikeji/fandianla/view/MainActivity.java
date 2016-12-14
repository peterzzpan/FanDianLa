package com.huaimikeji.fandianla.view;

import android.os.Bundle;
import android.view.View;

import com.huaimikeji.fandianla.R;
import com.huaimikeji.fandianla.framework.BaseActivity;
import com.huaimikeji.fandianla.presenter.MainPresenter;
import com.huaimikeji.fandianla.util.ToastHelper;

public class MainActivity extends BaseActivity implements IMainView,View.OnClickListener{

    private MainPresenter mMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenter(this);
        initView();
    }

    private void initView(){
        findViewById(R.id.back_btn).setVisibility(View.GONE);
        findViewById(R.id.order).setOnClickListener(this);
        findViewById(R.id.order_manager).setOnClickListener(this);
        findViewById(R.id.printer_manager).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order:

                break;
            case R.id.order_manager:

                break;
            case R.id.printer_manager:
                mMainPresenter.clickPrinterBtn(this);
                break;

        }
    }

    @Override
    public void showToast(int msgId) {
        ToastHelper.showToast(this,msgId);
    }

    @Override
    public void showProgress(Object object) {

    }

    @Override
    public void dismissProgress() {

    }
}
