package com.demo.simplecursoradapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SeekBar;

/**
 * Created by ${momoThree} on 2017/9/14.
 * Title:
 */

public class DBUtils {


    private final SQLiteDatabase db;
    private String sql;
    private  Cursor c;
    private String name;
    private String salary;

    //创建或打开数据

    /**
     * 参数说明：
     * 1.创建或者打开数据库
     * 2.设置创建或打开数据库的模式为私有
     * 3.cursorfactory 工厂模式通常设置为null就够了
     */

    public DBUtils(Context context) {
        db = context.openOrCreateDatabase("android.db", context.MODE_PRIVATE, null);
       createTablePerson();
    }

    //创建Person表
    private void createTablePerson() {
        sql = "CREATE TABLE IF NOT EXISTS PERSON (id integer PRIMARY KEY AUTOINCREMENT ,name VARCHAR2(20) ,salary VARCHAR2(20))";
        db.execSQL(sql);
    }

    //数据库的操作
    //查询
    //1.查询语句
    //2.参数1：要执行的SQL语句
    //   参数2：SQL语句中对应的？的值
    public Cursor  query(){
        sql="SELECT id  _id,name,salary FROM person";
        c=db.rawQuery(sql,null);
        return  c;
    }

    //查询所有的人的信息

    public  Cursor  query2(){
       c=db.query("person" ,
               new String[]{"id,_id","name","salary"},
               null,
               null,//查询条件中所对应的值
               null,//分组
               null,//分组条件
               null//排序
               );
        return  c;
    }

    //保存数据
    //方法一：
    public  void insert1(String name,String salary){
        sql="INSERT INTO person(name,salary) VALUES(?,?)";
        db.execSQL(sql,new Object[]{name,salary});
    }
    //保存数据
    //方法二：
    public  void  insert2(String name,String salary){
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("salary",salary);
        db.insert("person",null,values);
    }

    //删除数据  方法一：
    public  void   delete(String  id){
        sql="DELETE FROM person WHERE id=?";
        db.execSQL(sql,new String[]{id});
    }
    //删除数据 方法二：
    public  void  delete2(String id){
        db.delete("person","id=?",new String[]{id});
    }

    //修改数据方法一：
    public void update(String salary,String id){
        sql="UPDATE person SET salary=? WHERE id=?";
        db.execSQL(sql,new String[] {salary,id});
    }
    //修改数据方法二：
    public void update2(String id,String salary){
        ContentValues values=new ContentValues();
        values.put("salary",salary);
        db.update("person",values,"id=?",new String[]{id});
    }


}
