package com.example.administrator.meituan.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 *从网络读取字符串类型数据的方法
 * Created by Administrator on 2016/8/13.
 */
public class StringFromPath {

    private String path;

    public StringFromPath(String path){
        this.path = path;

    }

    public String getString(){
        BufferedReader bufferedReader = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(path).openConnection();
            //设置提交方法
            httpURLConnection.setRequestMethod("GET");
            //设置超时时间
            httpURLConnection.setReadTimeout(5000);
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            //字符缓冲区
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = bufferedReader.readLine()) != null){
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
