package com.example.administrator.meituan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.StringFromPath;

public class RegistActivity extends ActionBarActivity {

    private EditText regist_username;
    private EditText regist_password;
    private EditText regist_repeatpassword;
    private ProgressDialog progressDialog;
    private Button regist;
    public static final String PATH_TEST = "http://49.140.122.74:8080/meituanShop/users/testUsername.action";
    public static final String PATH_REGIST = "http://49.140.122.74:8080/meituanShop/users/usersRegist.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        regist_username = (EditText) findViewById(R.id.regist_username);
        regist_password = (EditText) findViewById(R.id.regist_password);
        regist_repeatpassword = (EditText) findViewById(R.id.regist_repeatpassword);
//      regist_telephone = (EditText) findViewById(R.id.regist_telephone);
        regist = (Button) findViewById(R.id.regist);
        progressDialog = new ProgressDialog(RegistActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("正在注册");
        progressDialog.setMessage("请稍等");
        /*//失去焦点，校验用户名
        regist_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    new TestUsernameTask(regist_username).execute(PATH_TEST);
                }
            }
        });*/
        //注册按钮
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交前校验
               if(regist_username.getText().toString().trim().length() < 5){
                    Toast.makeText(RegistActivity.this,"用户名长度应不少于5位",Toast.LENGTH_SHORT).show();
                    return;
                }else if(regist_password.getText().toString().trim().length() < 6){
                    Toast.makeText(RegistActivity.this,"密码长度应不少于6位",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!regist_password.getText().toString().equals(regist_repeatpassword.getText().toString())){
                    Toast.makeText(RegistActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //注册按钮
                    new TestUsernameTask(regist_username).execute(PATH_TEST);
                }
            }
        });
    }
    //测试用户名是否重复
    class TestUsernameTask extends AsyncTask<String,Void,String> {

        private EditText regist_username;
        public TestUsernameTask(EditText regist_username){
            this.regist_username = regist_username;
        }
        @Override
        protected String doInBackground(String... params) {
            String uusername = regist_username.getText().toString().trim();
            params[0] = params[0] + "?uusername="+uusername;
            return new StringFromPath(params[0]).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals("0")){
                Toast.makeText(RegistActivity.this,"用户名重复",Toast.LENGTH_SHORT).show();
            }else{
                new RegistTask(regist_username,regist_password).execute(PATH_REGIST);
            }

        }
    }
    //注册
    class RegistTask extends AsyncTask<String,Void,String>{

        EditText regist_username;
        EditText regist_password;

        RegistTask(EditText regist_username,EditText regist_password ){
                this.regist_username = regist_username;
                this.regist_password = regist_password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String uusername = regist_username.getText().toString();
            String upassword = regist_password.getText().toString();
            try {
                params[0] = params[0]+"?"+"uusername="+uusername+"&"+"upassword="+upassword;
                String sign = new StringFromPath(params[0]).getString();
 /*             Users users = new Users();
                JSONObject jsonObject = new JSONObject(json_users);
                users.setUid((Integer) jsonObject.get("uid"));
                users.setUusername((String) jsonObject.get("uusername"));
                users.setUpassword((String) jsonObject.get("upassword"));
                users.setUname((String) jsonObject.get("uname"));
                users.setUsex((String) jsonObject.get("usex"));
                users.setUemail((String) jsonObject.get("uemail"));
                users.setUtelephone((String) jsonObject.get("utelephone"));
                users.setCredit((Integer) jsonObject.get("credit"));*/
                return sign;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("1") ){
               RegistActivity.this.finish();
            }else{
                Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
}
