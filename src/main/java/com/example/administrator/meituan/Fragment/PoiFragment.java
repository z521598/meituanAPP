package com.example.administrator.meituan.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.Activity.StoreActivity;
import com.example.administrator.meituan.POJO.Store;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Task.StringFromPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class PoiFragment extends Fragment{

    private ImageView imageView;
    private ListView poiList;
    private final static String PATH = "http://49.140.122.74:8080/meituanShop/store/findStoreBySaddress.action";
    private final static String  ONE_STORE_PATH = "http://49.140.122.74:8080/meituanShop/store/findStoreBySidForApp.action";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new InfoTask().execute(PATH);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poifrag,container,false);
        poiList = (ListView) view.findViewById(R.id.poiList);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    class SellerAdapter extends BaseAdapter {

        List<Store> storeList = new ArrayList<Store>();

        public SellerAdapter(List<Store> goodsList){
            this.storeList = goodsList;
        }
        @Override
        public int getCount() {
            return storeList.size();
        }

        @Override
        public Object getItem(int position) {
            return storeList.get(position);
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
                view = getActivity().getLayoutInflater().inflate(R.layout.image_and_text,null);
            }else{
                view = convertView;
            }
            //设置布局,获取控件
            TextView iat_title = (TextView) view.findViewById(R.id.iat_title);
            TextView iat_content = (TextView) view.findViewById(R.id.iat_content);
            RatingBar iat_ratingBar = (RatingBar) view.findViewById(R.id.iat_ratingBar);
            TextView iat_mark = (TextView) view.findViewById(R.id.iat_mark);
            ImageView iat_image = (ImageView) view.findViewById(R.id.iat_image);
            //todo 设置控件
            iat_title.setText(storeList.get(position).getStype() + "");
            Log.i("msg", "" + storeList.get(position).getSaddress());
            //todo 传地址为null
            //iat_content.setText(storeList.get(position).getSaddress()+"");
            iat_content.setText("");
            iat_ratingBar.setRating((float) (storeList.get(position).getSrating()));
            iat_mark.setText("");
            //图片大小不可过大！！
            String simage1 = storeList.get(position).getSimage1();
            new SetImageViewFromPathTask(iat_image,simage1).execute(simage1);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   new OneStoreTask(storeList.get(position).getSid()).execute(ONE_STORE_PATH);
                }
            });
            return view;
        }
    }
    //异步任务，获取List数据
    class InfoTask extends AsyncTask<String,Void,List<Store>>{

        @Override
        protected List<Store> doInBackground(String... params) {
            String listJson = new StringFromPath(params[0]).getString();
            List<Store> list = jsonToList(listJson);
            return list;
        }
        @Override
        protected void onPostExecute(List<Store> stores) {
            super.onPostExecute(stores);
            poiList.setAdapter(new SellerAdapter(stores));
        }
    }


    //异步任务，获取值，传值跳转
    class OneStoreTask extends AsyncTask<String,Void,String>{

        int sid;

        OneStoreTask(int sid){
            this.sid = sid;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            params[0] = params[0]+"?sid="+sid;
            String storeJson = new StringFromPath(params[0]).getString();
            return storeJson;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getActivity(), StoreActivity.class);
            intent.putExtra("json",s);
            getActivity().startActivity(intent);
        }
    }

    //将json转化为List
    private List<Store> jsonToList(String json){

        List<Store> list = new ArrayList<Store>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i<jsonArray.length(); i++){
                //获取每个Json对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Store store = new Store();
                Log.i("msg","@@@@@"+jsonObject.getString("simage1"));
                store.setSimage1(jsonObject.getString("simage1"));
                store.setStype(jsonObject.getString("stype"));
                store.setShomephone(jsonObject.getString("shomephone"));
                store.setSid(jsonObject.getInt("sid"));
                store.setSrating(jsonObject.getInt("srating"));
                store.setSdescription(jsonObject.getString("sdescription"));
                //todo 数据
                list.add(store);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
