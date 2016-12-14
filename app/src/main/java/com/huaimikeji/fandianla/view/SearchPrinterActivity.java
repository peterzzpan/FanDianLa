package com.huaimikeji.fandianla.view;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.huaimikeji.fandianla.R;
import com.huaimikeji.fandianla.framework.BaseActivity;
import com.huaimikeji.fandianla.presenter.SearchPrinterPresenter;
import com.huaimikeji.fandianla.util.ToastHelper;

import java.util.ArrayList;

public class SearchPrinterActivity extends BaseActivity implements ISearchPrinterView,View.OnClickListener,AdapterView.OnItemClickListener{

    private SearchPrinterPresenter mSearchPrinterPresenter;
    private ListView mResultListView;
    private int mPrinterIndex;

    public static Intent getIntent(int flag){
        Intent intent = new Intent();
        intent.putExtra(AddPrinterActivity.PRINTER_FLAG, flag);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_printer);
        onHandleIntent(getIntent());
        mSearchPrinterPresenter = new SearchPrinterPresenter(this);
        initView();
    }

    private void onHandleIntent(Intent intent){
        if(intent == null){
            return;
        }
        mPrinterIndex = intent.getIntExtra(AddPrinterActivity.PRINTER_FLAG,AddPrinterActivity.GLAG_RECEPTION);
    }

    public void initView(){
        TextView titleText = (TextView)findViewById(R.id.title);
        findViewById(R.id.back_btn).setOnClickListener(this);
        titleText.setText(R.string.fandianle_printer);
        findViewById(R.id.btn_printer_search).setOnClickListener(this);
        mResultListView = (ListView)findViewById(R.id.result_list);
        mResultListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                super.onBackPressed();
                break;
            case R.id.btn_printer_search:
                mSearchPrinterPresenter.searchBluethooth(this);
                break;
            case R.id.add_bluethooth:
                Integer pos = (Integer)v.getTag();
                goAddPrinterActivity(pos);
                break;
        }
    }

    private ProgressDialog mSearchDialog;
    @Override
    public void showProgress(Object object) {
        if(this.isFinishing()){
            return;
        }
        if(mSearchDialog == null){
            mSearchDialog = ProgressDialog.show(this,"请稍等","搜索设备中…",true);
        }else{
            mSearchDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if(this.isFinishing()){
            return;
        }
        if(mSearchDialog!=null && mSearchDialog.isShowing()){
            mSearchDialog.dismiss();
        }
    }

    private BTDeviceAdapter mDeivcesAdapter;
    @Override
    public void btSearchFinish(ArrayList<BluetoothDevice> mDevices) {
        if(mDevices==null || mDevices.isEmpty()){
            showToast(R.string.no_bt_devices);
        }

        mDeivcesAdapter = new BTDeviceAdapter(this,this,mDevices);
        mResultListView.setAdapter(mDeivcesAdapter);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSearchPrinterPresenter.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        mSearchPrinterPresenter.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mSearchPrinterPresenter.onDestory();
    }

    @Override
    public void showToast(int msgId) {
        ToastHelper.showToast(this, msgId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goAddPrinterActivity(position);
    }

    private void goAddPrinterActivity(int position){
        if(mDeivcesAdapter == null){
            return;
        }
        BluetoothDevice device = (BluetoothDevice)mDeivcesAdapter.getItem(position);
        if(device!=null){
            Intent intent = AddPrinterActivity.getIntent(mPrinterIndex,device);
            intent.setClass(this,AddPrinterActivity.class);
            this.startActivity(intent);
        }
    }
}
