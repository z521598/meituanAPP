package com.example.administrator.meituan.Task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.meituan.Activity.MainActivity;
import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.Sqlite.UsersSqliteOpenHelper;
import com.example.administrator.meituan.Task.StringFromPath;

import org.json.JSONException;
import org.json.JSONObject;

//异步任务，提交表单
//三个泛型，Params，Progress和Result
//Params 启动任务执行的输入参数，比如HTTP请求的URL。
//Progress 后台任务执行的百分比。
//Result 后台执行任务最终返回的结果，比如String。
public class LoginTask extends AsyncTask<String,Void,Users> {
    private String login_username;
    private String login_password;
    private Context context;

    //通过构造方法传值
    public LoginTask(String login_username,String login_password,Context context){
        this.login_username = login_username;
        this.login_password = login_password;
        this.context = context;
    }
    //当任务执行之前开始调用此方法,可以用来显示对话框
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    //后台执行方法
    @Override
    protected Users doInBackground(String... params) {
        //通过GET方法，需要用?传值，拼接字符串
        params[0] = params[0]+"?uusername="+login_username+"&upassword="+login_password;

        try {
                 /*封装的方法：从网络读取字符串类型数据的方法*/
            String str_users = new StringFromPath(params[0]).getString();
            //用来返回的Users
            Users users = new Users();
            JSONObject jsonObject = new JSONObject(str_users);
            users.setUid(jsonObject.getInt("uid"));
            users.setUusername(jsonObject.getString("uusername"));
            users.setUpassword(jsonObject.getString("upassword"));
            users.setUname(jsonObject.getString("uname"));
            users.setUsex(jsonObject.getString("usex"));
            users.setUaddress(jsonObject.getString("uaddress"));
            users.setUemail(jsonObject.getString("uemail"));
            users.setUtelephone(jsonObject.getString("utelephone"));
            //users.setCredit(jsonObject.getInt("credit"));
            //users.setUlastlogtime((Date) jsonObject.get("ulastlogtime"));
            users.setUsign(jsonObject.getInt("usign"));
            users.setUhead(jsonObject.getString("uhead"));
            users.setUisseal(jsonObject.getInt("uisseal"));
            return users;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //任务执行结束之后调用此方法


    @Override
    protected void onPostExecute(Users users) {
        super.onPostExecute(users);

        if(users != null){
            if(users.getUisseal() == 1){
                Toast.makeText(context,"用户被封禁,登录失败",Toast.LENGTH_SHORT).show();
            }else{
            //将登录信息存入数据库中
            UsersSqliteOpenHelper usersSqliteOpenHelper = new UsersSqliteOpenHelper(context);
            //激活数据库
            SQLiteDatabase sqLiteDatabase = usersSqliteOpenHelper.getReadableDatabase();
            sqLiteDatabase.execSQL("delete from `users`");
            String sql= "INSERT INTO `users` (`uid`,`uusername`,`upassword`,`uname`,`uhead`,`usex`,`uemail`,`uaddress`,`utelephone`,`credit`,`ulastlogtime`,`ulastlogaddress`,`uregisttime`,`usign`,`uisseal`,`usealtime`) VALUES"+
                    "('"+users.getUid()+"', '"+users.getUusername()+"', '"+users.getUpassword()+"', '"+users.getUname()+"', '"+users.getUhead()+"', '"+users.getUsex()+"', '"+users.getUemail()+"', '"+users.getUaddress()+"',  '"+users.getUtelephone()+"', '"+users.getCredit()+"', '"+users.getUlastlogtime()+"', '"+users.getUlastlogaddress()+"', '"+users.getUregisttime()+"', '"+users.getUsign()+"', '"+users.getUisseal()+"', '"+users.getUsealtime()+"')";
            sqLiteDatabase.execSQL(sql);
            Intent returnMain = new Intent(context,MainActivity.class);
            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
            context.startActivity(returnMain);
                ((Activity)context).finish();
            }
        }else{
            Toast.makeText(context,"用户名或者密码错误",Toast.LENGTH_SHORT).show();
        }
    }
}