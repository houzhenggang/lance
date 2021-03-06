package com.flickr.db.pool;

import com.flickr.db.pojo.DBParams;
import com.flickr.db.pojo.Page;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-27 17:46
 * To change this template use File | Settings | File Templates.
 */
public interface IDBExecutors {


    /*
    * 执行存储过程
    * */
    public List executeProc(String sql, Object... params);

    public <T> List<T> executeProc(String sql, Class<T> clazz, Object... args);

    /*
    *执行sql
    * */
    public boolean execute(String sql);

    /*
    *批量执行sql
    * */
    public void executeBatch(List<String> sqls);

    /*
    *查询map
    * */
    public Map queryForMap(String sql, Object... params);

    /*
    * 查询list
    * */
    public List queryForList(String sql, Object... params);

    public <T> T queryForObject(String sql, Class<T> clazz, Object... args);

    public <T> List<T> queryForList(String sql, Class<T> clazz, Object... args);

    public int getInt(String sql, Object... params);

    public long getLong(String sql, Object... params);

    public float getFloat(String sql, Object... params);

    public double getDouble(String sql, Object... params);

    public String getString(String sql, Object... params);

    /*
    * delete
    * */
    public boolean delete(String sql, Object... params);

    /*
    * update
    * */
    public void update(String sql, Object... params);

    /*
    * insert
    * */
    public String insert(String sql, Object... params);

    /*
    *批量插入
    * */
    public void insertBatch(String sql, List<Object[]> params);

    public void insert(String tableName, Map params);

    /*
    *批量执行，支持不同sql
    * **/
    public void executeDiff(List<DBParams> list);


    /**
     * 分页查询
     *
     * @param sql      查询的sql语句
     * @param pageNo   查询的起始页
     * @param pageSize 每页数量
     * @param args     对应sql语句中的参数值
     * @return 分页结果集对象
     */
    public Page queryForMapPage(String sql, String countSql, int pageNo, int pageSize, Object... args);

    public Page queryForObjectPage(String sql, String countSql, int pageNo, int pageSize, Class clazz, Object... args);


}
