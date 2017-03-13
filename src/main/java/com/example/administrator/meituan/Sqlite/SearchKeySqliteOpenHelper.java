package com.example.administrator.meituan.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/3.
 */
public class SearchKeySqliteOpenHelper extends SQLiteOpenHelper{
    //数据库名字
    private static final String KEY_DB ="skey.db";
    //数据库版本,升级的时候，数据库版本要大于现有的值
    private static final int VERSION = 1;

    public SearchKeySqliteOpenHelper(Context context) {
        super(context, KEY_DB,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        boolean bol = false;
        db.execSQL("CREATE TABLE skey (kid INTEGER PRIMARY KEY autoincrement , kname VARCHAR(10),uid VARCHAR(14))");
        bol = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
