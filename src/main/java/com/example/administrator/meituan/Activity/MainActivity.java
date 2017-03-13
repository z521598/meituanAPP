package com.example.administrator.meituan.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.meituan.Fragment.DealFragment;
import com.example.administrator.meituan.Fragment.MoreFragment;
import com.example.administrator.meituan.Fragment.PoiFragment;
import com.example.administrator.meituan.Fragment.UserFragment;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Service.ServiceForOrder;
import com.example.administrator.meituan.Sqlite.UsersSqliteOpenHelper;


public class MainActivity extends ActionBarActivity {
    private ImageView deal;
    private ImageView poi;
    private ImageView user;
    private ImageView more;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启Service监听订单变化情况
        Intent intent = new Intent(MainActivity.this, ServiceForOrder.class);
        startService(intent);
        //初始化Users表
        UsersSqliteOpenHelper usersSqliteOpenHelper = new UsersSqliteOpenHelper(MainActivity.this);
        usersSqliteOpenHelper.getReadableDatabase();
        deal = (ImageView) findViewById(R.id.deal);
        poi = (ImageView) findViewById(R.id.poi);
        user = (ImageView) findViewById(R.id.user);
        more = (ImageView) findViewById(R.id.more);
        //获取fragment管理器
        fragmentManager = getFragmentManager();
        //入栈监听
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){
            @Override
            public void onBackStackChanged() {
                //todo 暂未实现回退效果
            }
        });
        //打开App-显示首页
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DealFragment home = new DealFragment();
        fragmentTransaction.add(R.id.main,home);
        fragmentTransaction.commit();

    }
    //xml注册鼠标单击事件
    public void menuClick(View view){
        //开始一个Fragment事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = view.getId();
        switch (id){
            case R.id.deal :
            case R.id.dealt :
                //实例化fragment
                DealFragment dealFragment = new DealFragment();
                //替换fragment
                fragmentTransaction.replace(R.id.main,dealFragment);
                //入栈
                fragmentTransaction.addToBackStack(null);
                //设置底部导航变色
                deal.setImageResource(R.drawable.ic_menu_deal_on);
                poi.setImageResource(R.drawable.ic_menu_poi_off);
                user.setImageResource(R.drawable.ic_menu_user_off);
                more.setImageResource(R.drawable.ic_menu_more_off);
                break;
            case R.id.poi :
            case R.id.poit :
                PoiFragment poiFragment = new PoiFragment();
                fragmentTransaction.replace(R.id.main,poiFragment);
                fragmentTransaction.addToBackStack(null);
                deal.setImageResource(R.drawable.ic_menu_deal_off);
                poi.setImageResource(R.drawable.ic_menu_poi_on);
                user.setImageResource(R.drawable.ic_menu_user_off);
                more.setImageResource(R.drawable.ic_menu_more_off);
                break;
            case R.id.user :
            case R.id.usert :
                UserFragment userFragment = new UserFragment();
                fragmentTransaction.replace(R.id.main,userFragment);
                fragmentTransaction.addToBackStack(null);
                deal.setImageResource(R.drawable.ic_menu_deal_off);
                poi.setImageResource(R.drawable.ic_menu_poi_off);
                user.setImageResource(R.drawable.ic_menu_user_on);
                more.setImageResource(R.drawable.ic_menu_more_off);
                break;
            case R.id.more :
            case R.id.moret :
                MoreFragment moreFragment = new MoreFragment();
                fragmentTransaction.replace(R.id.main,moreFragment);
                fragmentTransaction.addToBackStack(null);
                deal.setImageResource(R.drawable.ic_menu_deal_off);
                poi.setImageResource(R.drawable.ic_menu_poi_off);
                user.setImageResource(R.drawable.ic_menu_user_off);
                more.setImageResource(R.drawable.ic_menu_more_on);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
