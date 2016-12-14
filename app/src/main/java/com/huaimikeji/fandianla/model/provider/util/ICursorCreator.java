/*
 * ICursorCreator.java
 * classes : com.baidu.netdisk.provider.CursorCreator
 * @author <a href="mailto:libin09@baidu.com">李彬</a>
 * V 1.0.0
 * Create at 2013-6-14 下午4:45:28
 */
package com.huaimikeji.fandianla.model.provider.util;

import android.database.Cursor;

/**
 * com.baidu.netdisk.provider.ICursorCreator
 * 
 * @author <a href="mailto:answersun@tencent.com">孙奇</a> <br/>
 *         create at 2013-6-14 下午4:45:28
 */
public interface ICursorCreator<T> {

    /**
     * 从cursor里创建出实体bean，方法实现不可改变cursor的位置，只允许读取cursor
     * 
     * @param cursor
     * @return
     */
    T createFormCursor(Cursor cursor);
}
