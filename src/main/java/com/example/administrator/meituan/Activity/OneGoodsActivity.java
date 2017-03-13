package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.Adapter.CommentAdapter;
import com.example.administrator.meituan.POJO.Goods;
import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Task.StringFromPath;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Tool.ListViewForScrollView;
import com.example.administrator.meituan.Tool.StringToObject;

import org.json.JSONException;

public class OneGoodsActivity extends ActionBarActivity {

    private final static String ADD_ORDER_PATH = "http://49.140.122.74:8080/meituanShop/orders/addOrders.action";
    private ImageView oneGoodsImage;
    private TextView oneGoodsTitle;
    private TextView oneGoodsPrice;
    private TextView oneGoodsPerson;
    private TextView oneGoodsContent;
    private TextView oneGoodsDescription;
    private Button oneGoodsBuy;
    private ListView commentList;
    private Goods g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_goods);
        //获取意图
        String oneGoodsJson =  getIntent().getStringExtra("oneGoods");
        oneGoodsTitle = (TextView) findViewById(R.id.oneGoodsTitle);
        oneGoodsPrice = (TextView) findViewById(R.id.oneGoodsPrice);
        oneGoodsPerson = (TextView) findViewById(R.id.oneGoodsPerson);
        oneGoodsContent = (TextView) findViewById(R.id.oneGoodsContent);
        oneGoodsDescription = (TextView) findViewById(R.id.oneGoodsDescription);
        oneGoodsImage = (ImageView) findViewById(R.id.oneGoodsImage);
        oneGoodsBuy = (Button) findViewById(R.id.oneGoodsBuy);
        commentList = (ListViewForScrollView) findViewById(R.id.commentList);
            //设置界面内容
        try {
            g = new StringToObject().goods(oneGoodsJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        oneGoodsTitle.setText(g.getGname());
        oneGoodsPrice.setText(g.getGprice()+"元");
        oneGoodsPerson.setText(g.getGpersonnum()+"人餐");
        //字符串操作
        oneGoodsContent.setText(g.getGcontent().replace(",","\n").replace("*","X"));
        oneGoodsDescription.setText(g.getGdescription());
        new SetImageViewFromPathTask(oneGoodsImage,g.getGcover()).execute(g.getGcover());
        commentList.setAdapter(new CommentAdapter(g.getCommentlist(),getLayoutInflater()));
        commentList.setFocusable(false);
        //设置Buy单击事件
        oneGoodsBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = new UsersFromSqlite(OneGoodsActivity.this).getUsers();
                if(users != null){
                    int uid = users.getUid();
                    int gid = g.getGid();
                    new AddOrdersTask(uid, gid).execute(ADD_ORDER_PATH);
                    OneGoodsActivity.this.finish();
                }else {
                    Toast.makeText(OneGoodsActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OneGoodsActivity.this, LoginActivity.class));
                }
            }
        });
    }

    //提交订单
    class AddOrdersTask extends AsyncTask<String,Void,String>{

        private int uid;
        private int gid;

        AddOrdersTask(int uid,int gid){
            this.uid = uid;
            this.gid = gid;
        }
        @Override
        protected String doInBackground(String... params) {
            params[0] = params[0]+"?gid="+gid+"&uid="+uid;
            String returnSign = new StringFromPath(params[0]).getString();
            return returnSign;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("1")){
                Toast.makeText(OneGoodsActivity.this,"购买成功，请前往订单列表付款",Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(OneGoodsActivity.this,PayActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);*/
            }else {
                Toast.makeText(OneGoodsActivity.this,"库存不足，购买失败",Toast.LENGTH_SHORT).show();
            }
        }
    }





}
