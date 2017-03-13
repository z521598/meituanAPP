package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.Adapter.GoodsAdapter;
import com.example.administrator.meituan.POJO.Store;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Tool.StringToObject;

import org.json.JSONException;

public class StoreActivity extends ActionBarActivity {

    private TextView oneStoreTitle;
    private TextView oneStoreShomephone;
    private RatingBar oneStoreRating;
    private TextView oneStoreAddress;
    private ListView goodsListByStoreView;
    private ImageView oneStoreImage;
    private TextView oneStoreDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        oneStoreTitle = (TextView) findViewById(R.id.oneStoreTitle);
        oneStoreShomephone = (TextView) findViewById(R.id.oneStoreShomephone);
        oneStoreRating = (RatingBar) findViewById(R.id.oneStoreRating);
        oneStoreAddress = (TextView) findViewById(R.id.oneStoreAddress);
        goodsListByStoreView = (ListView) findViewById(R.id.goodsListByStoreView);
        oneStoreImage = (ImageView) findViewById(R.id.oneStoreImage);
        oneStoreDescription = (TextView) findViewById(R.id.oneStoreDescription);
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        try {
            Store store = new StringToObject().stores(json);
            oneStoreDescription.setText(store.getSdescription());
            oneStoreTitle.setText(store.getStype());
            oneStoreShomephone.setText("Tel:"+store.getShomephone());
            oneStoreRating.setRating(Float.parseFloat(store.getSrating()+""));
            oneStoreAddress.setText(store.getSaddress());
            goodsListByStoreView.setAdapter(new GoodsAdapter(store.getGoodsList(), getLayoutInflater(), StoreActivity.this));
            new SetImageViewFromPathTask(oneStoreImage,store.getSimage1()).execute(store.getSimage1());

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
