package com.github.yoyozhangh.studydemo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.github.yoyozhangh.studydemo.db.Person;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private final static String TAG = ExampleInstrumentedTest.class.getSimpleName();

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.github.yoyozhangh.studydemo", appContext.getPackageName());
    }

    @Test
    public void testInsert() {
        // 内容提供者访问对象
        ContentResolver resolver = getContext().getContentResolver();

        for (int i = 0; i < 10; i++) {
            //
            ContentValues values = new ContentValues();
            values.put(Person.KEY_NAME, "wanglei" + i);
            values.put(Person.KEY_AGE, i);
            Uri uri = resolver.insert(Person.CONTENT_URI_INSERT, values);
            Log.i(TAG, "uri: " + uri);
            long id = ContentUris.parseId(uri);
            Log.i(TAG, "添加到: " + id);
        }
    }

    @Test
    public void testDelete() {

        // 内容提供者访问对象
        ContentResolver resolver = getContext().getContentResolver();
        String where = Person.KEY_ID + " = ?";
        String[] selectionArgs = {"3"};
        int count = resolver.delete(Person.CONTENT_URI_DELETE, where,
                selectionArgs);
        Log.i(TAG, "删除行: " + count);
    }

    @Test
    public void testUpdate() {

        // 内容提供者访问对象
        ContentResolver resolver = getContext().getContentResolver();

        ContentValues values = new ContentValues();
        values.put(Person.KEY_NAME, "lisi");

        int count = resolver.update(Person.CONTENT_URI_UPDATE, values,
                Person.KEY_ID + " = ?", new String[]{"1"});
        Log.i(TAG, "更新行: " + count);
    }

    @Test
    public void testQueryAll() {

        // 内容提供者访问对象
        ContentResolver resolver = getContext().getContentResolver();

        Cursor cursor = resolver
                .query(Person.CONTENT_URI_QUERY_ALL, new String[]{
                                Person.KEY_ID, Person.KEY_NAME, Person.KEY_AGE}, null,
                        null, "_id desc");

        if (cursor != null && cursor.getCount() > 0) {

            int id;
            String name;
            int age;
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(Person.KEY_ID));
                name = cursor.getString(cursor.getColumnIndex(Person.KEY_NAME));
                age = cursor.getInt(cursor.getColumnIndex(Person.KEY_AGE));
                Log.i(TAG, "id: " + id + ", name: " + name + ", age: " + age);
            }
            cursor.close();
        }
    }

    @Test
    public void testQuerySingleItem() {

        // 在uri的末尾添加一个id
        Uri uri = ContentUris.withAppendedId(Person.CONTENT_URI_QUERY_ITEM, 1);

        // 内容提供者访问对象
        ContentResolver resolver = getContext().getContentResolver();

        Cursor cursor = resolver.query(uri, new String[]{Person.KEY_ID,
                Person.KEY_NAME, Person.KEY_AGE}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            cursor.close();
            Log.i(TAG, "id: " + id + ", name: " + name + ", age: " + age);
        }
    }
}
