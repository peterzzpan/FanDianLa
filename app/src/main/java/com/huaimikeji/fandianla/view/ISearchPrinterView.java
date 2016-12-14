package com.huaimikeji.fandianla.view;

import android.bluetooth.BluetoothDevice;

import com.huaimikeji.fandianla.framework.IBaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface ISearchPrinterView extends IBaseView{

    public void btSearchFinish(ArrayList<BluetoothDevice> mDevices);

}
