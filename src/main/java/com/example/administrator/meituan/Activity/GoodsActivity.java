package com.example.administrator.meituan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.widget.ListView;


import com.example.administrator.meituan.Adapter.GoodsAdapter;
import com.example.administrator.meituan.POJO.Goods;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Tool.StringToObject;

import java.util.List;

public class GoodsActivity extends ActionBarActivity {


    private ListView goodsListView;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView应该在所有新添加的语句的下面
        setContentView(R.layout.activity_goods);
        goodsListView = (ListView) findViewById(R.id.goodsListView);
        //获取意图
        Intent intent = getIntent();
        String jsonGoods = intent.getStringExtra("jsonGoods");
        List<Goods> list = new StringToObject().goodsList(jsonGoods);
        //适配List
        goodsListView.setAdapter(new GoodsAdapter(list,getLayoutInflater(),GoodsActivity.this));
        //实例化近进度条
        progressDialog = new ProgressDialog(GoodsActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("请稍等");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
