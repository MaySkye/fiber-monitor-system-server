package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.base.start.PublisherHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WSNService {
    @Autowired
    PublisherHandler publisherHandler;

    public JSONObject sendControlInfo(String info){
        publisherHandler.sendControlInfo(info);
        JSONObject res= new JSONObject();
        res.put("status", "success");
        res.put("msg", "发布成功！");
        return res;
    }
}
