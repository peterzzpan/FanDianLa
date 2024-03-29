/*
 * ObjectCursorLoader.java
 * classes : com.baidu.netdisk.provider.ObjectCursorLoader
 * @author <a href="mailto:libin09@baidu.com">李彬</a>
 * V 1.0.0
 * Create at 2013-6-14 下午5:14:56
 */
package com.huaimikeji.fandianla.model.provider.util;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

/**
 * com.baidu.netdisk.provider.ObjectCursorLoader
 * 
 * @author <a href="mailto:answersun@tencent.com">孙奇</a> <br/>
 *         create at 2013-6-14 下午5:14:56
 * @param <T>
 */
public class ObjectCursorLoader<T> extends AsyncTaskLoader<ObjectCursor<T>> {

    final ForceLoadContentObserver mObserver;

    Uri mUri;
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;

    ObjectCursor<T> mCursor;

    private final ICursorCreator<T> mFactory;

    /**
     * Creates an empty unspecified CursorLoader. You must follow this with
     * calls to {@link #setUri(Uri)}, {@link #setSelection(String)}, etc to
     * specify the query to perform.
     */
    public ObjectCursorLoader(Context context, ICursorCreator<T> factory) {
        super(context);
        mObserver = new ForceLoadContentObserver();
        mFactory = factory;
    }

    /**
     * Creates a fully-specified CursorLoader. See
     * {@link android.content.ContentResolver#query(Uri, String[], String, String[], String)
     * ContentResolver.query()} for documentation on the meaning of the
     * parameters. These will be passed as-is to that call.
     */
    public ObjectCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder, ICursorCreator<T> factory) {
        super(context);
        mObserver = new ForceLoadContentObserver();
        mUri = uri;
        mProjection = projection;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mSortOrder = sortOrder;
        mFactory = factory;
    }

    /* Runs on a worker thread */
    @Override
    public ObjectCursor<T> loadInBackground() {
        Cursor cursor = getContext().getContentResolver().query(mUri, mProjection, mSelection, mSelectionArgs,
                mSortOrder);
        if (cursor == null) {
            return null;
        }
        // Ensure the cursor window is filled
        cursor.getCount();
        registerContentObserver(cursor, mObserver);
        return new ObjectCursor<T>(cursor, mFactory);
    }

    /**
     * Registers an observer to get notifications from the content provider when
     * the cursor needs to be refreshed.
     */
    void registerContentObserver(Cursor cursor, ContentObserver observer) {
        cursor.registerContentObserver(mObserver);
    }

    /* Runs on the UI thread */
    @Override
    public void deliverResult(ObjectCursor<T> cursor) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    /**
     * Starts an asynchronous load of the contacts list data. When the result is
     * ready the callbacks will be called on the UI thread. If a previous load
     * has been completed and is still valid the result may be passed to the
     * callbacks immediately.
     * 
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(ObjectCursor<T> cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public String[] getProjection() {
        return mProjection;
    }

    public void setProjection(String[] projection) {
        mProjection = projection;
    }

    public String getSelection() {
        return mSelection;
    }

    public void setSelection(String selection) {
        mSelection = selection;
    }

    public String[] getSelectionArgs() {
        return mSelectionArgs;
    }

    public void setSelectionArgs(String[] selectionArgs) {
        mSelectionArgs = selectionArgs;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String sortOrder) {
        mSortOrder = sortOrder;
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mUri=");
        writer.println(mUri);
        writer.print(prefix);
        writer.print("mProjection=");
        writer.println(Arrays.toString(mProjection));
        writer.print(prefix);
        writer.print("mSelection=");
        writer.println(mSelection);
        writer.print(prefix);
        writer.print("mSelectionArgs=");
        writer.println(Arrays.toString(mSelectionArgs));
        writer.print(prefix);
        writer.print("mSortOrder=");
        writer.println(mSortOrder);
        writer.print(prefix);
        writer.print("mCursor=");
        writer.println(mCursor);
        writer.print(prefix);
        writer.print("mContentChanged=");
    }
}
