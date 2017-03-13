package com.example.administrator.meituan.Task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.administrator.meituan.Activity.OrdersDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ToOrderDetailByOidTask extends AsyncTask<String,Void,String>{

    private static final String PATH = "http://49.140.122.74:8080/meituanShop/orders/findOrdersByOidForApp.action";
    Context context;

    public ToOrderDetailByOidTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String path = PATH+"?oid="+params[0];
        return new StringFromPath(path).getString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Intent intent = new Intent(context, OrdersDetailActivity.class);
        Bundle bundle = new Bundle();
        try {
            JSONObject jsonObject = new JSONObject(s);
            bundle.putString("gname", jsonObject.getString("gname"));
            bundle.putString("gprice", jsonObject.getString("gprice"));
            bundle.putString("gcontent", jsonObject.getString("gcontent"));
            bundle.putInt("oid", jsonObject.getInt("oid"));
            bundle.putString("ocreatetime", jsonObject.getString("ocreatetime"));
            bundle.putString("saddress", jsonObject.getString("saddress"));
            bundle.putString("shomephone", jsonObject.getString("shomephone"));
            bundle.putString("ticket", jsonObject.getString("ticket"));
            bundle.putString("gcover", jsonObject.getString("gcover"));
            bundle.putInt("osign", jsonObject.getInt("osign"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
