package com.demo.simplecursoradapter;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    public EditText  edit01,edit02;
    private ListView listView;
    private DBUtils dbUtils;
    private Cursor cursor;
    MySimpleCursorDapater adapter;
    private String salary;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edit01= (EditText) findViewById(R.id.id_et1);
        edit02= (EditText) findViewById(R.id.id_et2);
        listView= (ListView) findViewById(R.id.id_listview);
        dbUtils=new DBUtils(this);
        //查询出所有的人信息获取Cursor对象
        cursor = dbUtils.query();
        String from []={"_id","name","salary"};
        int to[]={R.id.textView1,R.id.textView2,R.id.textView3};
        adapter=new MySimpleCursorDapater(this,
                R.layout.custom_item_layout,
                cursor,
                from,
                to,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    public  void  commitData(View v){
        name=edit01.getText().toString();
        salary=edit02.getText().toString();
        //将数据写入person表中
        dbUtils.insert1(name,salary);
        //重新查询数据更新Cursor对象
        cursor=dbUtils.query();
        //刷新
        adapter.swapCursor(cursor);
    }



}
