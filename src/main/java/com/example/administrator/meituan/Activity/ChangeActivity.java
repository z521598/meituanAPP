package com.example.administrator.meituan.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.R;

public class ChangeActivity extends ActionBarActivity {

    private TextView change_message;
    private Button change_quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        change_message = (TextView) findViewById(R.id.change_message);
        change_quit = (Button) findViewById(R.id.change_quit);
        String sign = getIntent().getStringExtra("sign");
        change_message.setText("您已成为商家");
        change_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity.this.finish();
            }
        });

    }




}
