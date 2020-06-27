package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.rcd.fiber.base.VoltdbJdbcBaseDao;
import com.rcd.fiber.domain.entity.ServiceFileInfo;
import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.repository.MongoRepository;
import com.rcd.fiber.repository.TelemetryRepository;
import com.rcd.fiber.service.dto.SignalDTO;
import com.rcd.fiber.service.dto.TelemetryDTO;
import com.rcd.fiber.service.dto.TelemetryDTO2;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:
 * @Date: 2019/6/17 14:52
 */
@Service
@Transactional
public class TelemetryService {
    @Autowired
    private VoltdbJdbcBaseDao voltdbJdbcBaseDao;
    private final Logger log = LoggerFactory.getLogger(TelemetryService.class);
    private final TelemetryRepository telemetryRepository;

    public TelemetryService(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    // 返回所有数据
    public List<Telemetry> getAllTelemetry() {
        return telemetryRepository.findAll();
    }

    //根据site_name,device_name,data_name查找数据
    public List<Telemetry> getMonitorValue(String site_name, String device_name, String data_name) {
        return telemetryRepository.findMonitorValue(site_name, device_name, data_name);
    }

    public int updateValue(double detected_value, String site_name, String device_name, String data_name) {
        return telemetryRepository.updateMonitorValue(detected_value, site_name, device_name, data_name);
    }

    public int updateAlarmState(int alarm_state, String site_name, String device_name, String data_name) {
        return telemetryRepository.updateAlarmState(alarm_state, site_name, device_name, data_name);
    }

    public int deleteTelemetry(String site_name){
        return telemetryRepository.deleteTelemetryBySiteNameEquals(site_name);
    }

    // 返回vlotdb内的所有数据
    public List<Telemetry> getVoltdbTelemetry() {
        ResultSet set = voltdbJdbcBaseDao.executeQuery("select * from TELEMETRY");
        try {
            List<Telemetry> list = voltdbJdbcBaseDao.populate(set, TelemetryDTO.class);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseDao.closeConnection();
        }
        return null;
    }

    //赵艺： 根据设备名称和属性名称，查询vlotdb内的遥测数据
    public List<TelemetryDTO2> getVoltdbTelemetryValue(String site_name, String device_name, String data_name) {
        ResultSet set ;
        try {
            set = voltdbJdbcBaseDao.executeQuery("select * from TELEMETRY where site_name = '"+site_name+"' and device_name = '"
                +device_name+"' and data_name = '" +data_name+"'");
            List<TelemetryDTO2> list = voltdbJdbcBaseDao.populate(set, TelemetryDTO2.class);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseDao.closeConnection();
        }
        return null;
    }


    //赵艺： 根据设备名称和属性名称，查询vlotdb内的遥控数据
    public List<SignalDTO> getVoltdbSignalValue(String site_name, String device_name, String data_name) {
        ResultSet set ;
        try {
            set = voltdbJdbcBaseDao.executeQuery("select * from telesignalling where site_name = '"+site_name+"' and device_name = '"
                +device_name+"' and data_name = '" +data_name+"'");
            List<SignalDTO> list = voltdbJdbcBaseDao.populate(set, SignalDTO.class);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseDao.closeConnection();
        }
        return null;
    }

    //王伟：修改监控属性
    public HashMap<String, String> updateDetectedValue(String jsonData) {
        //返回结果
        HashMap<String, String> res = new HashMap<>();
        //解析JSONArray
        JSONArray arr = JSONArray.parseArray(jsonData);
        JSONObject obj = JSONObject.parseObject(arr.getString(0));
        //获取站点名、设备名
        String device_name = obj.getString("device_name");
        String site_name = obj.getString("site_name");
        //错误报告
        JSONObject errReport = new JSONObject();
        //调用dao层修改监控值
        for (int i = 1; i < arr.size(); i++) {
            obj = JSONObject.parseObject(arr.getString(i));
            String data_name = obj.getString("name");
            String detected_value = obj.getString("value");
           /* if (Float.parseFloat(detected_value) > 100) {
                errReport.put(data_name, detected_value + "，超出合理范围！");
                continue;
            }*/
            if (telemetryRepository.updateDetectedValue(detected_value, site_name, device_name, data_name) <= 0) {
                // errReport.put(data_name, detected_value + "，修改失败！");
            }
        }
        //若存在错误信息
        if (errReport.size() > 0) {
            res.put("type", "error");
            res.put("msg", JSONObject.toJSONString(errReport));
            return res;
        }
        res.put("type", "success");
        return res;
    }

    //王伟：获取监控值
    public HashMap<String, String> getDetectedValueByParameters(String jsonData) {
        //待查询的站点名，设备id
        JSONObject obj = JSONObject.parseObject(jsonData);
        //待传送给后端的结果
        HashMap<String, String> res = new HashMap<String, String>();
        List<Telemetry> list = telemetryRepository.getDetectedValueByParameters(obj.getString("site_name"), obj.getString("device_name"));
        if (list.size() == 0) {
            res.put("type", "error");
            res.put("msg", "查询不到源数据！");
            return res;
        }
        //数据字段
        HashMap<String, String> resData = new HashMap<String, String>();
        for (Telemetry item : list) {
            resData.put(item.getDataName(), item.getDetectedValue());
        }
        res.put("data", JSONObject.toJSONString(resData));
        res.put("type", "success");
        return res;
    }

    public void addTelemetry(Telemetry telemetry){
        telemetryRepository.saveAndFlush(telemetry);
    }

}
