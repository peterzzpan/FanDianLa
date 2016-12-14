package com.huaimikeji.fandianla.model.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/4/15.
 */
public class BTPrinterSQLiteHelper extends SQLiteOpenHelper {

    /**
     * 数据库名
     */
    public static final String DATABASE_NAME = "bt_printer.db";

    /**
     * 数据库版本号
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * 打印机表
     */
    public static final String BT_PRINTER_TABLE = "bt_printer_table";

    public BTPrinterSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTroubleTable(db);
    }

    /**
     * @param db
     * @param oldVersion
     *            旧版本号
     * @param newVersion
     *            新版本号
     * @see SQLiteOpenHelper#onUpgrade(SQLiteDatabase,
     *      int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        upgradeVersion(db, oldVersion, newVersion);
    }

    /**
     * 对各个版本的数据库采取不同的升级策略
     *
     * @param db
     *            数据库对象
     */
    private void upgradeVersion(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int index = oldVersion + 1; index <= newVersion; index++) {
            updateVersion(db, index);
        }
    }

    /**
     * 升级到某个版本。跨版本升级时，会逐级升级到每个版本，直到最高版本。<br />
     * 比如版本1升级到版本4:<br />
     * 1>2>3>4
     *
     * @param db
     *            数据库对象
     * @param version
     *            数据库将要升级到的版本号
     */
    private void updateVersion(SQLiteDatabase db, int version) {
        switch (version) {
            case 2:
                break;
        }
    }

    private void createTroubleTable(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + BT_PRINTER_TABLE + " ("

                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl._ID + " INTEGER PRIMARY KEY,"
                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_NAME + " TEXT,"
                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_ALIAS_NAME + " TEXT,"
                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_ADDRESS + " TEXT,"

                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_UUID + " TEXT,"
                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_STYLE + " INTEGER,"
                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_FORMAT + " TEXT,"
                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_COUNT + " INTEGER,"
                    + BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_CATEGORY + " INTEGER"

                    + ");");
        } catch (Exception e) {
            Log.d("panzz", Log.getStackTraceString(e));
        }
    }
}
