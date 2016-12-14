package com.huaimikeji.fandianla.model.provider;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.huaimikeji.fandianla.model.PrinterBean;
import com.huaimikeji.fandianla.model.provider.util.ObjectCursor;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15.
 */
public class BTPrinterPrviderHelper {

    /**
     * 插入打印机数据
     * @param context
     * @param bean
     * @return
     */
    public boolean addPrinterDB(Context context,PrinterBean bean){
        try {

            ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

            ContentProviderOperation.Builder builder = insertPrinterBeanOperation(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_URI, bean);
            ContentProviderOperation operation = builder.build();
            operations.add(operation);
            ContentProviderResult[] results = null;
            results = context.getContentResolver().applyBatch(BTPrinterProviderConfigs.AUTHORITY, operations);

            if (results != null && results.length > 0) {

                return true;
            }
        } catch (RemoteException e) {
            Log.e("panzz", Log.getStackTraceString(e));
            return false;
        } catch (OperationApplicationException e) {
            Log.e("panzz", Log.getStackTraceString(e));
            return false;
        } catch (Exception e) {
            Log.e("panzz", Log.getStackTraceString(e));
        }
        return false;
    }

    private ContentProviderOperation.Builder insertPrinterBeanOperation(Uri uri, PrinterBean bean) {

        return ContentProviderOperation.newInsert(uri)
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_NAME, bean.getmPrinterName())
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_ALIAS_NAME, bean.getmPrinterAliasName())
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_ADDRESS, bean.getmPrinterAddress())
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_UUID, bean.getmPrinterUuid())
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_STYLE, bean.getmPrinterStyle())
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_FORMAT, bean.getmPrinterFormat())
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_COUNT, bean.getmPrinterCount())
                .withValue(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_CATEGORY, bean.getmPrinterCategory());

    }


    /**
     * 更新打印机信息
     * @param context
     * @param bean 打印机信息
     * @return
     */
    public boolean updatePrinter(Context context,PrinterBean bean){
        String where = BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_CATEGORY + " = " + bean.getmPrinterCategory();
        Cursor result = null;
        try {
            ContentValues values = new ContentValues();
            values.put(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_NAME,bean.getmPrinterName());
            values.put(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_ALIAS_NAME,bean.getmPrinterAliasName());
            values.put(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_ADDRESS,bean.getmPrinterAddress());
            values.put(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_UUID,bean.getmPrinterUuid());
            values.put(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_STYLE,bean.getmPrinterStyle());
            values.put(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_FORMAT,bean.getmPrinterFormat());
            values.put(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_COUNT,bean.getmPrinterCount());
            int index = context.getContentResolver().update(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_URI,values,where,null);
            if(index>0){
                return true;
            }

        } catch (Exception e) {
            Log.e("panzz", Log.getStackTraceString(e));
            return false;
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception e2) {
            }
        }
    }


    /**
     * 获取打印机
     * @param context
     * @param flag 前台打印机;后厨打印机
     * @return
     */
    public PrinterBean getPrinterBean(Context context,int flag){

//        if (address != null) {
            String where = BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_CATEGORY + " = " + flag;
            Cursor result = null;
            try {
                result = context.getContentResolver().query(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_URI,
                        BTPrinterProviderConfigs.BT_PRINTER, where, null, null);
                if (result != null && result.getCount() > 0) {
                    Log.d("panzz", "modelResult:" + result.getCount());
                    result.moveToFirst();
                    PrinterBean bean = new PrinterBean();
                    ObjectCursor<PrinterBean> oc = new ObjectCursor<PrinterBean>(result, bean);
                    bean = oc.getModel();
                    return bean;
                }
            } catch (Exception e) {
                Log.e("panzz", Log.getStackTraceString(e));
                return null;
            } finally {
                try {
                    if (result != null) {
                        result.close();
                    }
                } catch (Exception e2) {
                }
            }
//        }
        return null;
    }

    /**
     * 删除打印机
     * @param context
     * @param name 打印机名称
     * @param address 打印机地址
     * @return
     */
    public int delPrinter(Context context,String name,String address){
        int delCount = 0;
        try {
            String where = BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_NAME + " == " + name + " AND " + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_ADDRESS + " == "
                    + address;
            delCount = context.getContentResolver().delete(BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_URI, where, null);
            Log.d("panzz", "delCount:" + delCount);
        } catch (Exception e) {
            Log.e("panzz", Log.getStackTraceString(e));
        }
        return delCount;

    }

}
