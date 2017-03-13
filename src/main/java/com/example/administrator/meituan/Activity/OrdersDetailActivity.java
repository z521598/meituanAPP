package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Task.StringFromPath;

public class OrdersDetailActivity extends ActionBarActivity {

    private final static String PATH = "http://49.140.122.74:8080/meituanShop/";
    private TextView ordersDetailTitle;
    private TextView ordersDetailPrice;
    private TextView ordersDetailTicket;
    private TextView ordersDetailContent;
    private TextView ordersDetailAddress;
    private TextView ordersDetailTelephone;
    private TextView ordersDetailOid;
    private TextView ordersDetailTime;
    private ImageView ordersDetailImage;
    private TextView ordersDetailOsign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);
        ordersDetailTitle = (TextView) findViewById(R.id.ordersDetailTitle);
        ordersDetailPrice = (TextView) findViewById(R.id.ordersDetailPrice);
        ordersDetailOsign = (TextView) findViewById(R.id.ordersDetailOsign);
        ordersDetailTicket = (TextView) findViewById(R.id.ordersDetailTicket);
        ordersDetailContent = (TextView) findViewById(R.id.ordersDetailContent);
        ordersDetailAddress = (TextView) findViewById(R.id.ordersDetailAddress);
        ordersDetailTelephone = (TextView) findViewById(R.id.ordersDetailTelephone);
        ordersDetailOid = (TextView) findViewById(R.id.ordersDetailOid);
        ordersDetailTime = (TextView) findViewById(R.id.ordersDetailTime);
        ordersDetailImage = (ImageView) findViewById(R.id.ordersDetailImage);

        Bundle bundle = getIntent().getExtras();
        ordersDetailTitle.setText(bundle.getString("gname"));
        ordersDetailPrice.setText(bundle.getString("gprice"));
        ordersDetailContent.setText(bundle.getString("gcontent"));
        ordersDetailOid.setText("订单编号："+bundle.getInt("oid"));
        ordersDetailTime.setText("下单时间："+bundle.getString("ocreatetime"));
        ordersDetailAddress.setText(bundle.getString("saddress"));
        ordersDetailTelephone.setText(bundle.getString("shomephone"));
        if(bundle.getString("ticket").equals("")){
            ordersDetailTicket.setText("暂无");
        }else{
            ordersDetailTicket.setText(bundle.getString("ticket"));
        }
        new SetImageViewFromPathTask(ordersDetailImage,bundle.getString("gcover")).execute(bundle.getString("gcover"));
        switch (bundle.getInt("osign")){
            case 0:
                ordersDetailOsign.setText("待付款");
                break;
            case 1:
                ordersDetailOsign.setText("待消费");
                break;
            case 2:
                ordersDetailOsign.setText("待评价");
                break;
            case 3:
                ordersDetailOsign.setText("已取消");
                break;
            case 4:
                ordersDetailOsign.setText("已退款");
                break;
            case 5:
                ordersDetailOsign.setText("已评价");
                break;

        }
    }

}
