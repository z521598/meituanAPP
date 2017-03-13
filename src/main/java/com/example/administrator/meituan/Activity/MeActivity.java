package com.example.administrator.meituan.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.administrator.meituan.R;

public class MeActivity extends ActionBarActivity {

    private Button me_quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        me_quit = (Button) findViewById(R.id.me_quit);
        me_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeActivity.this.finish();
            }
        });
    }


}
