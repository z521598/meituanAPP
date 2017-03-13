package com.example.administrator.meituan.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.Activity.LoginActivity;
import com.example.administrator.meituan.Activity.MessageListActivity;
import com.example.administrator.meituan.Activity.UpdatePasswordActivity;
import com.example.administrator.meituan.Activity.UsersUpdateActivity;
import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Task.ToOrdersActivityTask;
import com.example.administrator.meituan.Tool.CircleImageView;

/**
 * Created by Administrator on 2016/8/2.
 */
public class UserFragment extends Fragment{

    private LinearLayout allOrdersButton;
    private LinearLayout orders_0;
    private LinearLayout orders_1;
    private LinearLayout orders_2;
    private LinearLayout orders_4;
    private LinearLayout users_updateUsers;
    private LinearLayout users_updatePwd;
    private ImageView messgae_01;
    private Users users;
    private ImageView users_head_image;
    private ProgressDialog progressDialog;

    int uid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userfrag,container,false);
        TextView user_to_login = (TextView) view.findViewById(R.id.user_to_login);
        allOrdersButton = (LinearLayout) view.findViewById(R.id.allOrdersButton);
        orders_0  = (LinearLayout) view.findViewById(R.id.orders_0);
        orders_1  = (LinearLayout) view.findViewById(R.id.orders_1);
        orders_2  = (LinearLayout) view.findViewById(R.id.orders_2);
        orders_4  = (LinearLayout) view.findViewById(R.id.orders_4);
        messgae_01 = (ImageView) view.findViewById(R.id.messgae_01);
        users_head_image = (CircleImageView) view.findViewById(R.id.users_head_image);
        users_updateUsers = (LinearLayout) view.findViewById(R.id.users_updateUsers);
        users_updatePwd = (LinearLayout) view.findViewById(R.id.users_updatePwd);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("正在加载");
        progressDialog.setMessage("请稍等");

        users = new UsersFromSqlite(getActivity()).getUsers();
        if(users != null){
                uid = users.getUid();
                if(!users.getUname().equals("")){
                    user_to_login.setText(users.getUname());
                }else{
                    user_to_login.setText(""+users.getUusername());
                }
                if(!users.getUhead().equals("")){
                    new SetImageViewFromPathTask(users_head_image,users.getUhead()).execute(users.getUhead());
                }
        }
        //用户页面跳转到登录页面
        user_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过getActivity方法获取到显示这个fragment的activity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //设置监听事件，5个
        allOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users != null){
                    new ToOrdersActivityTask("10",""+uid,getActivity()).execute();
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        orders_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users != null){
                    new ToOrdersActivityTask("0",""+uid,getActivity()).execute();
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
         orders_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users != null){
                    new ToOrdersActivityTask("1",""+uid,getActivity()).execute();
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            }
        });
        orders_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users != null){
                    new ToOrdersActivityTask("2",""+uid,getActivity()).execute();
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        orders_4.setOnClickListener(new View.OnClickListener() {
          @Override
                public void onClick(View v) {
              if(users != null){
                  new ToOrdersActivityTask("4", "" + uid,getActivity()).execute();
              }else {
                  Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                  getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
              }
            }
        });
        //跳转到修改密码
        users_updatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users != null){
                    Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        //跳转到完善资料
        users_updateUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users != null){
                    Intent intent = new Intent(getActivity(), UsersUpdateActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        //跳转到消息列表
        messgae_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MessageListActivity.class));
            }
        });
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
