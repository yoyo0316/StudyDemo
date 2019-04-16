package com.github.yoyozhangh.studydemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class PersonSQLiteOpenHelper extends SQLiteOpenHelper {

    //
    private static final String DB_NAME = "person.db";
    public static final String TABLE_NAME = "person";

    public PersonSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * 数据库第一次创建时回调此方法. 初始化一些表
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" + Person.KEY_ID + " integer primary key autoincrement, "
                + Person.KEY_NAME + " varchar(100), " + Person.KEY_AGE + " integer);";
        db.execSQL(sql);
    }

    /**
     * 数据库的版本号更新时回调此方法, 更新数据库的内容(删除表, 添加表, 修改表
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
