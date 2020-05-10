package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.rcd.fiber.annotation.CheckPermission;
import com.rcd.fiber.domain.entity.ServiceFileInfo;
import com.rcd.fiber.domain.entity.Site;
import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.service.TelemetryService;
import com.rcd.fiber.service.dto.SignalDTO;
import com.rcd.fiber.service.dto.TelemetryDTO2;
import com.rcd.fiber.web.rest.auth.Check;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:
 * @Date: 2019/6/17 14:58
 */
@RestController
@RequestMapping("/telemetry")
@CrossOrigin(origins = "*") // 跨域
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
            double state=0;
            if(val>9){
                state=1;
            }
            service.updateValue(val, site_name, device_name, data_name);

            List<Telemetry> telemetrys = service.getMonitorValue(site_name, device_name, data_name);
            if (telemetrys.size() >= 1) {
                list.add(telemetrys.get(0));
            }
        }
        String jsonListEmp = TelemetryToJson(list);
        System.out.println("jsonListEmp:  " + jsonListEmp);
    /*    String sss="<resource>\n" +
            "\t<site_name>西安</site_name>\n" +
            "\t<alarm_type>故障</alarm_type>\n" +
            "\t<user>admin</user>\n" +
            "\t<timestamp>2020-02-02 12:02:02</timestamp>\n" +
            "\t<info>null</info>\n" +
            "</resource>\n" +
            "<alarm>\n" +
            "\t<device_name>拍频比对设备设备A1.2</device_name>\n" +
            "\t<data_name>光频_频率稳定度A1.2</data_name>\n" +
            "\t<value>9.51</value>\n" +
            "\t<alarm_info>null</alarm_info>\n" +
            "</alarm>";
        System.out.println("告警信息：\n"+sss);*/
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

    public static String getListJson(List<TelemetryDTO2> items1,List<SignalDTO> items2) throws JSONException {
        if (items1 == null && items2 == null)
            return "";
        JSONArray array = new JSONArray();
        JSONObject jsonObject = null;
        TelemetryDTO2 info1 = null;
        SignalDTO  info2 = null;

        if(items1!=null){
            for (int i = 0; i < items1.size(); i++) {
                info1 = items1.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("site_name", info1.getSiteName());
                jsonObject.put("device_name", info1.getDeviceName());
                jsonObject.put("data_name", info1.getDataName());
                jsonObject.put("detected_value", info1.getDetectedValue());
                jsonObject.put("alarm_state", info1.getAlarmState());
                array.add(jsonObject);
            }
        }
        if(items2!=null){
            for (int i = 0; i < items2.size(); i++) {
                info2 = items2.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("site_name", info2.getSiteName());
                jsonObject.put("device_name", info2.getDeviceName());
                jsonObject.put("data_name", info2.getDataName());
                jsonObject.put("detected_value", info2.getDetectedValue());
                array.add(jsonObject);
            }
        }

        return array.toString();
    }

    @GetMapping("/find-voltdb-all")
    @Timed
    public ResponseEntity<List<Telemetry>> getVoltdbTelemetry() {
        List<Telemetry> list = service.getVoltdbTelemetry();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/findVoltdbMonitorValue/{site_name}/{monitorInfoSet}")
    @Timed
    public String getVoltdbMonitorValue(@PathVariable(value = "site_name") String site_name,
                                  @PathVariable(value = "monitorInfoSet") String monitorInfoSet)
    {
        System.out.println("site_name: " + site_name);
        System.out.println("monitorInfoSet: " + monitorInfoSet);

        List<TelemetryDTO2> list1 = new ArrayList<>();
        List<SignalDTO> list2 = new ArrayList<>();
        String[] strings = monitorInfoSet.split("::");
        for (String str : strings) {
            String[] s = str.split(":");
            String device_name = s[0];
            String data_name = s[1];
            System.out.println("device_name: " + device_name);
            System.out.println("data_name: " + data_name);

            if(data_name.equals("锁定状态")||data_name.equals("运行状态")){
                List<SignalDTO> signals = service.getVoltdbSignalValue(site_name, device_name, data_name);
                if (signals.size() >= 1) {
                    list2.add(signals.get(0));
                }
            }else{
                List<TelemetryDTO2> telemetrys = service.getVoltdbTelemetryValue(site_name, device_name, data_name);
                if (telemetrys.size() >= 1) {
                    list1.add(telemetrys.get(0));
                }
            }
        }
        String jsonListEmp = getListJson(list1,list2);
        System.out.println("jsonListEmp:  " + jsonListEmp);
        return jsonListEmp;
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
