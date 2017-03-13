package com.example.administrator.meituan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.POJO.Orders;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.OrdersUpdateTask;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Tool.StringToObject;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrdersActivity extends ActionBarActivity {

    private final static String ORDERS_UPDATE_PATH = "http://49.140.122.74:8080/meituanShop/orders/updateOrders.action";
    private ListView ordersListView;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ordersListView = (ListView) findViewById(R.id.ordersListView);
        progressDialog = new ProgressDialog(OrdersActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("请稍等");
        //获取意图
        Intent intent = getIntent();
        String ordersJson= intent.getStringExtra("ordersJson");
        List<Orders> ordersList = new StringToObject().ordersList(ordersJson);
        if(ordersList.size() == 0){
            Toast.makeText(OrdersActivity.this,"无此类型订单，将自动返回",Toast.LENGTH_LONG).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        OrdersActivity.this.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            ordersListView.setAdapter(new OrdersAdapter(ordersList));
        }
    }


    //适配器
    class OrdersAdapter extends BaseAdapter{

        List<Orders> goodsList;

        public OrdersAdapter(List<Orders> goodsList){
            this.goodsList = goodsList;
        }
        @Override
        public int getCount() {
            return goodsList.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView != null){
                view = convertView;
            }else{
                view = getLayoutInflater().inflate(R.layout.title_and_image_layout,null);
            }
            //设置布局,获取控件
            TextView orders_title = (TextView) view.findViewById(R.id.orders_title);
            TextView orders_sign = (TextView) view.findViewById(R.id.orders_sign);
            TextView orders_time = (TextView) view.findViewById(R.id.orders_time);
            TextView orders_price = (TextView) view.findViewById(R.id.orders_price);

            Button orders_button2 = (Button) view.findViewById(R.id.orders_button2);
            Button orders_button1 = (Button) view.findViewById(R.id.orders_button1);

            ImageView orders_image = (ImageView) view.findViewById(R.id.orders_image );
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            orders_time.setText("下单时间："+sdf.format(goodsList.get(position).getOcreatetime()));
            orders_title.setText(goodsList.get(position).getGname());
            orders_price.setText("价格："+goodsList.get(position).getGprice()+"元");
            //根据Osign设置显示文字
            switch (goodsList.get(position).getOsign()){
                case 0 :
                    orders_sign.setText("待支付");
                    orders_button1.setText("取消订单");
                    orders_button2.setText("去支付");
                    break;
                case 1 :
                    orders_button1.setVisibility(View.INVISIBLE);
                    orders_button2.setText("退款");
                    orders_sign.setText("待消费");
                    break;
                case 2 :
                    orders_button1.setVisibility(View.INVISIBLE);
                    orders_button2.setText("去评价");
                    orders_sign.setText("待评价");
                    break;
                case 3 :
                    orders_button1.setVisibility(View.INVISIBLE);
                    orders_button2.setVisibility(View.INVISIBLE);
                    orders_sign.setText("已取消");
                    break;
                case 4 :
                    orders_button1.setVisibility(View.INVISIBLE);
                    orders_button2.setVisibility(View.INVISIBLE);
                    orders_sign.setText("已退款");
                    break;
            }

            //运用异步任务，获取图片
            String gcover = goodsList.get(position).getGcover();
            new SetImageViewFromPathTask(orders_image,gcover).execute(gcover);
            //button1单击操作按钮，进行相应操作
            orders_button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (goodsList.get(position).getOsign()){
                        case 0 :
                            //取消订单
                            new OrdersUpdateTask("3",""+goodsList.get(position).getOid(),OrdersActivity.this).execute(ORDERS_UPDATE_PATH);
                            break;
                    }
                }
            });
            //button2单击操作按钮，进行相应操作
            orders_button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    switch (goodsList.get(position).getOsign()){
                        case 0 :
                            //去支付按钮
                            Intent toPay = new Intent(OrdersActivity.this,PayActivity.class);
                            bundle.putString("gname",goodsList.get(position).getGname());
                            bundle.putString("gprice",goodsList.get(position).getGprice()+"元");
                            bundle.putInt("oid",goodsList.get(position).getOid());
                            toPay.putExtras(bundle);
                            startActivity(toPay);
                            break;
                        case 1 :
                            //退款按钮
                            new OrdersUpdateTask("4",""+goodsList.get(position).getOid(),OrdersActivity.this).execute(ORDERS_UPDATE_PATH);
                            break;
                        case 2 :
                            //评论按钮
                            Intent toComment = new Intent(OrdersActivity.this,CommentActivity.class);
                            bundle.putString("gname",goodsList.get(position).getGname());
                            bundle.putInt("oid",goodsList.get(position).getOid());
                            bundle.putString("gcover",goodsList.get(position).getGcover());
                            bundle.putInt("gid",goodsList.get(position).getGid());
                            toComment.putExtras(bundle);
                            startActivity(toComment);
                            break;
                    }
                }
            });



            //单击整个view的项时，跳转到订单详情
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrdersActivity.this,OrdersDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("gname",goodsList.get(position).getGname());
                    bundle.putString("gprice", goodsList.get(position).getGprice() + "元");
                    bundle.putInt("oid", goodsList.get(position).getOid());
                    bundle.putString("gcontent", goodsList.get(position).getGcontent());
                    bundle.putString("shomephone", goodsList.get(position).getShomephone());
                    bundle.putString("saddress", goodsList.get(position).getSaddress());
                    bundle.putString("ticket",goodsList.get(position).getTicket());
                    bundle.putInt("osign", goodsList.get(position).getOsign());
                    bundle.putString("gcover", goodsList.get(position).getGcover());
                    //时间格式化
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    bundle.putString("ocreatetime",sdf.format(goodsList.get(position).getOcreatetime()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            return view;
        }
    }



}
