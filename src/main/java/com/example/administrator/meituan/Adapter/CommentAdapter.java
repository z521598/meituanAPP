package com.example.administrator.meituan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.meituan.POJO.Comment;
import com.example.administrator.meituan.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/9/1.
 */
public class CommentAdapter extends BaseAdapter{

    List<Comment> commentList;
    LayoutInflater inflater;

    public CommentAdapter(List<Comment> commentList,LayoutInflater inflater){
        this.commentList = commentList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            //获取视图填充器
            view = inflater.inflate(R.layout.comment,null);
        }else{
            view = convertView;
        }


        TextView show_comment_content = (TextView) view.findViewById(R.id.show_comment_content);
        TextView show_comment_response = (TextView) view.findViewById(R.id.show_comment_response);
        RatingBar comment_ratingBar = (RatingBar) view.findViewById(R.id.comment_ratingBar);

        show_comment_content.setText(commentList.get(position).getCcontent());
        show_comment_response.setText(commentList.get(position).getCresponse());
        comment_ratingBar.setRating(Float.parseFloat("" + commentList.get(position).getRating()));

        return view;
    }
}
