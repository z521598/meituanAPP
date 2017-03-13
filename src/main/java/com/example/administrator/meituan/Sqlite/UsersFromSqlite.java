package com.example.administrator.meituan.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.meituan.POJO.Users;

/**
 * Created by Administrator on 2016/8/27.
 */
public class UsersFromSqlite {

    private Context context;

    public UsersFromSqlite(Context context){
        this.context = context;
    }

    public Users getUsers(){
        Users users = new Users();
        UsersSqliteOpenHelper usersSqliteOpenHelper = new UsersSqliteOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = usersSqliteOpenHelper.getReadableDatabase();
        //查询的值返回为游标
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users", null);
        if (cursor.getCount() == 0){
            return null;
        }
        while (cursor.moveToNext()){
            users.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
            users.setUname(cursor.getString(cursor.getColumnIndex("uname")));
            users.setUusername(cursor.getString(cursor.getColumnIndex("uusername")));
            users.setUpassword(cursor.getString(cursor.getColumnIndex("upassword")));
            users.setUhead(cursor.getString(cursor.getColumnIndex("uhead")));
            users.setUsex(cursor.getString(cursor.getColumnIndex("usex")));
            users.setUemail(cursor.getString(cursor.getColumnIndex("uemail")));
            users.setUtelephone(cursor.getString(cursor.getColumnIndex("utelephone")));
            users.setUaddress(cursor.getString(cursor.getColumnIndex("uaddress")));
        }
        return users;
    }
}
