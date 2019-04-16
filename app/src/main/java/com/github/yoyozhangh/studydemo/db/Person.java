package com.github.yoyozhangh.studydemo.db;

import android.net.Uri;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class Person {
    public static final String AUTHORITY = "com.github.yoyozhangh.studydemo";

    //
    public static final String PATH_INSERT = "person/insert";
    public static final String PATH_DELETE = "person/delete";
    public static final String PATH_UPDATE = "person/update";
    public static final String PATH_QUERY_ALL = "person/queryAll";
    public static final String PATH_QUERY_ITEM = "person/query/#";
    //
    public static final Uri CONTENT_URI_INSERT = Uri.parse("content://" + AUTHORITY + "/" + PATH_INSERT);
    public static final Uri CONTENT_URI_DELETE = Uri.parse("content://" + AUTHORITY + "/" + PATH_DELETE);
    public static final Uri CONTENT_URI_UPDATE = Uri.parse("content://" + AUTHORITY + "/" + PATH_UPDATE);
    public static final Uri CONTENT_URI_QUERY_ALL = Uri.parse("content://" + AUTHORITY + "/" + PATH_QUERY_ALL);
    public static final Uri CONTENT_URI_QUERY_ITEM = Uri.parse("content://" + AUTHORITY + "/" + PATH_QUERY_ITEM);

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AGE = "age";

}
