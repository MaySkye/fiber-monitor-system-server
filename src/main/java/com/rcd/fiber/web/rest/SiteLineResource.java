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
import org.springframework.web.bind.annotation.*;

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

        List<SiteLine> l = siteLineservice.getAllSiteLine();
        for (int i = 0; i < l.size(); i++) {
            SiteLine sl = l.get(i);
            //插入新数据
            double stable = Math.random() * 10;
            stable = (double) Math.round(stable * 100) / 100;
            double transspeed = Math.random() * 10;
            transspeed = (double) Math.round(transspeed * 100) / 100;
            String point1=sl.getPoint1();
            String point2=sl.getPoint2();
            siteLineservice.updateValues(point1+"-"+point2,stable,transspeed,"正常",point1,point2);
        }

        //读取返回数据
        List<SiteLine> list = siteLineservice.getAllSiteLine();
        String jsonListEmp = SiteLineToJson(list);
        System.out.println("jsonListEmp:  " + jsonListEmp);

        return jsonListEmp;
        //return ResponseEntity.ok(list);
    }

    @GetMapping("/findPaintInfo")
    @Timed
    public String getAllPaintInfo() {
        List<SiteLine> l = siteLineservice.getAllSiteLine();
        for (int i = 0; i < l.size(); i++) {
            SiteLine sl = l.get(i);
            //插入新数据
            double stable = Math.random() * 10;
            stable = (double) Math.round(stable * 100) / 100;
            double transspeed = Math.random() * 10;
            transspeed = (double) Math.round(transspeed * 100) / 100;
            String point1=sl.getPoint1();
            String point2=sl.getPoint2();
            siteLineservice.updateValues(point1+"-"+point2,stable,transspeed,"正常",point1,point2);
        }

        List<Site> sitelist = siteservice.getAllSite();
        List<SiteLine> list = siteLineservice.getAllSiteLine();
        String siteInfo=SiteToJson(sitelist);;
        String siteLinkInfo=SiteLineToJson(list);
        String paintInfojson=PaintInfoToJson(siteInfo,siteLinkInfo);
        return paintInfojson;
    }

    @RequestMapping(value = "/addline",method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public void addSitePost(@RequestBody String line_json) {
        line_json=line_json.substring(1,line_json.length()-1);
        System.out.println("line_json:"+line_json);
        JSONObject json = JSONObject.parseObject(line_json);
        SiteLine siteline=new SiteLine();
        siteline.setPoint1((String) json.get("val_point1"));
        siteline.setPoint2((String) json.get("val_point2"));
        siteline.setLineName((String) json.get("val_linename"));
        siteline.setLineType((String) json.get("val_linetype"));
        siteline.setLineInfo((String) json.get("val_lineinfo"));
        if(!json.get("val_linelen").equals("")){
            siteline.setLen(Double.valueOf((String) json.get("val_linelen")));
        }
        //数据库中添加一条链路，并立即刷新缓存
        siteLineservice.addsiteLine(siteline);
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
            jsonObject.put("stable", info.getStable());
            jsonObject.put("transspeed", info.getTransspeed());
            jsonObject.put("state", info.getState());
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
