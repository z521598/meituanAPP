package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Task.OrdersUpdateTask;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Task.StringFromPath;

public class CommentActivity extends ActionBarActivity {
    private final static String PATH = "http://49.140.122.74:8080/meituanShop/orders/comment.action";
    private TextView comment_title;
    private ImageView comment_image;
    private EditText comment_content;
    private RatingBar comment_ratingBar;
    private Button comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final Bundle bundle = getIntent().getExtras();
        //获取设置控件
        comment_title = (TextView) findViewById(R.id.comment_title);
        comment_image = (ImageView) findViewById(R.id.comment_image);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_ratingBar = (RatingBar) findViewById(R.id.comment_ratingBar);
        comment = (Button) findViewById(R.id.comment);
        comment_title.setText(bundle.getString("gname"));
        new SetImageViewFromPathTask(comment_image,bundle.getString("gcover")).execute(bundle.getString("gcover"));
        //提交按钮
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ComentTask(comment_content.getText().toString(), bundle.getInt("gid"), bundle.getInt("oid"), comment_ratingBar.getRating()).execute(PATH);
            }
        });

    }

    class ComentTask extends AsyncTask<String,Void,String>{

        String ccontent;
        int gid;
        int oid;
        int uid;
        Float rating;
        int osign;


        ComentTask(String ccontent, int gid, int oid, Float rating){
            this.ccontent = ccontent;
            this.gid = gid;
            this.oid = oid;
            this.rating = rating;
            this.uid = ((Users) new UsersFromSqlite(CommentActivity.this).getUsers()).getUid();
            this.osign = 5;

        }

        @Override
        protected String doInBackground(String... params) {
            params[0] = params[0]+"?ccontent="+ccontent.replace(" ","@")+"&gid="+gid+"&oid="+oid+"&uid="+uid+"&rating="+rating+"&osign="+osign;
            Log.i("msg",params[0]);
            return new StringFromPath(params[0]).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("2")){
                Toast.makeText(CommentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                CommentActivity.this.finish();
            }else{
                Toast.makeText(CommentActivity.this,"评论失败",Toast.LENGTH_SHORT).show();
                CommentActivity.this.finish();
            }
        }
    }


}
