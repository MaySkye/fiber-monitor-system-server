package com.rcd.fiber.utils;

import com.alibaba.fastjson.JSONObject;

import java.text.MessageFormat;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlStringProcessor {

    public static String processSQL(String sql, JSONObject json) {
        WWLogger.debug("待处理的sql：", sql, "json参数：", json);
        Set<String> keys = json.keySet();
        for (String key : keys) {
            String value = json.getString(key);
            sql = sql.replaceAll("#\\{" + key + "\\}", "'" + value + "'");
            sql = sql.replaceAll("\\$\\{" + key + "\\}", value);
        }
        WWLogger.debug(MessageFormat.format("带参数的查询：{0}", sql));
        return sql;
    }
}
