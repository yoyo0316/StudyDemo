package com.github.yoyozhangh.studydemo.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class PersonContentObserver extends ContentObserver {

    private final static String TAG = PersonContentObserver.class.getSimpleName();
    private Context mContext;

    public PersonContentObserver(Handler handler, Context context) {
        super(handler);
        this.mContext = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.d(TAG, "onChange() called with: selfChange = [" + selfChange + "]");
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(Person.CONTENT_URI_QUERY_ALL, new String[]{Person.KEY_ID, Person.KEY_NAME, Person.KEY_AGE}, null, null, "_id desc");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id;
            String name;
            int age;
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(Person.KEY_ID));
                name = cursor.getString(cursor.getColumnIndex(Person.KEY_NAME));
                age = cursor.getInt(cursor.getColumnIndex(Person.KEY_AGE));
                Log.d(TAG, "onChange called with: id = [" + id + "]" + " name=" + name + " age=" + age);
            }
            cursor.close();
        }
    }
}
