package com.github.yoyozhangh.studydemo.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class ChannelContentProvider extends ContentProvider {


    private static final int PERSON_INSERT_CODE = 0;
    private static final int PERSON_DELETE_CODE = 1;
    private static final int PERSON_UPDATE_CODE = 2;
    private static final int PERSON_QUERY_ALL_CODE = 3;
    private static final int PERSON_QUERY_ITEM_CODE = 4;

    private static UriMatcher uriMatcher;
    private PersonSQLiteOpenHelper mOpenHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Person.AUTHORITY, Person.PATH_INSERT, PERSON_INSERT_CODE);
        uriMatcher.addURI(Person.AUTHORITY, Person.PATH_DELETE, PERSON_DELETE_CODE);
        uriMatcher.addURI(Person.AUTHORITY, Person.PATH_UPDATE, PERSON_UPDATE_CODE);
        uriMatcher.addURI(Person.AUTHORITY, Person.PATH_QUERY_ALL, PERSON_QUERY_ALL_CODE);
        uriMatcher.addURI(Person.AUTHORITY, Person.PATH_QUERY_ITEM, PERSON_QUERY_ITEM_CODE);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PersonSQLiteOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case PERSON_QUERY_ALL_CODE:
                return "vnd.android.cursor.dir/person";
            case PERSON_QUERY_ITEM_CODE:
                return "vnd.android.cursor.item/person";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case PERSON_INSERT_CODE:
                SQLiteDatabase database = mOpenHelper.getWritableDatabase();
                if (database.isOpen()) {
                    long id = database.insert(PersonSQLiteOpenHelper.TABLE_NAME, null, values);
                    database.close();
                    Uri newUri = ContentUris.withAppendedId(uri, id);
                    //通知内容观察者数据发生变化
                    getContext().getContentResolver().notifyChange(newUri, null);
                    return newUri;
                }
                break;
            default:
                throw new IllegalArgumentException("uri 不匹配:" + uri);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case PERSON_DELETE_CODE:
                SQLiteDatabase database = mOpenHelper.getWritableDatabase();
                if (database.isOpen()) {
                    int count = database.delete(PersonSQLiteOpenHelper.TABLE_NAME, selection, selectionArgs);
                    database.close();
                    //通知内容观察者数据发生变化
                    getContext().getContentResolver().notifyChange(uri, null);
                    return count;
                }
                break;
            default:
                throw new IllegalArgumentException("uri 不匹配:" + uri);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case PERSON_UPDATE_CODE:
                SQLiteDatabase database = mOpenHelper.getWritableDatabase();
                if (database.isOpen()) {
                    int count = database.update(PersonSQLiteOpenHelper.TABLE_NAME, values, selection, selectionArgs);
                    database.close();
                    //通知内容观察者数据发生变化
                    getContext().getContentResolver().notifyChange(uri, null);
                    return count;
                }
                break;
            default:
                throw new IllegalArgumentException("uri 不匹配:" + uri);
        }
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mOpenHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case PERSON_QUERY_ALL_CODE:
                if (database.isOpen()) {
                    Cursor cursor = database.query(PersonSQLiteOpenHelper.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder);
                    //通知内容观察者数据发生变化
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }
                break;
            case PERSON_QUERY_ITEM_CODE:
                if (database.isOpen()) {
                    long id = ContentUris.parseId(uri);
                    Cursor cursor = database.query(PersonSQLiteOpenHelper.TABLE_NAME, projection,
                            Person.KEY_ID + " = ?", new String[]{id + ""}, null, null, sortOrder);
                    //通知内容观察者数据发生变化
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }
                break;
            default:
                throw new IllegalArgumentException("uri 不匹配:" + uri);
        }
        return null;
    }
}
