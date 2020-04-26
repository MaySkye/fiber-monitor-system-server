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
import org.springframework.web.bind.annotation.*;

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



    @RequestMapping(value = "/addsite",method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public void addSitePost(@RequestBody String site_json) {
        site_json=site_json.substring(1,site_json.length()-1);
        System.out.println("site_json:"+site_json);
        JSONObject json = JSONObject.parseObject(site_json);
        Site site=new Site();
        site.setSiteName((String) json.get("val_sitename"));
        site.setSiteType((String) json.get("val_sitetype"));
        site.setSiteAddress((String) json.get("val_siteaddress"));
        site.setSiteInfo((String) json.get("val_siteinfo"));
        site.setSiteLevel(Long.valueOf((String) json.get("val_sitelevel")));
        site.setSiteLocalx(Double.valueOf((String) json.get("val_lng")));
        site.setSiteLocaly(Double.valueOf((String) json.get("val_lat")));
        //数据库中添加一个站点，并立即刷新缓存
        siteservice.addSite(site);
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

            int devcount=(int)(5+Math.random()*(10-5+1));
            jsonObject.put("devcount", devcount);
            if(info.getSiteName().equals("郑州")){
                jsonObject.put("state","故障" );
            }
            else if(info.getSiteName().equals("天津")){
                jsonObject.put("state","异常" );
            }
            else if(info.getSiteName().equals("温州")){
                jsonObject.put("state","异常" );
            }
            else if(info.getSiteName().equals("襄樊")){
                jsonObject.put("state","异常" );
            }
            else if(info.getSiteName().equals("昆明")){
                jsonObject.put("state","故障" );
            }
            else{jsonObject.put("state","正常" );}
            //System.out.println("jsonObject:  " + jsonObject.toString());
            array.add(jsonObject);
        }
        return array.toString();
    }
}
