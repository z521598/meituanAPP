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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.meituan.Activity.ChangeActivity;
import com.example.administrator.meituan.Activity.LoginActivity;
import com.example.administrator.meituan.Activity.MeActivity;
import com.example.administrator.meituan.Activity.MessageActivity;
import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Task.StringFromPath;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MoreFragment extends Fragment {
    private final static String PATH  = "http://49.140.122.74:8080/meituanShop/users/updateSeller.action";
    private LinearLayout more_message;
    private LinearLayout more_me;
    private LinearLayout more_store;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.morefrag,container,false);
        more_me = (LinearLayout) view.findViewById(R.id.more_me);
        more_message = (LinearLayout) view.findViewById(R.id.more_message);
        more_store = (LinearLayout) view.findViewById(R.id.more_store);

        more_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new UsersFromSqlite(getActivity()).getUsers() != null){
                    Users users = new UsersFromSqlite(getActivity()).getUsers();
                    new ChangeStoreTask(users.getUid()).execute(PATH);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }

            }
        });
        more_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeActivity.class);
                getActivity().startActivity(intent);
            }
        });
        more_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                intent.putExtra("msg","您想告诉我们什么？");
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }



    class ChangeStoreTask extends AsyncTask<String,Void,String>{

        int uid;
        ChangeStoreTask(int uid){
            this.uid = uid;
        }
        @Override
        protected String doInBackground(String... params) {
            params[0] = params[0]+"?uid="+uid;
            return new StringFromPath(params[0]).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getActivity(), ChangeActivity.class);
            intent.putExtra("sign",s);
            getActivity().startActivity(intent);
        }
    }
}
