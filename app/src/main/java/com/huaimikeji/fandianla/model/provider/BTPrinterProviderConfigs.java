package com.huaimikeji.fandianla.model.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/4/15.
 */
public class BTPrinterProviderConfigs {
    public static final String AUTHORITY = "com.huaimiit.fandianla.model.provider.BTPrinterContentProvider";

    /**
     * uri
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PRINTER = "printer";

    interface BTPrinterColumns extends BaseColumns {
        String PRINTER_NAME = "printer_name";
        String PRINTER_ALIAS_NAME = "printer_alias_name";
        String PRINTER_ADDRESS = "printer_address";
        String PRINTER_UUID = "printer_uuid";
        String PRINTER_STYLE = "printer_style";
        String PRINTER_FORMAT = "printer_format";
        String PRINTER_COUNT = "printer_count";
        String PRINTER_CATEGORY = "printer_category";
    }

    public static class BTPrinterColumnsImpl implements BTPrinterColumns {

        public static final Uri PRINTER_URI = BASE_CONTENT_URI.buildUpon().appendPath(PRINTER).build();
        static final String PRINTER_TYPE = "vnd.android.cursor.dir/vnd.huaimikeji.fandianla.trouble";
        static final String PRINTER_ITEM_TYPE = "vnd.android.cursor.item/vnd.huaimikeji.fandianla.trouble";
        static final int FIRST_PATH = 1;

        static Uri buildPrinterUri(String rowId) {
            return PRINTER_URI.buildUpon().appendPath(rowId).build();
        }

        static String getColumnsName(Uri uri) {
            if (null == uri) {
                return null;
            }
            return uri.getPathSegments().get(FIRST_PATH);
        }
    }

    public static String[] BT_PRINTER = new String[]{
            BTPrinterColumnsImpl._ID,
            BTPrinterColumnsImpl.PRINTER_NAME,
            BTPrinterColumnsImpl.PRINTER_ALIAS_NAME,
            BTPrinterColumnsImpl.PRINTER_ADDRESS,
            BTPrinterColumnsImpl.PRINTER_UUID,
            BTPrinterColumnsImpl.PRINTER_STYLE,
            BTPrinterColumnsImpl.PRINTER_FORMAT,
            BTPrinterColumnsImpl.PRINTER_COUNT,
            BTPrinterColumnsImpl.PRINTER_CATEGORY
    };

}
