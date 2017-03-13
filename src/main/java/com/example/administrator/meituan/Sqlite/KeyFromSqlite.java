package com.example.administrator.meituan.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
public class KeyFromSqlite {

    private Context context;

    public KeyFromSqlite(Context context){
        this.context = context;
    }

    public List<String> getKeys(){
        List<String> list = new ArrayList<String>();

        SearchKeySqliteOpenHelper searchKeySqliteOpenHelper = new SearchKeySqliteOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = searchKeySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT kname from skey order by kid desc",null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("kname")));
        }
        return list;
    }

    public List<String> getKeysLimit(int number){
        List<String> list = new ArrayList<String>();

        SearchKeySqliteOpenHelper searchKeySqliteOpenHelper = new SearchKeySqliteOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = searchKeySqliteOpenHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT kname from skey order by kid desc limit 0,"+number,null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("kname")));
        }
        return list;
    }

}
