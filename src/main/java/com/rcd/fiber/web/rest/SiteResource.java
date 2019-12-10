package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.service.SiteService;
import com.rcd.fiber.service.TelemetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 20:14 2019/11/26
 * @Modify By:
 */
@RestController
@RequestMapping("/site")
@CrossOrigin // 防止跨域
public class SiteResource {
    private final Logger logger = LoggerFactory.getLogger(SiteResource.class);
    private final SiteService siteservice;

    public SiteResource(SiteService siteservice) {
        this.siteservice = siteservice;
    }

    @GetMapping("/findall")
    @Timed
    public String getAllSite() {
        List<Site> list = siteservice.getAllSite();
        //list.stream().forEach(x->System.out.println(x));
        String jsonListEmp = SiteToJson(list);
        System.out.println("jsonListEmp:  " + jsonListEmp);
        return jsonListEmp;
        //return ResponseEntity.ok(list);
    }

    public static String SiteToJson(List<Site> items) throws JSONException {
        if (items == null)
            return "";
        JSONArray array = new JSONArray();
        JSONObject jsonObject = null;
        Site info = null;
        //System.out.println("size:  " + items.size());
        for (int i = 0; i < items.size(); i++) {
            info = items.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("site_id", info.getSiteId());
            jsonObject.put("site_level", info.getSiteLevel());
            jsonObject.put("site_localx", info.getSiteLocalx());
            jsonObject.put("site_localy", info.getSiteLocaly());
            jsonObject.put("site_name", info.getSiteName());
            jsonObject.put("site_info", info.getSiteInfo());
            jsonObject.put("site_type", info.getSiteType());
            jsonObject.put("site_address", info.getSiteAddress());
            jsonObject.put("connect", info.getConnect());
            //System.out.println("jsonObject:  " + jsonObject.toString());
            array.add(jsonObject);
        }
        return array.toString();
    }
}
