package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.POJO.Message;
import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Task.StringFromPath;
import com.example.administrator.meituan.Tool.StringToObject;

import java.util.List;

public class MessageListActivity extends ActionBarActivity {

    private final static String PATH = "http://49.140.122.74:8080/meituanShop/message/findByUusername.action";
    private final static String UPDATE_PATH = "http://49.140.122.74:8080/meituanShop/message/updateState.action";
    private ListView messgaeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        messgaeListView = (ListView) findViewById(R.id.messgaeListView);
        Users users = new UsersFromSqlite(MessageListActivity.this).getUsers();
        if(users != null){
            new GetMessageTask(users.getUusername()).execute(PATH);
        }else {
            Toast.makeText(MessageListActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
        }
    }

    class GetMessageTask extends AsyncTask<String,Void,List<Message>>{

        String uusername;
        GetMessageTask(String uusername){
            this.uusername = uusername;
        }

        @Override
        protected List<Message> doInBackground(String... params) {
            params[0] = params[0]+"?uusername="+uusername;
            Log.i("msg",params[0]);
            String jsonList = new StringFromPath(params[0]).getString();
            //Toast.makeText(MessageListActivity.this,jsonList,Toast.LENGTH_SHORT).show();
            List<Message> list = new StringToObject().messageList(jsonList);;
            return list;
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);
            messgaeListView.setAdapter(new MsgAdapter(messages));
        }
    }

    class MsgAdapter extends BaseAdapter{
        List<Message> messages;
        MsgAdapter(List<Message> messages){
            this.messages = messages;
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null){
                view = getLayoutInflater().inflate(R.layout.text_only,null);
            }else{
                view = convertView;
            }
            TextView onlyText = (TextView) view.findViewById(R.id.onlyText);
            onlyText.setText(messages.get(position).getMtitle());
            int msign = messages.get(position).getMsign();
            //加粗
            TextPaint tp = onlyText.getPaint();
            if(msign == 0 ){
                tp.setFakeBoldText(true);
            }else{
                tp.setFakeBoldText(false);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MessageListActivity.this,MsgContentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("mcontent",messages.get(position).getMcontent());
                    bundle.putString("mtitle", messages.get(position).getMtitle());
                    bundle.putInt("mid", messages.get(position).getMid());
                    bundle.getInt("msign", messages.get(position).getMsign());
                    new UpdateTask( messages.get(position).getMid()).execute(UPDATE_PATH);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    class UpdateTask extends AsyncTask<String,Void,String>{

        int mid;
        UpdateTask(int mid){
            this.mid = mid;
        }

        @Override
        protected String doInBackground(String... params) {
            params[0] = params[0]+"?mid="+mid;
            return new StringFromPath(params[0]).getString();
        }
    }
}
