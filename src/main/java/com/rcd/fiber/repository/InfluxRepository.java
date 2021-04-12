package com.rcd.fiber.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.annotation.IJsonQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluxRepository {
    @IJsonQuery("select * from telemetry where device_name = #{device_name} and data_name = #{data_name} and site_name = #{site_name} order by time desc limit 1")
    public JSONObject getTelemetryInfo(JSONObject params);

    @IJsonQuery("select * from telesignalling where device_name = #{device_name} and data_name = #{data_name} and site_name = #{site_name} order by time desc limit 1")
    public JSONObject getSignalInfo(JSONObject params);
}
