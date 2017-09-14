package com.demo.simplecursoradapter;


import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.security.PrivateKey;

/**
 * Created by ${momoThree} on 2017/9/14.
 * Title:
 */

public class MySimpleCursorDapater  extends SimpleCursorAdapter {

    private Button  btn01,btn02;
    private String  sql;
    private  DBUtils db;
    private  Cursor c;

    //构造方法
    /**
     *
     * @param context 上下文
     * @param layout 布局
     * @param c       游标对象
     * @param from   字符串数组
     * @param to    int数组 上面字段需要展示对应的控件的id
     * @param flags  标志
     */
    public MySimpleCursorDapater(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.c=c;
        db=new DBUtils(context);
    }

    //和BaseAdapter 中的getView的方法类似，都是在展示每一个item的时候被调用
    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        btn01= (Button) view.findViewById(R.id.button1);
        btn02= (Button) view.findViewById(R.id.button2);
        final TextView tv1=(TextView) view.findViewById(R.id.textView1);
        TextView tv2=(TextView) view.findViewById(R.id.textView2);
        final String id=tv1.getText().toString();
        final String name=tv2.getText().toString();
        //给删除按钮添加点击的监听事件

        btn02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView tv=(TextView) view.findViewById(R.id.textView1);
                String id=tv.getText().toString();
                Log.i("tag", "被点击的数据的id是："+tv.getText());
                db.delete(id);
                //更新Cursor
                c=db.query();
                //新的Cursor对象替换旧的Cursor对象达到刷新的功能
                MySimpleCursorDapater.this.swapCursor(c);
            }
        });
        btn01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //弹出自定的对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                //添加对话框的标题
                builder.setTitle("修改工资金额");
                //获取自定义布局对应的View对象
                View view=LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);
                TextView tv=(TextView) view.findViewById(R.id.textView1);
                final EditText et=(EditText) view.findViewById(R.id.editText1);
                tv.setText(name);
                //对话框加载自定义布局
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    /**
                     * 将修改的内容更新到数据库中
                     * 获取需要修改的数据
                     * 同步更新ListView中的数据
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //更新金额  首先获取金额
                        String salary=et.getText().toString();
                        db.update(salary, id);
                        c=db.query();
                        MySimpleCursorDapater.this.swapCursor(c);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                builder.show();
            }
        });

    }
}
