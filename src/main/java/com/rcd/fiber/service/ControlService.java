package com.rcd.fiber.service;

import com.rcd.fiber.domain.entity.Control;
import com.rcd.fiber.repository.ControlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 10:27 2019/9/25
 * @Modify By:
 */
@Service
@Transactional
public class ControlService {
    private final Logger log = LoggerFactory.getLogger(ControlService.class);

    private final ControlRepository dao;

    public ControlService(ControlRepository dao) {
        this.dao = dao;
    }

    // 返回所有数据
    public List<Control> getAllControl() {
        return dao.findAll();
    }

    public int updateValue(int value,String site_name,String device_name,String data_name) {
        return dao.updateControlValue(value,site_name,device_name,data_name);
    }
}
