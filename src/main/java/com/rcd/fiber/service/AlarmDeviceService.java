package com.rcd.fiber.service;

import com.rcd.fiber.domain.entity.AlarmDevice;
import com.rcd.fiber.repository.AlarmDeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/7/19 16:26
 */
@Service
public class AlarmDeviceService {
    Logger logger = LoggerFactory.getLogger(AlarmDeviceService.class);

    private final AlarmDeviceRepository dao;

    public AlarmDeviceService(AlarmDeviceRepository dao) {
        this.dao = dao;
    }

    // 获取预警的信息然后处理,查询是否有status 的数据
    public List<AlarmDevice> getAlarmDevice(Integer status) {
        return dao.findAllByStatus(status);
    }

    public int updataData() {
        return dao.updateData();
    }
}

