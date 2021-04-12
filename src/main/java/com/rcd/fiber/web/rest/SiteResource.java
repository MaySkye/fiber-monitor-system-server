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

import java.util.ArrayList;
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
    private final SiteLineService siteLineservice;
    public SiteResource(SiteService siteservice,SiteLineService siteLineservice) {
        this.siteservice = siteservice;
        this.siteLineservice = siteLineservice;
    }

    @GetMapping("/findall")
    @Timed
    public String getAllSite() {
        List<Site> list = siteservice.getAllSite();
        //list.stream().forEach(x->System.out.println(x));
        String jsonListEmp = getSiteJsonStr(list);
        System.out.println("jsonListEmp:  " + jsonListEmp);
        return jsonListEmp;
        //return ResponseEntity.ok(list);
    }

    @GetMapping("/findsiteinfo/{sitelevel}")
    @Timed
    public String getSiteInfo(@PathVariable(value = "sitelevel") String sitelevel) {
        List<Site> list = siteservice.getAllSite();
        System.out.println("sitelevel: "+sitelevel);
        if(sitelevel.equals("all")){
            return getSiteJsonStr(list);
        }
        else if(sitelevel.equals("1")){
            return getSiteJsonStr(getLevelSite(list,1));
        }
        else if(sitelevel.equals("2")){
            return getSiteJsonStr(getLevelSite(list,2));
        }
        else if(sitelevel.equals("3")){
            return getSiteJsonStr(getLevelSite(list,3));
        }else {
            return null;
        }
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


    @GetMapping("/delete/{sitename}")
    @Timed
    public int deleteSite(@PathVariable(value = "sitename") String sitename) {
        System.out.println("delete-sitename: "+sitename);
        List<Site> list = siteservice.getAllSite();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getSiteName().equals(sitename)){
                siteservice.deleteSite(list.get(i));
                //判断是否存在与该站点有关的链路
                List<SiteLine> siteLines = siteLineservice.getAllSiteLine();
                for(int j=0;j<siteLines.size();j++){
                    String point1 = siteLines.get(j).getPoint1();
                    String point2 = siteLines.get(j).getPoint2();
                    if(point1.equals(sitename)||point2.equals(sitename)){
                        //删除该链路
                        siteLineservice.deleteSiteLine(siteLines.get(j));
                    }
                }
                return 0;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/updatesite",method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public int updateSitePost(@RequestBody String site_json){
        System.out.println("updateSitePost:  "+site_json);
        Site site = new Site();
        JSONObject obj = JSONObject.parseObject(site_json);
        site.setSiteId(obj.getLong("site_id"));
        site.setSiteName(obj.getString("site_name"));
        site.setSiteLevel(obj.getLong("site_level"));
        site.setSiteType(obj.getString("site_type"));
        site.setSiteLocalx(obj.getDouble("site_localx"));
        site.setSiteLocaly(obj.getDouble("site_localy"));
        site.setSiteInfo(obj.getString("site_info"));
        site.setSiteAddress(obj.getString("site_address"));
       return siteservice.updateSite(site);
    }

    public  static String getSiteJsonStr(List<Site> items) throws JSONException {
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
            jsonObject.put("devcount", info.getDevcount());
            /*int devcount=(int)(5+Math.random()*(10-5+1));
            jsonObject.put("devcount", devcount);*/
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

    private  List<Site> getLevelSite(List<Site> items,int level){
        List<Site> resList = new ArrayList<>();
        for(int i=0;i<items.size();i++){
            if(items.get(i).getSiteLevel()==level){
                resList.add(items.get(i));
            }
        }
        return resList;
    }
}
