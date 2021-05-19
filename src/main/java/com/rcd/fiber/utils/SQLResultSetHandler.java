package com.rcd.fiber.utils;

import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLResultSetHandler {
    /**
     * 返回驼峰命名的结果结合
     * @return
     */
    public static List<JSONObject> getResultList(ResultSet resultSet) throws SQLException {
        List<JSONObject> list = new ArrayList<>();
        while (resultSet.next()) {
            ResultSetMetaData meta = resultSet.getMetaData();
            JSONObject item = new JSONObject();
            for (int i = 0; i < meta.getColumnCount(); i++) {
                item.put(BeanNameUtil.lineToHump(meta.getColumnName(i + 1)), resultSet.getString(i + 1));
            }
            list.add(item);
        }
        return list;
    }
}
