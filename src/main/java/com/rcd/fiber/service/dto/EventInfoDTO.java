package com.rcd.fiber.service.dto;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 0:02 2020/5/16
 * @Modify By:
 */
public class EventInfoDTO {

    private String SiteName;

    private String DeviceName;

    private String DataName;

    private String EventType;

    private String Value;

    public EventInfoDTO() {
    }

    public EventInfoDTO(String siteName, String deviceName, String dataName, String eventType, String value) {
        SiteName = siteName;
        DeviceName = deviceName;
        DataName = dataName;
        EventType = eventType;
        Value = value;
    }

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getDataName() {
        return DataName;
    }

    public void setDataName(String dataName) {
        DataName = dataName;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "EventInfoDTO{" +
            "SiteName='" + SiteName + '\'' +
            ", DeviceName='" + DeviceName + '\'' +
            ", DataName='" + DataName + '\'' +
            ", EventType='" + EventType + '\'' +
            ", Value='" + Value + '\'' +
            '}';
    }
}
