package com.huaimikeji.fandianla.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.huaimikeji.fandianla.R;
import com.huaimikeji.fandianla.framework.BaseActivity;
import com.huaimikeji.fandianla.framework.BasePresenter;
import com.huaimikeji.fandianla.framework.MainApplication;
import com.huaimikeji.fandianla.view.ISearchPrinterView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13.
 */
public class SearchPrinterPresenter extends BasePresenter{

    private static String TAG = SearchPrinterPresenter.class.getSimpleName();

    private ISearchPrinterView mSearchPrinterView;

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    // 用于存放未配对蓝牙设备
    private ArrayList<BluetoothDevice> mUnbondDevices = null;

    // 用于存放已配对蓝牙设备
    private ArrayList<BluetoothDevice> mBondDevices = null;

    public SearchPrinterPresenter(ISearchPrinterView searchPrinterView){
        this.mSearchPrinterView = searchPrinterView;
        registerBTReceiver();
    }

    /**
     * 打开蓝牙
     * @param activity
     */
    private void openBluethooth(BaseActivity activity){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 1);
    }

    /**
     * 搜索蓝牙设备
     */
    public void searchBluethooth(BaseActivity activity){
        if(!isOpen()){
            openBluethooth(activity);
        }else{
            startSearchBl();
        }
    }

    /**
     * 判断蓝牙是否打开
     * @return true 开启
     */
    private boolean isOpen(){
        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
     */
    private void startSearchBl(){
        this.mBluetoothAdapter.startDiscovery();
    }

    /**
     *
     */
    private void registerBTReceiver() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        MainApplication.getContext().registerReceiver(mReceiver, intentFilter);
    }

    private void unregisterBTReceiver(){
        try {
            MainApplication.getContext().unregisterReceiver(mReceiver);
        }catch (Exception e){
            Log.d(TAG, Log.getStackTraceString(e));
        }
    }

    /**
     * 添加蓝牙设备
     * @param device
     */
    private void addBTDevices(BluetoothDevice device){
        if(device==null){
            return;
        }
//        if(device.getBondState() == BluetoothDevice.BOND_BONDED){
//            Log.d(TAG, "已绑定设备");
//            //已经绑定设备
//            if(mBondDevices==null){
//                mBondDevices = new ArrayList<BluetoothDevice>();
//            }
//            mBondDevices.add(device);
//        }else{
            //未绑定设备
            if(mUnbondDevices==null){
                mUnbondDevices = new ArrayList<BluetoothDevice>();
            }
            Log.d(TAG, "未绑定设备");
            mUnbondDevices.add(device);
//        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addBTDevices(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //开始搜索
                mSearchPrinterView.showProgress(null);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //设备搜索完毕
                mSearchPrinterView.dismissProgress();
                mSearchPrinterView.btSearchFinish(mUnbondDevices);
            }else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                //蓝牙状态发生变化 ：开启；关闭
                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    mSearchPrinterView.showToast(R.string.bt_open);
                }else{
                    mSearchPrinterView.showToast(R.string.bt_close);
                }
            }
        }
    };

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
        unregisterBTReceiver();
    }
}
