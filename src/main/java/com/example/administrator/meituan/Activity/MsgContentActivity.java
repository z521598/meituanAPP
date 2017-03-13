package com.example.administrator.meituan.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.R;

public class MsgContentActivity extends ActionBarActivity {
    private TextView mtitle;
    private TextView mcontent;
    private Button mclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_content);
        //获取控件
        mtitle = (TextView) findViewById(R.id.mtitle);
        mcontent = (TextView) findViewById(R.id.mcontent);
        mclose = (Button) findViewById(R.id.mclose);
        Bundle bundle = getIntent().getExtras();
        mtitle.setText(bundle.getString("mtitle"));
        mcontent.setText(bundle.getString("mcontent"));
        int msign = bundle.getInt("msign");

        mclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgContentActivity.this.finish();
            }
        });
    }


}
