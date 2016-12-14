package com.huaimikeji.fandianla.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huaimikeji.fandianla.R;
import com.huaimikeji.fandianla.presenter.EmptyPrinterPresenter;
import com.huaimikeji.fandianla.util.ToastHelper;

public class PrinterActivity extends Activity implements IEmptyPrinterView,View.OnClickListener{


    private EmptyPrinterPresenter mEmptyPrsenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_printer);
        mEmptyPrsenter = new EmptyPrinterPresenter(this);
        initView();
    }

    private void initView(){
        TextView titleText = (TextView)findViewById(R.id.title);
        findViewById(R.id.back_btn).setOnClickListener(this);
        titleText.setText(R.string.fandianle_printer);
        findViewById(R.id.add_kitchen_printer).setOnClickListener(this);
        findViewById(R.id.add_reception_printer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_kitchen_printer:
                mEmptyPrsenter.addPrinter(AddPrinterActivity.FLAG_KITCHEN,this);
                break;
            case R.id.add_reception_printer:
                mEmptyPrsenter.addPrinter(AddPrinterActivity.GLAG_RECEPTION,this);
                break;
            case R.id.back_btn:
                super.onBackPressed();
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
