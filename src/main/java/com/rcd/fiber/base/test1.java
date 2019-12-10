package com.rcd.fiber.base;

import com.rcd.fiber.domain.entity.Telemetry;
import com.rcd.fiber.service.dto.TelemetryDTO;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/6/18 13:39
 */
public class test1 {
    // 测试jdbc的使用
    public static void main(String[] args) {
        VoltdbJdbcBaseDao dao = new VoltdbJdbcBaseDao();
        ResultSet set = dao.executeQuery("select * from TELEMETRY");
        try {
            List list = dao.populate(set, TelemetryDTO.class);
            TelemetryDTO b = (TelemetryDTO) list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            dao.closeConnection();
        }


    }
}
