package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.SiteLine;
import com.rcd.fiber.service.SiteLineService;
import com.rcd.fiber.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rcd.fiber.web.rest.SiteResource.SiteToJson;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 16:07 2019/12/13
 * @Modify By:
 */
@RestController
@RequestMapping("/siteline")
@CrossOrigin // 防止跨域
public class SiteLineResource {
    private final Logger logger = LoggerFactory.getLogger(SiteLineResource.class);
    private final SiteLineService siteLineservice;
    private final SiteService siteservice;

    public SiteLineResource(SiteLineService siteLineservice, SiteService siteservice) {
        this.siteLineservice = siteLineservice;
        this.siteservice = siteservice;
    }

    @GetMapping("/findall")
    @Timed
    public String getAllSiteLine() {

        List<SiteLine> list = siteLineservice.getAllSiteLine();
        //list.stream().forEach(x->System.out.println(x));
        String jsonListEmp = SiteLineToJson(list);
        System.out.println("jsonListEmp:  " + jsonListEmp);
        return jsonListEmp;
        //return ResponseEntity.ok(list);
    }

    @GetMapping("/findPaintInfo")
    @Timed
    public String getAllPaintInfo() {
        List<Site> sitelist = siteservice.getAllSite();
        List<SiteLine> list = siteLineservice.getAllSiteLine();
        String siteInfo=SiteToJson(sitelist);;
        String siteLinkInfo=SiteLineToJson(list);
        String paintInfojson=PaintInfoToJson(siteInfo,siteLinkInfo);
        return paintInfojson;
    }

    public static String SiteLineToJson(List<SiteLine> items) throws JSONException {
        if (items == null)
            return "";
        JSONArray array = new JSONArray();
        JSONObject jsonObject = null;
        SiteLine info = null;
        //System.out.println("size:  " + items.size());
        for (int i = 0; i < items.size(); i++) {
            info = items.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("line_id", info.getLineId());
            jsonObject.put("point1", info.getPoint1());
            jsonObject.put("point2", info.getPoint2());
            jsonObject.put("line_name", info.getLineName());
            jsonObject.put("line_type", info.getLineType());
            jsonObject.put("line_info", info.getLineInfo());
            jsonObject.put("length", info.getLen());
            //System.out.println("jsonObject:  " + jsonObject.toString());
            array.add(jsonObject);
        }
        return array.toString();
    }

    public static String PaintInfoToJson(String siteInfo,String siteLinkInfo) throws JSONException {
        if (siteInfo == null||siteLinkInfo==null)
            return "";
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();;
        jsonObject.put("siteInfo",siteInfo);
        jsonObject.put("siteLinkInfo",siteLinkInfo);
        array.add(jsonObject);
        return array.toString();
    }
}
