package com.example.administrator.meituan.Task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.meituan.Activity.MainActivity;
import com.example.administrator.meituan.Activity.OrdersDetailActivity;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;

public class OrdersUpdateTask extends AsyncTask<String,Void,String> {
    private final static String ORDERS_UPDATE_PATH = "http://49.140.122.74:8080/meituanShop/orders/updateOrders.action";
    String oid;
    String osign;
    Context context;

    public OrdersUpdateTask(String osign, String oid, Context context){
        this.osign = osign;
        this.oid = oid;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String path = ORDERS_UPDATE_PATH+"?osign="+osign+"&oid="+oid;
        return new StringFromPath(path).getString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(osign.equals("1")){
            new ToOrderDetailByOidTask(context).execute(oid);
        }else if(osign.equals("3")){
            Toast.makeText(context,"订单取消成功",Toast.LENGTH_SHORT).show();
            ((Activity) context).finish();
        }else if(osign.equals("5")){
            String uid = new UsersFromSqlite(context).getUsers().getUid()+"";
            new ToOrdersActivityTask("1",uid,context).execute();
        }
        if(osign.equals("4")){
            if(s.equals("1")){
                Toast.makeText(context,"退款成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"操作失败",Toast.LENGTH_SHORT).show();
            }
            ((Activity) context).finish();
        }

    }
}