package com.example.administrator.meituan.Task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.administrator.meituan.Activity.OrdersActivity;

/**
 * Created by Administrator on 2016/8/30.
 */
//异步任务.获取订单数据，控制跳转
public class ToOrdersActivityTask extends AsyncTask<String,Void,String> {

    private final static String BASE_PATH = "http://49.140.122.74:8080/meituanShop/orders/findOrdersByOsignAndUid.action";
    String uid;
    String osign;
    Context context;

    public ToOrdersActivityTask(String osign, String uid,Context context){
        this.osign = osign;
        this.uid = uid;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        String path = BASE_PATH+"?osign="+osign+"&uid="+uid;
        return new StringFromPath(path).getString();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Intent intent = new Intent(context, OrdersActivity.class);
        intent.putExtra("ordersJson",s);
        context.startActivity(intent);

    }
}