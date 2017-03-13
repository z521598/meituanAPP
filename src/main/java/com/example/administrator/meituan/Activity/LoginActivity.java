package com.example.administrator.meituan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.LoginTask;
import com.example.administrator.meituan.Task.StringFromPath;
import com.example.administrator.meituan.Sqlite.UsersSqliteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends ActionBarActivity {

    private Button login;
    private EditText login_username;
    private EditText login_password;
    private TextView findPwd;
    private Switch showPwd;
    private TextView login_to_regist;
    //注意事项：1）需要加上htto://    2）localhost需要替换为IP地址    联网状态下，cmd输入命令ipconfig进行查询
    //url的验证可以通过浏览器的地址栏来验证
    private static final String PATH = "http://49.140.122.74:8080/meituanShop/users/usersLogin.action";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        login_to_regist = (TextView) findViewById(R.id.login_to_regist);
        findPwd = (TextView) findViewById(R.id.findPwd);

        showPwd = (Switch) findViewById(R.id.showPwd);
        //实例化进度条
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("请稍等");

        //登录按钮
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先toString()，再去空格
                String username = login_username.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                new LoginTask(username,password,LoginActivity.this).execute(PATH);
            }
        });

        //注册按钮-跳转到注册页面
        login_to_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
        //跳转到新建消息页面
        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MessageActivity.class);
                intent.putExtra("msg", "留下你的用户名、联系方式、");
                startActivity(intent);
            }
        });


        showPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    showPwd.setText("明文");
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    showPwd.setText("密文");
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });



    }


}
