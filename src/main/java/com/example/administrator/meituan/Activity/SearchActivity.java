package com.example.administrator.meituan.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.meituan.POJO.Users;
import com.example.administrator.meituan.R;
import com.example.administrator.meituan.Sqlite.KeyFromSqlite;
import com.example.administrator.meituan.Sqlite.SearchKeySqliteOpenHelper;
import com.example.administrator.meituan.Sqlite.UsersFromSqlite;
import com.example.administrator.meituan.Task.StringFromPath;
import com.example.administrator.meituan.Tool.ListViewForScrollView;

import java.util.List;

public class SearchActivity extends ActionBarActivity {

    private final static String PATH = "http://49.140.122.74:8080/meituanShop/goods/findForSearch.action";
    private EditText search_text;
    private ImageView search;
    private ListViewForScrollView search_list;
    private TextView clear;
    private Users users;
    private ScrollView searchScrollView;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_text = (EditText) findViewById(R.id.search_text);
        search_list = (ListViewForScrollView) findViewById(R.id.search_list);
        search = (ImageView) findViewById(R.id.search);
        clear = (TextView) findViewById(R.id.clear);
        searchScrollView = (ScrollView) findViewById(R.id.searchScrollView);
        searchScrollView.smoothScrollTo(0,0);
        users  = new UsersFromSqlite(SearchActivity.this).getUsers();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gname = search_text.getText().toString();
                new SearchTask(gname).execute(PATH);
            }
        });
        list = new KeyFromSqlite(SearchActivity.this).getKeys();
        //ArrayAdapter
        arrayAdapter = new ArrayAdapter<String>(SearchActivity.this,R.layout.support_simple_spinner_dropdown_item,list);
        search_list.setAdapter(arrayAdapter);
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = list.get(position);
                search_text.setText(key);
            }
        });
        if(new KeyFromSqlite(SearchActivity.this).getKeys().size() == 0){
            clear.setVisibility(View.INVISIBLE);
        }


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchKeySqliteOpenHelper searchKeySqliteOpenHelper = new SearchKeySqliteOpenHelper(SearchActivity.this);
                SQLiteDatabase sqLiteDatabase = searchKeySqliteOpenHelper.getReadableDatabase();
                String sql = "delete from skey";
                sqLiteDatabase.execSQL(sql);
                clear.setVisibility(View.INVISIBLE);
                search_list.setAdapter(null);
            }
        });
    }

    class SearchTask extends AsyncTask<String,Void,String>{

        String gname;
        SearchTask(String gname){
            this.gname = gname;
        }
        @Override
        protected String doInBackground(String... params) {
            SearchKeySqliteOpenHelper searchKeySqliteOpenHelper = new SearchKeySqliteOpenHelper(SearchActivity.this);
            SQLiteDatabase sqLiteDatabase = searchKeySqliteOpenHelper.getReadableDatabase();
            int uid = 0;
            if(users != null){
                uid = users.getUid();
            }
            String sql = "insert into skey (kname,uid) values ('"+gname+"','"+uid+"')";
            sqLiteDatabase.execSQL(sql);
            params[0] = params[0]+"?gname="+gname;
            return new StringFromPath(params[0]).getString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(SearchActivity.this,GoodsActivity.class);
            intent.putExtra("jsonGoods",s);
            startActivity(intent);
        }
    }

}
