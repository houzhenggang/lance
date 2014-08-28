package com.flickr.test;

import com.alibaba.fastjson.JSONObject;
import com.flickr.db.pool.DBExecutors;
import com.flickr.db.pool.IDBExecutors;
import com.flickr.test.pojo.Sequences;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-27 16:24
 * To change this template use File | Settings | File Templates.
 */
public class DBTest {
    private IDBExecutors executors = null;

    @BeforeTest
    public void setUp() {
        System.setProperty("DataConfig", "F:\\git\\lance\\trunk\\flickr\\src\\main\\resources\\jdbc.properties");
        executors = new DBExecutors();
    }

    @Test
    public void runQuery() {
        String sql = "select count(1) from sequence where id=?";
        System.out.println("记录行int:" + executors.getInt(sql, "1"));
        System.out.println("记录行long:" + executors.getLong(sql, "1"));
        System.out.println("记录行float:" + executors.getFloat(sql, "1"));
        System.out.println("记录行double:" + executors.getDouble(sql, "1"));
        System.out.println("记录行string:" + executors.getString(sql, "1"));
    }

    @Test
    public void runQueryMap() {
        String sql = "select * from sequence where id=?";
        Map val = executors.queryForMap(sql, "3");
        System.out.println("结果:" + JSONObject.toJSONString(val));
    }

    @Test
    public void runQueryList() {
        String sql = "select * from sequence";
        List val = executors.queryForList(sql);
        System.out.println("结果:" + JSONObject.toJSONString(val));
    }

    @Test
    public void insert() {
        String sql = "insert into sequence(stub) values(?)";
        String id = executors.insert(sql, "b");
        System.out.println("插入数据:" + id);
    }

    @Test
    public void batchInsert() {
        String sql = "insert into sequence(stub) values(?)";
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] array = null;
        for (int i = 0; i < 1000; i++) {
            array = new Object[1];
            array[0] = "A";
            list.add(array);
        }
        executors.batchInsert(sql, list);
    }

    @Test
    public void delete() {
        String sql = "delete from sequence where id=?";
        executors.delete(sql,"1");
        System.out.println("删除数据!");
    }

    @Test
    public void queryForObject(){
        String sql = "select * from sequence where id=?";
        Sequences sequences=executors.queryForObject(sql,Sequences.class,"2");
        System.out.println("结果:"+JSONObject.toJSONString(sequences));
    }

    @Test
    public void queryForList(){
        String items[]=new String[10];
        StringBuffer buffer=new StringBuffer(300);
        buffer.append("select * from sequence where id in(");
        for(int i=0;i<10;i++){
            items[i]=String.valueOf(i+2);
            buffer.append("?");
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length()-1);
        buffer.append(")");
        List<Sequences> list=this.executors.queryForList(buffer.toString(),Sequences.class,items);
        System.out.println("结果:"+JSONObject.toJSONString(list));
    }


}