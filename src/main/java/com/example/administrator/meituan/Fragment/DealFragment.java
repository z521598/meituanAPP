package com.example.administrator.meituan.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.administrator.meituan.Activity.GoodsActivity;
import com.example.administrator.meituan.Activity.MessageListActivity;
import com.example.administrator.meituan.Activity.SearchActivity;
import com.example.administrator.meituan.Adapter.GoodsAdapter;
import com.example.administrator.meituan.POJO.Goods;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.KeyFromSqlite;
import com.example.administrator.meituan.Sqlite.SearchKeySqliteOpenHelper;
import com.example.administrator.meituan.Task.StringFromPath;
import com.example.administrator.meituan.Tool.ListViewForScrollView;
import com.example.administrator.meituan.Tool.StringToObject;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class DealFragment extends Fragment{

    private ProgressDialog progressDialog;
    private ListView homeView;
    private ListView keyView;
    private ScrollView dealScrollView;
    private LinearLayout favLin;
    private ImageView message_02;
    //windows+r+"cmd"+"ipconfig"查找IP地址
    //未连接网络的本机IP地址：127.0.0.1
    private ListView horizontalList;

    private static final String PATH = "http://49.140.122.74:8080/meituanShop/goods/findGoodsByGtype.action";
    private static final String ADV_PATH = "http://49.140.122.74:8080/meituanShop/goods/findByAdv.action";
    private static final String KEY_PATH = "http://49.140.122.74:8080/meituanShop/goods/findByKeyList.action";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("请稍等");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dealfrag, container, false);

        homeView = (ListViewForScrollView) view.findViewById(R.id.homeView);
        keyView = (ListView) view.findViewById(R.id.keyView);
        favLin = (LinearLayout) view.findViewById(R.id.favLin);
        message_02 = (ImageView) view.findViewById(R.id.messgae_02);
        new AdvTask().execute();
        SearchKeySqliteOpenHelper searchKeySqliteOpenHelper = new SearchKeySqliteOpenHelper(getActivity());
        searchKeySqliteOpenHelper.getReadableDatabase();
        if(new KeyFromSqlite(getActivity()).getKeys().size() != 0){
            new KeyTask().execute(KEY_PATH);
        }else{
            favLin.setVisibility(View.INVISIBLE);
        }
        message_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MessageListActivity.class));
            }
        });
        //8个导航的按钮事件，XML注册不好使？
        LinearLayout gtype1 = (LinearLayout) view.findViewById(R.id.gtype1);
        LinearLayout gtype2 = (LinearLayout) view.findViewById(R.id.gtype2);
        LinearLayout gtype3 = (LinearLayout) view.findViewById(R.id.gtype3);
        LinearLayout gtype4 = (LinearLayout) view.findViewById(R.id.gtype4);
        LinearLayout gtype5 = (LinearLayout) view.findViewById(R.id.gtype5);
        LinearLayout gtype6 = (LinearLayout) view.findViewById(R.id.gtype6);
        LinearLayout gtype7 = (LinearLayout) view.findViewById(R.id.gtype7);
        LinearLayout gtype8 = (LinearLayout) view.findViewById(R.id.gtype8);
        LinearLayout toSearch = (LinearLayout) view.findViewById(R.id.toSearch);
        dealScrollView = (ScrollView) view.findViewById(R.id.dealScrollView);
        dealScrollView.smoothScrollTo(0, 0);
        gtype1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClick(v);
            }
        });
         gtype2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClick(v);
            }
        });
         gtype3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClick(v);
            }
        });
         gtype4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClick(v);
            }
        });
         gtype5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClick(v);
            }
        });
         gtype6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClick(v);
            }
        });
         gtype7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClick(v);
            }
        });
         gtype8.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 navClick(v);
             }
         });
         toSearch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //初始化Key的Sqlite数据库
                 Intent intent = new Intent(getActivity(), SearchActivity.class);
                 getActivity().startActivity(intent);
             }
         });
        //跳转到信息列表
        message_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MessageListActivity.class));
            }
        });
        return view;
    }

    //得到ListJson数据，跳转并传值到团购列表页面,
    class DealAsynTask extends AsyncTask<String,Void,String>{

        String gtype;
        DealAsynTask(String gtype){
            this.gtype = gtype;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<Goods> goodsList = new ArrayList<Goods>();
            params[0] = params[0] + "?gtype="+gtype;
            return  new StringFromPath(params[0]).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getActivity(), GoodsActivity.class);
            intent.putExtra("jsonGoods",s);
            //需要加上getActivity()
            getActivity().startActivity(intent);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //每个导航的按钮事件
    public void navClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.gtype1 :
                new DealAsynTask("1").execute(PATH);
                break;
            case R.id.gtype2 :
                new DealAsynTask("2").execute(PATH);
                break;
            case R.id.gtype3 :
                new DealAsynTask("3").execute(PATH);
                break;
            case R.id.gtype4 :
                new DealAsynTask("4").execute(PATH);
                break;
            case R.id.gtype5 :
                new DealAsynTask("5").execute(PATH);
                break;
            case R.id.gtype6 :
                new DealAsynTask("6").execute(PATH);
                break;
            case R.id.gtype7 :
                new DealAsynTask("7").execute(PATH);
                break;
            case R.id.gtype8 :
                new DealAsynTask("8").execute(PATH);
                break;
        }
    }
    //获取广告资源
    class AdvTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return new StringFromPath(ADV_PATH).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            List<Goods> goodsList = new StringToObject().goodsList(s);
            homeView.setAdapter(new GoodsAdapter(goodsList,getActivity().getLayoutInflater(),getActivity()));
        }
    }
    //获取“猜你喜欢”资源
    class KeyTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            List<String> list = new KeyFromSqlite(getActivity()).getKeysLimit(3);
            Log.i("msg",list.size()+"");
            HttpPost httpRequest;
            List<NameValuePair> para;
            HttpResponse httpResponse;
            //建立HttpPost链接
            httpRequest=new HttpPost(params[0]);
            //Post操作参数必须使用NameValuePair[]阵列储存
            para=new ArrayList<NameValuePair>();
            String lisyJson = new Gson().toJson(list);
            Log.i("msg",lisyJson);
            para.add(new BasicNameValuePair("key", lisyJson));
            //发送Http Request
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(para, HTTP.UTF_8));
                //取得Http Response
                httpResponse=new DefaultHttpClient().execute(httpRequest);
                //若状态码为200
                if(httpResponse.getStatusLine().getStatusCode()==200){
                    String strResult= EntityUtils.toString(httpResponse.getEntity());
                    return strResult;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            keyView.setAdapter(new GoodsAdapter(new StringToObject().goodsList(s),getActivity().getLayoutInflater(),getActivity()));
        }
    }

}
