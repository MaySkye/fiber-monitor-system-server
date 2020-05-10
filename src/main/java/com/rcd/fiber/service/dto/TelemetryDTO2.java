package com.rcd.fiber.service.dto;


/**
 * @Author: HUHU
 * @Date: 2019/6/17 13:59
 */

public class TelemetryDTO2 {


    private Integer id;

    private String SiteName;

    private String DeviceName;

    private String DataName;

    private String Timestamp;

    private Double DetectedValue;

    private String Base;

    private Integer Offset;

    private Integer Ratio;

    private Double UpperLimit;

    private Double LowerLimit;

    private Double AlarmUpperLimit;

    private Double AlarmLowerLimit;

    private Integer State;

    private Integer Blocked;

    private String AlarmState;

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
        this.Timestamp = timestamp;
    }

    public Double getDetectedValue() {
        return DetectedValue;
    }

    public void setDetectedValue(Double detectedValue) {
        DetectedValue = detectedValue;
    }

    public String getBase() {
        return Base;
    }

    public void setBase(String base) {
        Base = base;
    }

    public Integer getOffset() {
        return Offset;
    }

    public void setOffset(Integer offset) {
        Offset = offset;
    }

    public Integer getRatio() {
        return Ratio;
    }

    public void setRatio(Integer ratio) {
        Ratio = ratio;
    }

    public Double getUpperLimit() {
        return UpperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        UpperLimit = upperLimit;
    }

    public Double getLowerLimit() {
        return LowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        LowerLimit = lowerLimit;
    }

    public Double getAlarmUpperLimit() {
        return AlarmUpperLimit;
    }

    public void setAlarmUpperLimit(Double alarmUpperLimit) {
        AlarmUpperLimit = alarmUpperLimit;
    }

    public Double getAlarmLowerLimit() {
        return AlarmLowerLimit;
    }

    public void setAlarmLowerLimit(Double alarmLowerLimit) {
        AlarmLowerLimit = alarmLowerLimit;
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

    public String getAlarmState() {
        return AlarmState;
    }

    public void setAlarmState(String alarmState) {
        AlarmState = alarmState;
    }
}
