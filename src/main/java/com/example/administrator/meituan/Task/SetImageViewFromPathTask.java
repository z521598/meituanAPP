package com.example.administrator.meituan.Task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;

public class SetImageViewFromPathTask extends AsyncTask<String,Void,Bitmap> {
    private final static String BASE_IMAGE_PATH = "http://49.140.122.74:8080/meituanShop/";
    //防止适配时发生错误
    private String url;
    private ImageView imageView;

    public SetImageViewFromPathTask(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String path = BASE_IMAGE_PATH + params[0];
        try {
            //todo 设置标签
            imageView.setTag(url);
            Bitmap bitmap = new BitmapFromPath(path).getBitmap();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        //做判断，防止出错,getTag()获取标签
        if(imageView.getTag().equals(url)){
            imageView.setImageBitmap(bitmap);
        }
    }
}