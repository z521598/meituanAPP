package com.example.administrator.meituan.Task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;

import java.net.URL;

/**
 * Created by Administrator on 2016/8/25.
 */
public class BitmapFromPath {
    private String path;

    public BitmapFromPath(String path){
        this.path = path;
    }

    public Bitmap getBitmap() throws IOException {
        HttpURLConnection hc = (HttpURLConnection) new URL(path).openConnection();
        hc.setRequestMethod("POST");
        hc.setReadTimeout(6000);
        Bitmap bitmap = BitmapFactory.decodeStream(hc.getInputStream());
        return  bitmap;
    }
}
