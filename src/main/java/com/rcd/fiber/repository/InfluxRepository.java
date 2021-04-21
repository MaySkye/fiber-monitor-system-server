package com.rcd.fiber.repository;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.annotation.IJsonGroupQuery;
import org.springframework.stereotype.Component;

@Component
public class InfluxRepository {
    @IJsonGroupQuery("select * from telemetry where device_name = #{device_name} and site_name = #{site_name} group by data_name order by time desc limit 1")
    public JSONObject getTelemetryInfo(JSONObject params) {
        return null;
    }


    @IJsonGroupQuery("select * from telesignalling where device_name = #{device_name} and site_name = #{site_name} group by data_name order by time desc limit 1")
    public JSONObject getSignalInfo(JSONObject params) {
        return null;
    }
}
