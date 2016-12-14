package com.huaimikeji.fandianla.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huaimikeji.fandianla.R;
import com.huaimikeji.fandianla.model.PrinterBean;
import com.huaimikeji.fandianla.presenter.AddPrinterPresenter;
import com.huaimikeji.fandianla.util.ToastHelper;

import org.w3c.dom.Text;

public class AddPrinterActivity extends Activity implements IAddPrinterView,View.OnClickListener{

    private static final String TAG = AddPrinterActivity.class.getSimpleName();
    private AddPrinterPresenter mAddPrinterPresenter;
    public static final int GLAG_RECEPTION = 0;
    public static final int FLAG_KITCHEN = 1;
    public static final String PRINTER_FLAG = "printer_flag";
    public static final String SELECT_BT_DEVICE = "select_bt_device";
    private int mPrinterIndex = 0;
    private BluetoothDevice mDevice;
    private PrinterBean mPrinter = new PrinterBean();
    private EditText mPrinterName;
    private TextView mPrinterStyle;
    private CheckBox mReceptionBill;
    private CheckBox mKitchenBill;
    private CheckBox mStubBill;
    private TextView mPrintCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_printer);
        mAddPrinterPresenter = new AddPrinterPresenter(this);
        onHandleIntent(getIntent());
        intView();
    }

    private void intView(){
        TextView title = (TextView)findViewById(R.id.title);
        findViewById(R.id.back_btn).setOnClickListener(this);
        mPrinterName = (EditText)findViewById(R.id.ed_printer_name);
        mPrinterStyle = (TextView)findViewById(R.id.printer_style);
        mReceptionBill = (CheckBox)findViewById(R.id.print_reception_bill);
        mKitchenBill = (CheckBox)findViewById(R.id.print_kitchen_bill);
        mStubBill = (CheckBox)findViewById(R.id.print_stub_bill);
        mPrintCount = (TextView)findViewById(R.id.print_count);

        RadioGroup printerGroup = (RadioGroup)findViewById(R.id.printer_format);
        printerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.printer_format1){
                    mPrinter.setmPrinterFormat("58mm");
                }else if(checkedId == R.id.printer_format2){
                    mPrinter.setmPrinterFormat("80mm");
                }
            }
        });

        findViewById(R.id.count_remove).setOnClickListener(this);
        findViewById(R.id.count_add).setOnClickListener(this);
        findViewById(R.id.save_printer).setOnClickListener(this);
        findViewById(R.id.delete_printer).setOnClickListener(this);
        if(mPrinterIndex == GLAG_RECEPTION){
            title.setText("设置前台打印机");
            mPrinterStyle.setVisibility(View.VISIBLE);
            mReceptionBill.setVisibility(View.VISIBLE);
            mKitchenBill.setVisibility(View.VISIBLE);
            mStubBill.setVisibility(View.VISIBLE);
        }else{
            title.setText("设置后厨打印机");
            mPrinterStyle.setVisibility(View.GONE);
            mReceptionBill.setVisibility(View.GONE);
            mKitchenBill.setVisibility(View.GONE);
            mStubBill.setVisibility(View.GONE);
        }
    }

    private void onHandleIntent(Intent intent){
        if(intent == null){
            return;
        }
        mPrinterIndex = intent.getIntExtra(PRINTER_FLAG,GLAG_RECEPTION);
        mDevice = (BluetoothDevice)intent.getParcelableExtra(SELECT_BT_DEVICE);
        Log.d(TAG,"device-->"+(mDevice == null?null:mDevice.toString()));
    }

    public static Intent getIntent(int flag,BluetoothDevice device){
        Intent intent = new Intent();
        intent.putExtra(PRINTER_FLAG,flag);
        intent.putExtra(SELECT_BT_DEVICE,device);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_printer:
                StringBuffer style = new StringBuffer();
                style.append(mReceptionBill.isChecked() ? "1":"0" + ";");
                style.append(mKitchenBill.isChecked() ? "1":"0" + ";");
                style.append(mStubBill.isChecked() ? "1" : "0");
                mPrinter.setmPrinterStyle(style.toString());
                mPrinter.setmPrinterAliasName(mPrinterName.getText().toString());
                mPrinter.setmPrinterCount(Integer.parseInt(mPrintCount.getText().toString()));
                mPrinter.setmPrinterName(mDevice.getName());
                mPrinter.setmPrinterAddress(mDevice.getAddress());
//                mPrinter.setmPrinterUuid(mDevice.getUuids());

                mAddPrinterPresenter.addBTPrinter(mDevice,mPrinter);
                break;
            case R.id.delete_printer:
                break;
            case R.id.count_remove:

                break;
            case R.id.count_add:
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

    private ProgressDialog mProgressDialog;
    @Override
    public void showProgress(Object object) {
        if(this.isFinishing()){
            return;
        }
        mProgressDialog = ProgressDialog.show(this,"连接蓝牙打印机","大约需要一分钟时间…");
    }

    @Override
    public void dismissProgress() {
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }


}
