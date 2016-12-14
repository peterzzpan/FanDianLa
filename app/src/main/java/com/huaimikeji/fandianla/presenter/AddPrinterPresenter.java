package com.huaimikeji.fandianla.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import com.huaimikeji.fandianla.framework.BasePresenter;
import com.huaimikeji.fandianla.framework.MainApplication;
import com.huaimikeji.fandianla.model.PrinterBean;
import com.huaimikeji.fandianla.model.provider.BTPrinterPrviderHelper;
import com.huaimikeji.fandianla.view.IAddPrinterView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/14.
 */
public class AddPrinterPresenter extends BasePresenter{

    private final static String TAG = AddPrinterPresenter.class.getSimpleName();
    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private IAddPrinterView mAddPrinterView;

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private static OutputStream mOutputStream;

    private BluetoothSocket mBluetoothSocket;

    private BTPrinterPrviderHelper mDBHelper;

    private boolean isConnected = false;

    private boolean isConnecting = false;

    public AddPrinterPresenter(IAddPrinterView addPrinterView){
        this.mAddPrinterView = addPrinterView;
        mDBHelper = new BTPrinterPrviderHelper();

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
        isConnected = false;
        if(mOutputStream!=null){
            try {
                mOutputStream.close();
                mBluetoothSocket.close();
            } catch (IOException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            }
        }
        mBluetoothSocket = null;
        mBluetoothAdapter = null;
    }

    /**
     * 添加蓝牙打印机
     * @param device
     */
    public void addBTPrinter(BluetoothDevice device,PrinterBean bean){
        mAddPrinterView.showProgress(null);
        BTAsyncTAsk asyncTAsk = new BTAsyncTAsk(device,bean);
        asyncTAsk.execute();
    }

    /**
     * 蓝牙配对和连接
     */
    private class BTAsyncTAsk extends AsyncTask<Void, Integer, Boolean>{

        private BluetoothDevice device;
        private PrinterBean bean;

        public BTAsyncTAsk(BluetoothDevice device,PrinterBean bean){
            this.device = device;
            this.bean = bean;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            if(BTPrinterManager.getInstance().pairBTDevice(device)){
//                if(connectBTDevice(device)){
//                    return true;
//                }
//            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
//                printTestData();

            }
            mDBHelper.addPrinterDB(MainApplication.getContext(),bean);
            mAddPrinterView.dismissProgress();
        }
    }
}
