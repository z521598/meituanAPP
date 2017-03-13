package com.example.administrator.meituan.Activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Task.LoginTask;
import com.example.administrator.meituan.Task.SetImageViewFromPathTask;
import com.example.administrator.meituan.Task.StringFromPath;

public class UsersUpdateActivity extends ActionBarActivity {

    private static final String PATH = "http://49.140.122.74:8080/meituanShop/users/updateMoreUsers.action";
    private static final String NEW_PATH = "http://49.140.122.74:8080/meituanShop/users/usersLogin.action";
    private ImageView uhead;
    private EditText uname;
    private EditText uemail;
    private EditText uaddress;
    private EditText utelephone;
    private LinearLayout save;
    private Spinner usex;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_update);
        users = new UsersFromSqlite(this).getUsers();
        uname = (EditText) findViewById(R.id.uname);
        uemail = (EditText) findViewById(R.id.uemail);
        uaddress = (EditText) findViewById(R.id.uaddress);
        utelephone = (EditText) findViewById(R.id.utelephone);
        uhead = (ImageView) findViewById(R.id.uhead);
        save = (LinearLayout) findViewById(R.id.save);
        usex = (Spinner) findViewById(R.id.usex);
        String[] sexs;
        if (users.getUsex().equals("男")) {
            sexs = new String[]{"男", "女"};
        } else {
            sexs = new String[]{"女", "男"};
        }
        InputFilter[] filters = {new InputFilter.LengthFilter(20)};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexs);
        usex.setAdapter(arrayAdapter);

        //保证EditText大小
        if (users.getUname().equals("")) {
            uname.setFilters(filters);
        } else {
            uname.setText(users.getUname());
        }
        if (users.getUemail().equals("")) {
            uemail.setFilters(filters);
        } else {
            uemail.setText(users.getUemail());
        }
        if (users.getUaddress().equals("")) {
            uaddress.setFilters(filters);
        } else {
            uaddress.setText(users.getUaddress());
        }
        if (users.getUtelephone().equals("")) {
            utelephone.setFilters(filters);
        } else {
            utelephone.setText(users.getUtelephone());
        }


        new SetImageViewFromPathTask(uhead, users.getUhead()).execute(users.getUhead());

        //save保存按钮
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String users_uname = uname.getText().toString();
                String users_uemail = uemail.getText().toString();
                String users_uaddress = uaddress.getText().toString();
                String users_utelephone = utelephone.getText().toString();
                String users_usex = usex.getSelectedItem().toString();
                int uid = users.getUid();
                String path = PATH + "?uname=" + users_uname + "&uemail=" + users_uemail + "&uaddress=" + users_uaddress + "&utelephone=" + users_utelephone + "&usex=" + users_usex + "&uid=" + uid;
                new UpdateMoreUsers().execute(path);
                Log.i("msg", path);
            }
        });
    }

    class UpdateMoreUsers extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return new StringFromPath(params[0]).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("1")) {
                Toast.makeText(UsersUpdateActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                new LoginTask(users.getUusername(), users.getUpassword(), UsersUpdateActivity.this).execute(NEW_PATH);
            }
        }
    }

}
