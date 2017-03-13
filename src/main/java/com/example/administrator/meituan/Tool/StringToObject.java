package com.example.administrator.meituan.Tool;

import android.util.Log;

import com.example.administrator.meituan.POJO.Comment;
import com.example.administrator.meituan.POJO.Goods;
import com.example.administrator.meituan.POJO.Message;
import com.example.administrator.meituan.POJO.Orders;
import com.example.administrator.meituan.POJO.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class StringToObject {

    //把json字符串变成List<Goods>
    public List<Goods> goodsList(String json){
        List<Goods> list = new ArrayList<Goods>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i<jsonArray.length(); i++){
                //获取每个Json对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Goods goods = new Goods();
                goods.setGid(Integer.parseInt(jsonObject.getString("gid")));
                goods.setUid(Integer.parseInt(jsonObject.getString("uid")));
                goods.setGpersonnum(jsonObject.getString("gpersonnum"));
                goods.setGprice(Double.parseDouble(jsonObject.getString("gprice")));
                goods.setGcover(jsonObject.getString("gcover"));
                goods.setGname(jsonObject.getString("gname"));
                //todo 其他数据
                list.add(goods);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //json字符串变成List<Messgae>
    public List<Message> messageList(String json){
        List<Message> list = new ArrayList<Message>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i<jsonArray.length(); i++){
                //获取每个Json对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Message message = new Message();
                message.setMid(jsonObject.getInt("mid"));
                message.setMtitle(jsonObject.getString("mtitle"));
                message.setMtype(jsonObject.getInt("mtype"));
                message.setMsign(jsonObject.getInt("msign"));
                message.setMcontent(jsonObject.getString("mcontent"));
                list.add(message);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    //将json转化为List<Orders>
    public List<Orders> ordersList(String ordersJson){
        List<Orders> list = new ArrayList<Orders>();
        try {
            JSONArray jsonArray = new JSONArray(ordersJson);
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Orders orders = new Orders();
                orders.setOid(jsonObject.getInt("oid"));
                orders.setGname(jsonObject.getString("gname"));
                orders.setGprice(jsonObject.getDouble("gprice"));
                orders.setGcontent(jsonObject.getString("gcontent"));
                orders.setTicket(jsonObject.getString("ticket"));
                orders.setGid(jsonObject.getInt("gid"));
                //处理时间
                String ocreatetimeString = jsonObject.getString("ocreatetime");
                Date ocreatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(ocreatetimeString);
                orders.setShomephone(jsonObject.getString("shomephone"));
                orders.setSaddress(jsonObject.getString("saddress"));

                orders.setOcreatetime(ocreatetime);
                orders.setOsign(jsonObject.getInt("osign"));
                orders.setGcover(jsonObject.getString("gcover"));
                //todo 日期
                //orders.setOcreatetime(jsonObject.get("ocreatetime"));

                list.add(orders);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }

    public Goods goods(String json) throws JSONException {
        Goods goods = new Goods();
        JSONObject jsonObject = new JSONObject(json);
        goods.setGid(jsonObject.getInt("gid"));
        goods.setGname(jsonObject.getString("gname"));
        goods.setGcontent(jsonObject.getString("gcontent"));
        goods.setGdescription(jsonObject.getString("gdescription"));
        goods.setGcover(jsonObject.getString("gcover"));
        goods.setGpersonnum(jsonObject.getString("gpersonnum"));
        goods.setGoldprice(jsonObject.getDouble("goldprice"));
        goods.setGprice(jsonObject.getDouble("gprice"));
        String commentlist = jsonObject.getString("commentlist");
        JSONArray jsonArray = new JSONArray(commentlist);
        List<Comment> list = new ArrayList<Comment>();
        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject1= jsonArray.getJSONObject(i);
            Comment comment = new Comment();
            comment.setCid(jsonObject1.getInt("cid"));
            comment.setRating(jsonObject1.getDouble("rating"));
            comment.setCcontent(jsonObject1.getString("ccontent"));
            comment.setCresponse(jsonObject1.getString("cresponse"));
            list.add(comment);
        }
        goods.setCommentlist(list);
        return goods;
    }

    public Store stores(String json) throws JSONException {
            Store store = new Store();
            JSONArray ja = new JSONArray(json);
            //注意json结构
            JSONObject jsonObject = ja.getJSONObject(0);
            store.setSimage1(jsonObject.getString("simage1"));
            store.setStype(jsonObject.getString("stype"));
            store.setShomephone(jsonObject.getString("shomephone"));
            store.setSid(jsonObject.getInt("sid"));
           store.setSaddress(jsonObject.getString("saddress"));
            store.setSrating(jsonObject.getInt("srating"));
            store.setSdescription(jsonObject.getString("sdescription"));
            //todo 数据
            String jsonList = jsonObject.getString("goodsList");
            List<Goods> goodsList =  new ArrayList<Goods>();
            JSONArray jsonArray = new JSONArray(jsonList);
            for(int i = 0; i<jsonArray.length(); i++){
                //获取每个Json对象
                JSONObject goodsObject = jsonArray.getJSONObject(i);
                Goods goods = new Goods();
                goods.setGid(goodsObject.getInt("gid"));
                goods.setGname(goodsObject.getString("gname"));
                goods.setGcover(goodsObject.getString("gcover"));
                goods.setGpersonnum(goodsObject.getString("gpersonnum"));
                goods.setGoldprice(goodsObject.getDouble("goldprice"));
                goods.setGprice(goodsObject.getDouble("gprice"));
                goodsList.add(goods);
            }
            store.setGoodsList(goodsList);
        return store;
    }

}
