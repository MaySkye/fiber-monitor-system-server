package com.rcd.fiber.service.dto;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 0:02 2020/5/16
 * @Modify By:
 */
public class EventInfoDTO {

    private String siteName;

    private String deviceName;

    private String dataName;

    private String eventType;

    private String eventLevel;

    private String value;

    private String timestamp;

    public EventInfoDTO() {
    }

    public EventInfoDTO(String siteName, String deviceName, String dataName, String eventType, String eventLevel, String value, String timestamp) {
        this.siteName = siteName;
        this.deviceName = deviceName;
        this.dataName = dataName;
        this.eventType = eventType;
        this.eventLevel = eventLevel;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
