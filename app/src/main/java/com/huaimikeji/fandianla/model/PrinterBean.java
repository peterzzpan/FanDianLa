package com.huaimikeji.fandianla.model;

import android.database.Cursor;

import com.huaimikeji.fandianla.model.provider.BTPrinterProviderConfigs;
import com.huaimikeji.fandianla.model.provider.util.ICursorCreator;

/**
 * Created by Administrator on 2016/4/18.
 */
public class PrinterBean implements ICursorCreator<PrinterBean> {
    /**
     * 前台打印flag
     */
    public final static int FLAG_RECEPTION_PRINTER = 0;
    /**
     * 后厨打印机
     */
    public final static int FLAG_KITCHEN_PRINTER = 1;

    /**
     * 打印机名称
     */
    private String mPrinterName;
    /**
     * 打印机别名
     */
    private String mPrinterAliasName;

    /**
     * 打印机地址
     */
    private String mPrinterAddress;

    /**
     * uuid
     */
    private String mPrinterUuid;
    /**
     * 打印机样式 0：前台票据;1：后厨票据;2：存根票据
     */
    private String mPrinterStyle;
    /**
     * 打印机幅面 0 ：58mm；1:80mm
     */
    private String mPrinterFormat;
    /**
     * 打印数量
     */
    private int mPrinterCount;
    /**
     * 打印机类别：前台打印机 0；后厨打印机 1
     */
    private int mPrinterCategory;

    public String getmPrinterName() {
        return mPrinterName;
    }

    public void setmPrinterName(String mPrinterName) {
        this.mPrinterName = mPrinterName;
    }

    public String getmPrinterAliasName() {
        return mPrinterAliasName;
    }

    public void setmPrinterAliasName(String mPrinterAliasName) {
        this.mPrinterAliasName = mPrinterAliasName;
    }

    public String getmPrinterStyle() {
        return mPrinterStyle;
    }

    public void setmPrinterStyle(String mPrinterStyle) {
        this.mPrinterStyle = mPrinterStyle;
    }

    public String getmPrinterFormat() {
        return mPrinterFormat;
    }

    public void setmPrinterFormat(String mPrinterFormat) {
        this.mPrinterFormat = mPrinterFormat;
    }

    public int getmPrinterCount() {
        return mPrinterCount;
    }

    public void setmPrinterCount(int mPrinterCount) {
        this.mPrinterCount = mPrinterCount;
    }

    public int getmPrinterCategory() {
        return mPrinterCategory;
    }

    public void setmPrinterCategory(int mPrinterCategory) {
        this.mPrinterCategory = mPrinterCategory;
    }

    public String getmPrinterAddress() {
        return mPrinterAddress;
    }

    public void setmPrinterAddress(String mPrinterAddress) {
        this.mPrinterAddress = mPrinterAddress;
    }

    public String getmPrinterUuid() {
        return mPrinterUuid;
    }

    public void setmPrinterUuid(String mPrinterUuid){
        this.mPrinterUuid = mPrinterUuid;
    }

    @Override
    public PrinterBean createFormCursor(Cursor cursor) {
        PrinterBean bean = new PrinterBean();
        bean.setmPrinterName(cursor.getString(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_NAME)));
        bean.setmPrinterAliasName(cursor.getString(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_ALIAS_NAME)));
        bean.setmPrinterAddress(cursor.getString(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_ADDRESS)));
        bean.setmPrinterUuid(cursor.getString(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_UUID)));
        bean.setmPrinterStyle(cursor.getString(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_STYLE)));
        bean.setmPrinterFormat(cursor.getString(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_FORMAT)));
        bean.setmPrinterCount(cursor.getInt(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_COUNT)));
        bean.setmPrinterCategory(cursor.getInt(cursor.getColumnIndex(BTPrinterProviderConfigs.
                BTPrinterColumnsImpl.PRINTER_CATEGORY)));

        return bean;
    }
}
