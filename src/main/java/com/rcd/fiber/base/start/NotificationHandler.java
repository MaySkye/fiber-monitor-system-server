package com.rcd.fiber.base.start;

import com.rcd.fiber.service.dto.EventInfoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationHandler {
    // 站点名--设备名--属性名
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, EventInfoDTO>>> events = new ConcurrentHashMap<>();

    public synchronized void addNewEvent(EventInfoDTO event) {
        // 获取站点事件表
        ConcurrentHashMap<String, ConcurrentHashMap<String, EventInfoDTO>> eventsOfSite = events.get(event.getSiteName());
        // 如果站点事件表为空
        if (eventsOfSite == null) {
            // 创建站点事件表
            eventsOfSite = new ConcurrentHashMap<>();
            events.put(event.getSiteName(), eventsOfSite);
        }

        // 如果站点事件表不为空
        String deviceName = event.getDeviceName();
        // 获取设备事件表
        ConcurrentHashMap<String, EventInfoDTO> eventsOfDevice = eventsOfSite.get(deviceName);
        // 如果设备事件表为空
        if (eventsOfDevice == null) {
            eventsOfDevice = new ConcurrentHashMap<>();
            eventsOfSite.put(deviceName, eventsOfDevice);
        }

        // 如果设备事件表不为空
        eventsOfDevice.put(event.getDataName(), event);
    }

    public List<EventInfoDTO> getEventsBySite(String siteName) {
        ArrayList result = new ArrayList();
        ConcurrentHashMap<String, ConcurrentHashMap<String, EventInfoDTO>> eventsOfSite = events.get(siteName);
        if (eventsOfSite != null) {
            eventsOfSite.forEach((String deviceName, ConcurrentHashMap<String, EventInfoDTO> eventsOfDevice) -> {
                eventsOfDevice.forEach((String dataName, EventInfoDTO info) -> {
                    result.add(info);
                });
            });
        }
        return result;
    }

}
