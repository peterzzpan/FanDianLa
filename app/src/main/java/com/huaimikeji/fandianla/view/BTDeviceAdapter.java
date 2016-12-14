package com.huaimikeji.fandianla.view;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.huaimikeji.fandianla.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14.
 */
public class BTDeviceAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<BluetoothDevice> mBTDevices;
    private View.OnClickListener mClickListener;

    public BTDeviceAdapter(Context context, View.OnClickListener clickListener,ArrayList<BluetoothDevice> bTDevices){
        this.mContext = context;
        this.mBTDevices = bTDevices;
        this.mClickListener = clickListener;
    }

    @Override
    public int getCount() {
        if(mBTDevices==null){
            return 0;
        }
        return mBTDevices.size();
    }

    @Override
    public Object getItem(int position) {
        if(mBTDevices==null){
            return null;
        }
        return mBTDevices.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if(convertView == null){
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_bluethooth_item,null);
            myHolder.mName = (TextView)convertView.findViewById(R.id.bluethooth_name);
            myHolder.mAdd = (Button)convertView.findViewById(R.id.add_bluethooth);
            myHolder.mState = (TextView)convertView.findViewById(R.id.bt_state);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        BluetoothDevice device = mBTDevices.get(position);
        if(device !=null){
            if(device.getBondState() == BluetoothDevice.BOND_BONDED){
                myHolder.mState.setText("已配对");
            }else{
                myHolder.mState.setText("未配对");
            }
            myHolder.mName.setText(device.getName());
            myHolder.mAdd.setOnClickListener(mClickListener);
            myHolder.mAdd.setTag(position);
        }
        return convertView;
    }

    static class MyHolder{
        TextView mName;
        TextView mState;
        Button mAdd;
    }
}
