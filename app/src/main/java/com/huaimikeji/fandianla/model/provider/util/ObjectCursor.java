/*
 * ObjectCursor.java
 * classes : com.baidu.netdisk.provider.ObjectCursor
 * @author <a href="mailto:libin09@baidu.com">李彬</a>
 * V 1.0.0
 * Create at 2013-6-14 下午4:42:55
 */
package com.huaimikeji.fandianla.model.provider.util;

import java.util.Arrays;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * com.baidu.netdisk.provider.ObjectCursor
 * 
 * @author <a href="mailto:answersun@tencent.com">孙奇</a> <br/>
 *         create at 2013-6-14 下午4:42:55
 */
public class ObjectCursor<T> extends CursorWrapper {

    private ICursorCreator<T> mFactory;

    /**
     * @param cursor
     * @param factory
     */
    public ObjectCursor(Cursor cursor, ICursorCreator<T> factory) {
        super(cursor);
        mFactory = factory;
    }

    public T getModel() {
        return mFactory.createFormCursor(this);
    }

    /**
     * @return
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "ObjectCursor [mFactory=" + mFactory + ", getModel()=" + getModel() + ", isClosed()=" + isClosed()
                + ", getCount()=" + getCount() + ", moveToFirst()=" + moveToFirst() + ", getColumnCount()="
                + getColumnCount() + ", getColumnNames()=" + Arrays.toString(getColumnNames()) + ", getExtras()="
                + getExtras() + ", getWantsAllOnMoveCalls()=" + getWantsAllOnMoveCalls() + ", isAfterLast()="
                + isAfterLast() + ", isBeforeFirst()=" + isBeforeFirst() + ", isFirst()=" + isFirst() + ", isLast()="
                + isLast() + ", moveToLast()=" + moveToLast() + ", moveToNext()=" + moveToNext() + ", getPosition()="
                + getPosition() + ", moveToPrevious()=" + moveToPrevious() + ", requery()=" + requery()
                + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
                + "]";
    }

}
