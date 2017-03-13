package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.POJO.Message;
import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends ActionBarActivity {

    private static final String PATH = "http://49.140.122.74:8080/meituanShop/message/addMessage.action";
    private TextView msg_title;
    private EditText msg_content;
    private EditText msg_mtitle;
    private Button msg_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        msg_content = (EditText) findViewById(R.id.msg_content);
        msg_mtitle = (EditText) findViewById(R.id.msg_mtitle);
        msg_send = (Button) findViewById(R.id.msg_send);
        msg_title = (TextView) findViewById(R.id.msg_title);
        String title = getIntent().getStringExtra("msg");
        msg_title.setText(title);

        msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg_content.getText().toString().equals("")){
                    Toast.makeText(MessageActivity.this,"消息不能为空",Toast.LENGTH_SHORT).show();
                }else if(msg_mtitle.getText().toString().equals("")){
                    Toast.makeText(MessageActivity.this,"标题不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    new MsgTask(msg_content.getText().toString(),msg_mtitle.getText().toString()).execute(PATH);
                }



            }
        });
    }

    //post数据
    class MsgTask extends AsyncTask<String,Void,String>{

        String mcontent;
        String mtitle;
        MsgTask(String mcontent,String mtitle){
            this.mcontent = mcontent;
            this.mtitle = mtitle;
        }

        @Override
        protected String doInBackground(String... params) {
            String uusername = "游客";
            //获取Users-uusername
            Users users = new UsersFromSqlite(MessageActivity.this).getUsers();
            if(users != null){
                uusername = users.getUusername();
            }
            HttpPost httpRequest;
            List<NameValuePair> para;
            HttpResponse httpResponse;
            //建立HttpPost链接
            httpRequest=new HttpPost(params[0]);
            //Post操作参数必须使用NameValuePair[]阵列储存
            para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("mcontent",mcontent));
            para.add(new BasicNameValuePair("uusername",uusername));
            para.add(new BasicNameValuePair("mtitle",mtitle));
            //发送Http Request
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(para, HTTP.UTF_8));
                //取得Http Response
                httpResponse=new DefaultHttpClient().execute(httpRequest);
                //若状态码为200
                if(httpResponse.getStatusLine().getStatusCode()==200){
                    String strResult= EntityUtils.toString(httpResponse.getEntity());
                    return strResult;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("1")) {
                Toast.makeText(MessageActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                MessageActivity.this.finish();
            }else {
                Toast.makeText(MessageActivity.this,"未知原因，发送失败,请重试",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
