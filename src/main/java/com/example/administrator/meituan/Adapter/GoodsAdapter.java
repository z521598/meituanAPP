package com.example.administrator.meituan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.meituan.Activity.OneGoodsActivity;
import com.example.administrator.meituan.POJO.Goods;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Task.StringFromPath;

import java.util.ArrayList;
import java.util.List;

//通过获取List<Goods>,适配数据
public class GoodsAdapter extends BaseAdapter {

    private final static String ONE_GOODS_PATH = "http://49.140.122.74:8080/meituanShop/goods/findByGidForApp.action";
    List<Goods> goodsList = new ArrayList<Goods>();
    private LayoutInflater inflater;
    Context context;

    public GoodsAdapter(List<Goods> goodsList,LayoutInflater inflater,Context context){
        this.goodsList = goodsList;
        this.inflater = inflater;
        this.context = context;
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
        if(convertView == null){
            //获取视图填充器
            view = inflater.inflate(R.layout.image_and_text,null);
        }else{
            view = convertView;
        }
        //设置布局,获取控件
        TextView iat_title = (TextView) view.findViewById(R.id.iat_title);
        TextView iat_content = (TextView) view.findViewById(R.id.iat_content);
        RatingBar iat_ratingBar = (RatingBar) view.findViewById(R.id.iat_ratingBar);
        TextView iat_mark = (TextView) view.findViewById(R.id.iat_mark);
        ImageView iat_image = (ImageView) view.findViewById(R.id.iat_image);
        //设置控件
        iat_title.setText(goodsList.get(position).getGname());
        iat_content.setText(goodsList.get(position).getGprice() + "元");
        iat_ratingBar.setRating(3.2f);
        iat_mark.setText(goodsList.get(position).getGpersonnum() + "人");
        String gcover = goodsList.get(position).getGcover();
        //图片大小不可过大！！

        new SetImageViewFromPathTask(iat_image,gcover).execute(gcover);
        //设置商品详情跳转
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OneGoodsTask(goodsList.get(position).getGid().toString()).execute(ONE_GOODS_PATH);
            }
        });
        return view;
    }
    //跳转到详情前，获取数据
    class OneGoodsTask extends AsyncTask<String,Void,String> {

        String gid;

        OneGoodsTask(String gid){
            this.gid = gid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            params[0] = params[0]+"?gid="+gid;
            String OneGoodsjson = new StringFromPath(params[0]).getString();
            return OneGoodsjson;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(context,OneGoodsActivity.class);
            intent.putExtra("oneGoods", s);
            context.startActivity(intent);
        }
    }
}