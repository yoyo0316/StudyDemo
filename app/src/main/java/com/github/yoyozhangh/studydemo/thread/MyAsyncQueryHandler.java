package com.github.yoyozhangh.studydemo.thread;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.github.yoyozhangh.studydemo.db.Person;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class MyAsyncQueryHandler extends AsyncQueryHandler {

    private final static String TAG = MyAsyncQueryHandler.class.getSimpleName();

    public MyAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    public void asyncQueryPersonDb() {
        startQuery(0, null, Person.CONTENT_URI_QUERY_ALL, new String[]{Person.KEY_ID, Person.KEY_NAME, Person.KEY_AGE}, null, null, null);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Log.e(TAG, "onQueryComplete called with: _id = [" + cursor.getInt(cursor.getColumnIndex(Person.KEY_ID)) + "]," +
                        " name = [" + cursor.getString(cursor.getColumnIndex(Person.KEY_NAME)) + "]," +
                        " age = [" + cursor.getInt(cursor.getColumnIndex(Person.KEY_AGE)) + "]");
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        super.onUpdateComplete(token, cookie, result);
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        super.onDeleteComplete(token, cookie, result);
    }


}
