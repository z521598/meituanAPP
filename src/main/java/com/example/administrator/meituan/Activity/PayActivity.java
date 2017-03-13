package com.example.administrator.meituan.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.OrdersUpdateTask;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;

public class PayActivity extends ActionBarActivity {


    private TextView pay_gname;
    private TextView pay_gprice;
    private EditText passwordPayText;
    private Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        pay_gname = (TextView) findViewById(R.id.pay_gname);
        pay_gprice = (TextView) findViewById(R.id.pay_gprice);
        passwordPayText= (EditText) findViewById(R.id.passwordPayText);
        pay = (Button) findViewById(R.id.pay);
        //获取bundle,设置组件
        final Bundle bundle = getIntent().getExtras();
        String gname = bundle.getString("gname");
        String gprice = bundle.getString("gprice");
        pay_gname.setText(gname);
        pay_gprice.setText(gprice);
        //pay按钮事件
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = new UsersFromSqlite(PayActivity.this).getUsers().getUpassword();
                //验证密码
                if(passwordPayText.getText().toString().equals(password)){
                    new OrdersUpdateTask("1",""+bundle.getInt("oid"),PayActivity.this).execute();
                }else{
                    Toast.makeText(PayActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

}
