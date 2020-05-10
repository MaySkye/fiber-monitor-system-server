package com.rcd.fiber.service.dto;

public class SignalDTO {

    /*
    * SITE_NAME  DEVICE_NAME  DATA_NAME    TIMESTAMP  DETECTED_VALUE  STATE  BLOCKED  NORMAL_VALUE  WARNING_VALUE  ALARM_VALUE

     * */
    private Integer id;

    private String SiteName;

    private String DeviceName;

    private String DataName;

    private String Timestamp;

    private Double DetectedValue;

    private Integer State;

    private Integer Blocked;

    private Double NormalValue;

    private Double WarningValue;

    private Double AlarmValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public Double getDetectedValue() {
        return DetectedValue;
    }

    public void setDetectedValue(Double detectedValue) {
        DetectedValue = detectedValue;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public Integer getBlocked() {
        return Blocked;
    }

    public void setBlocked(Integer blocked) {
        Blocked = blocked;
    }

    public Double getNormalValue() {
        return NormalValue;
    }

    public void setNormalValue(Double normalValue) {
        NormalValue = normalValue;
    }

    public Double getWarningValue() {
        return WarningValue;
    }

    public void setWarningValue(Double warningValue) {
        WarningValue = warningValue;
    }

    public Double getAlarmValue() {
        return AlarmValue;
    }

    public void setAlarmValue(Double alarmValue) {
        AlarmValue = alarmValue;
    }
}
