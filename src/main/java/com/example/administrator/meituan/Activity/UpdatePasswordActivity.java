package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Sqlite.UsersSqliteOpenHelper;
import com.example.administrator.meituan.Task.StringFromPath;

public class UpdatePasswordActivity extends ActionBarActivity {

    private final static String PATH = "http://49.140.122.74:8080/meituanShop//users/updateUsers.action";
    private EditText update_password;
    private EditText update_repeatpassword;
    private EditText update_oldpassword;
    private Button users_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        update_password = (EditText) findViewById(R.id.update_password);
        update_repeatpassword = (EditText) findViewById(R.id.update_repeatpassword);
        update_oldpassword = (EditText) findViewById(R.id.update_oldpassword);
        users_update = (Button)findViewById(R.id.users_update);

        //修改按钮
        users_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = new UsersFromSqlite(UpdatePasswordActivity.this).getUsers();
                String oldPassword = update_oldpassword.getText().toString();
                String pwd = users.getUpassword();
                int uid = users.getUid();
                if(!update_password.getText().toString().trim().equals(update_repeatpassword.getText().toString().trim())){
                    Toast.makeText(UpdatePasswordActivity.this,"二次输入密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!pwd.equals(oldPassword)){
                    Toast.makeText(UpdatePasswordActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    new UpdatePwd(uid,update_password.getText().toString()).execute(PATH);
                    //将登录信息存入数据库中
                    UsersSqliteOpenHelper usersSqliteOpenHelper = new UsersSqliteOpenHelper(UpdatePasswordActivity.this);
                    //激活数据库
                    SQLiteDatabase sqLiteDatabase = usersSqliteOpenHelper.getReadableDatabase();
                    sqLiteDatabase.execSQL("delete from `users`");
                    UpdatePasswordActivity.this.finish();
                }
            }
        });
    }


    class UpdatePwd extends AsyncTask<String,Void,String>{

        int uid;
        String upassword;
        UpdatePwd( int uid,String upassword){
            this.uid = uid;
            this.upassword = upassword;
        }
        @Override
        protected String doInBackground(String... params) {
            params[0] = params[0]+"?uid="+uid+"&upassword="+upassword;
            return new StringFromPath(params[0]).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("1")){
                Toast.makeText(UpdatePasswordActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                UpdatePasswordActivity.this.finish();
            }
        }
    }

}
