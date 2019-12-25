package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.rcd.fiber.domain.entity.ServiceFileInfo;
import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.service.TelemetryService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/6/17 14:58
 */
@RestController
@RequestMapping("/telemetry")
@CrossOrigin // 防止跨域
public class TelemetryResource {
    private final Logger logger = LoggerFactory.getLogger(TelemetryResource.class);
    private final TelemetryService service;

    public TelemetryResource(TelemetryService service) {
        this.service = service;
    }

    @GetMapping("/findall")
    @Timed
    public ResponseEntity<List<Telemetry>> getAllTelemetry() {
        List<Telemetry> list = service.getAllTelemetry();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/findMonitorValue/{site_name}/{monitorInfoSet}")
    @Timed
    public String getMonitorValue(@PathVariable(value = "site_name") String site_name,
                                  @PathVariable(value = "monitorInfoSet") String monitorInfoSet
    ) {
        System.out.println("site_name: " + site_name);
        System.out.println("monitorInfoSet: " + monitorInfoSet);
        List<Telemetry> list = new ArrayList<>();
        String[] strings = monitorInfoSet.split("::");
        for (String str : strings) {
            String[] s = str.split(":");
            String device_name = s[0];
            String data_name = s[1];
            System.out.println("device_name: " + device_name);
            System.out.println("data_name: " + data_name);

            //持续更新数据的操作
            double val = Math.random() * 10;
            val = (double) Math.round(val * 100) / 100;
            service.updateValue(val, site_name, device_name, data_name);

            List<Telemetry> telemetrys = service.getMonitorValue(site_name, device_name, data_name);
            if (telemetrys.size() >= 1) {
                list.add(telemetrys.get(0));
            }
        }
        String jsonListEmp = TelemetryToJson(list);
        System.out.println("jsonListEmp:  " + jsonListEmp);
        return jsonListEmp;
    }

    public static String TelemetryToJson(List<Telemetry> items) throws JSONException {
        if (items == null)
            return "";
        JSONArray array = new JSONArray();
        JSONObject jsonObject = null;
        Telemetry info = null;
        for (int i = 0; i < items.size(); i++) {
            info = items.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("site_name", info.getSiteName());
            jsonObject.put("device_name", info.getDeviceName());
            jsonObject.put("data_name", info.getDataName());
            jsonObject.put("detected_value", info.getDetectedValue());
            jsonObject.put("alarm_state", info.getAlarmState());
            array.add(jsonObject);
        }
        return array.toString();
    }

    @GetMapping("/find-voltdb-all")
    @Timed
    public ResponseEntity<List<Telemetry>> getVoltdbTelemetry() {
        List<Telemetry> list = service.getVoltdbTelemetry();
        return ResponseEntity.ok(list);
    }


    //王伟：更新检测值
    @RequestMapping(value = "/updateDetectedValue", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public HashMap<String, String> updateDetectedValue(@RequestBody String jsonData) throws UnsupportedEncodingException {
        return service.updateDetectedValue(jsonData);
    }

    //王伟：获取检测值
    @RequestMapping(value = "/getDetectedValueByParameters", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public HashMap<String, String> getDetectedValueByParameters(@RequestBody String jsonData) {
        System.out.println("jsonData:"+jsonData);
        System.out.println("data:"+service.getDetectedValueByParameters(jsonData).get("data"));
        return service.getDetectedValueByParameters(jsonData);
    }

    //王伟：获取service（latest）文件信息
    @RequestMapping("/getLastestServiceFileInfo")
    @ResponseBody
    @Timed
    public ServiceFileInfo getLastestServiceFileInfo(@RequestParam("site_name") String site_name,
                                                     @RequestParam("site_level") String site_level) {
        return service.getLastestServiceFileInfo(site_name, site_level);
    }

    //王伟：下载service（latest）文件
    @RequestMapping("/getLastestServiceFile")
    @ResponseBody
    public void getLastestServiceFile(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("site_name") String site_name,
                                      @RequestParam("site_level") String site_level) {
        System.out.println("site_name:"+site_name+"  site_level:"+site_level);
        service.getLatestServiceFile(request, response, site_name, site_level);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> upload(MultipartFile multipartFile,
                                          HttpServletRequest request) {
        Document metadata = new Document()
            .append("site_name", request.getParameter("site_name"))
            .append("site_level", request.getParameter("site_level"))
            .append("latest", request.getParameter("latest"))
            .append("filename",request.getParameter("filename"));
        return service.uploadServiceFile(multipartFile, metadata);
    }

    @RequestMapping(value = "/addtelemetry",method = RequestMethod.POST ,produces = "application/json;charset=utf-8")
    @ResponseBody
    @Timed
    public void addTelemetryPost(@RequestBody String telemetry_info) {
        System.out.println("telemetry_info:" + telemetry_info);

        try {
            String newstr = URLDecoder.decode(telemetry_info, "utf-8");
            System.out.println("newstr:" + newstr);
            JSONArray res_arr = JSON.parseArray(newstr);
            String site_name = res_arr.getJSONObject(0).getString("site_name");
            String telemetry_json = res_arr.getJSONObject(0).getString("telemetry_json");
            //先根据站点名称把相关数据删除
            service.deleteTelemetry(site_name);
            //添加
            JSONArray arr = JSON.parseArray(telemetry_json);
            for (int i = 0; i < arr.size(); i++) {
                JSONObject json = arr.getJSONObject(i);
                Telemetry telemetry = new Telemetry();
                telemetry.setSiteName((String) json.get("site_name"));
                telemetry.setDeviceName((String) json.get("device_name"));
                telemetry.setDataName((String) json.get("data_name"));
                telemetry.setBase((String) json.get("base"));
                if (!json.get("offset").equals("")) {
                    telemetry.setOffset(Integer.valueOf((String) json.get("offset")));
                }
                if (!json.get("ratio").equals("")) {
                    telemetry.setRatio(Integer.valueOf((String) json.get("ratio")));
                }
                telemetry.setUpperLimit((String) json.get("upper_limit"));
                telemetry.setLowerLimit((String) json.get("lower_limit"));
                telemetry.setAlarmUpperLimit((String) json.get("alarm_lower_limit"));
                telemetry.setAlarmLowerLimit((String) json.get("alarm_lower_limit"));
                if (!json.get("state").equals("")) {
                    telemetry.setState(Integer.valueOf((String) json.get("state")));
                }
                if (!json.get("blocked").equals("")) {
                    telemetry.setBlocked(Boolean.valueOf((String) json.get("blocked")));
                }
                telemetry.setAlarmState((String) json.get("alarm_state"));
                service.addTelemetry(telemetry);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
