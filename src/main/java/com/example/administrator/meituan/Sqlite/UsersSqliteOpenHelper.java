package com.example.administrator.meituan.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/27.
 */
//SQLiteOpenHelper 基类 创建数据库
public class UsersSqliteOpenHelper extends SQLiteOpenHelper{

    //数据库名字
    private static final String USER_DB ="user.db";
    //数据库版本,升级的时候，数据库版本要大于现有的值
    private static final int VERSION = 1;

    //构造方法,先引用父类的构造方法，再修改
    public UsersSqliteOpenHelper(Context context) {
        super(context, USER_DB, null, VERSION);
    }

    //第一次创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        boolean bol = false;
        //创建表，如果是主键，一定要用integer
        //尽量简洁
        db.execSQL("CREATE TABLE `users` (" +
                "  `uid` int(11)," +
                "  `uusername` varchar(21)," +
                "  `upassword` varchar(21)," +
                "  `uname` varchar(21)," +
                "  `uhead` varchar(50)," +
                "  `usex` varchar(10)," +
                "  `uemail` varchar(21)," +
                "  `uaddress` varchar(21)," +
                "  `utelephone` varchar(21)," +
                "  `credit` int(11)," +
                "  `ulastlogtime` datetime," +
                "  `ulastlogaddress` varchar(20)," +
                "  `uregisttime` datetime," +
                "  `usign` int(11)," +
                "  `uisseal` int(11)," +
                "  `usealtime` datetime" +
                ")");
        // ENGINE=InnoDB DEFAULT CHARSET=utf8;
        bol = true;
    }

    //升级更新数据库的方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
