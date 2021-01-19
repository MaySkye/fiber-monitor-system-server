package com.rcd.fiber.service;

import com.rcd.fiber.base.VoltdbJdbcBaseDao;
import com.rcd.fiber.service.dto.SignalDTO;
import com.rcd.fiber.service.dto.TelemetryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author:
 * @Date: 2019/6/17 14:52
 */
@Service
@Transactional
public class TelemetryService {
    private final Logger log = LoggerFactory.getLogger(TelemetryService.class);

    //王伟（2020-1-11）： 根据站点名称，查询voltDB内的遥测数据
    public List<TelemetryDTO> getVoltDBMonitorValue(String siteName) {
        VoltdbJdbcBaseDao voltdbJdbcBaseDao = new VoltdbJdbcBaseDao();
        ResultSet set;
        try {
            set = voltdbJdbcBaseDao.executeQuery("select * from TELEMETRY where site_name = '" + siteName);
            List<TelemetryDTO> list = voltdbJdbcBaseDao.populate(set, TelemetryDTO.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            voltdbJdbcBaseDao.closeConnection();
        }
        return null;
    }

    //王伟（2020-1-11）： 根据站点名称，查询voltDB内的遥控数据
    public List<SignalDTO> getVoltDBSignalValue(String siteName) {
        VoltdbJdbcBaseDao voltdbJdbcBaseDao = new VoltdbJdbcBaseDao();
        ResultSet set;
        try {
            set = voltdbJdbcBaseDao.executeQuery("select * from telesignalling where site_name = '" + siteName);
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
}
