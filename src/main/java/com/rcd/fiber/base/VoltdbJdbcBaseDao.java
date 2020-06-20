package com.rcd.fiber.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class VoltdbJdbcBaseDao {
    protected Connection conn = null; // 链接
    protected PreparedStatement ps = null; //
    protected ResultSet rs = null;
    @Value("${voltdb.url}")
    private String dbUrl;

    //private String dbUrl = "jdbc:voltdb://192.168.99.12:21212";
    private String url = "jdbc:voltdb://192.168.253.101:21212";

    /**
     * 获取连接
     *
     * @return
     */
    public Connection getConnection() {

        //测试环境
        String userName = "";
        String password = "";

        try {
            Class.forName("org.voltdb.jdbc.Driver");
            System.out.println("voltdb-url: "+url);
            conn = DriverManager.getConnection(url);
            System.out.println("conn: "+conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 查询方法
     *
     * @param sql
     * @param params
     * @return
     */
    public ResultSet executeQuery(String sql, Object... params) {
        //获取连接
        this.conn = getConnection();
        try {
            if (params != null) {
                ps = conn.prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long count(String sql, Object... params) {
        //获取连接
        this.conn = getConnection();
        try {
            if (params != null) {
                ps = conn.prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            rs.next();
            int row = rs.getInt(1);
            Long count = new Long((long) row);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int executeUpdate(String sql, Object... params) {
        //受影响行数
        int updateNum = 0;
        //获取连接
        this.conn = getConnection();
        try {
            //在获取到连接后先设置自动提交为fales
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            updateNum = ps.executeUpdate();
            //然后在sql执行之后手动提交
            conn.commit();
            //再把自动提交改回来
            conn.setAutoCommit(true);
            return updateNum;
        } catch (SQLException e) {
            e.printStackTrace();
            return updateNum;
        } finally {
            closeConnection();
        }
    }

    /**
     * 关闭连接
     */
    public void closeConnection() {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
     * 将rs结果转换成对象列表
     * @param rs jdbc结果集
     * @param clazz 对象的映射类
     * return 封装了对象的结果列表
     */
    public List populate(ResultSet rs, Class clazz) throws SQLException, InstantiationException, IllegalAccessException {
        //结果集的元素对象
        ResultSetMetaData rsmd = rs.getMetaData();
        //获取结果集的元素个数
        int colCount = rsmd.getColumnCount();
//         System.out.println("#");
//         for(int i = 1;i<=colCount;i++){
//             System.out.println(rsmd.getColumnName(i));
//             System.out.println(rsmd.getColumnClassName(i));
//             System.out.println("#");
//         }
        //返回结果的列表集合
        List list = new ArrayList();
        //业务对象的属性数组
        Field[] fields = clazz.getDeclaredFields();
        while (rs.next()) {//对每一条记录进行操作
            Object obj = clazz.newInstance();//构造业务对象实体
            //将每一个字段取出进行赋值
            for (int i = 1; i <= colCount; i++) {
                Object value = rs.getObject(i);
                //寻找该列对应的对象属性
                for (int j = 0; j < fields.length; j++) {
                    Field f = fields[j];
                    //如果匹配进行赋值
                    if (f.getName().equalsIgnoreCase(rsmd.getColumnName(i)) || f.getName().equalsIgnoreCase(rsmd.getColumnName(i).replace("_", "")) ) {
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);
                        System.out.println("f.getName(): "+f.getName()+" value:"+value);
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }
}
