package com.huaimikeji.fandianla.model.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class BTPrinterContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int PRINTER = 1;

    public static BTPrinterSQLiteHelper mSQLiteHelper;

    /**
     * 构建Uri
     *
     * @return
     */
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BTPrinterProviderConfigs.AUTHORITY;
        matcher.addURI(authority, BTPrinterProviderConfigs.PRINTER, PRINTER);
        return matcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        final int code = sUriMatcher.match(uri);
        int count;
        switch (code) {
            case PRINTER:
                count = db.delete(BTPrinterSQLiteHelper.BT_PRINTER_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);
        switch (match){
        case PRINTER:
            return BTPrinterProviderConfigs.BTPrinterColumnsImpl.PRINTER_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRINTER:
                long rowId = db.insertWithOnConflict(BTPrinterSQLiteHelper.BT_PRINTER_TABLE, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                if (rowId > 0L) {
                    getContext().getContentResolver().notifyChange(uri, null, false);
                }
                return BTPrinterProviderConfigs.BTPrinterColumnsImpl.buildPrinterUri(String.valueOf(rowId));
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        mSQLiteHelper = new BTPrinterSQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (match) {
            case PRINTER:

                cursor = db.query(BTPrinterSQLiteHelper.BT_PRINTER_TABLE, projection, selection, selectionArgs, null,
                        null, sortOrder);
                if (cursor != null) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                }
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRINTER:
                int ret = db.update(BTPrinterSQLiteHelper.BT_PRINTER_TABLE, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null, false);
                return ret;
        }
        return -1;
    }
}
