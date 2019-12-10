package com.rcd.fiber.domain.entity;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @Author: HUHU
 * @Date: 2019/6/17 13:59
 * 遥测数据dao
 */
@Entity
@Table(name = "telemetry")
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 25)
    @Column(name = "site_name", length = 25)
    private String SiteName;
    @Size(max = 255)
    @Column(name = "device_name")
    private String DeviceName;
    @Size(max = 255)
    @Column(name = "data_name")
    private String DataName;
    @Size(max = 50)
    @Column(name = "timestamp")
    private String Timestamp;
    @Size(max = 100)
    @Column(name = "detected_value")
    private String DetectedValue;
    @Size(max = 255)
    @Column(name = "base")
    private String Base;
    @Column(name = "offset")
    private Integer Offset;
    @Column(name = "ratio")
    private Integer Ratio;
    @Size(max = 50)
    @Column(name = "upper_limit")
    private String UpperLimit;
    @Size(max = 50)
    @Column(name = "lower_limit")
    private String LowerLimit;
    @Size(max = 50)
    @Column(name = "alarm_upper_limit")
    private String AlarmUpperLimit;
    @Size(max = 50)
    @Column(name = "alarm_lower_limit")
    private String AlarmLowerLimit;
    @Column(name = "state")
    private Integer State;
    @Column(name = "blocked")
    private Boolean Blocked;
    @Size(max = 255)
    @Column(name = "alarm_state")
    private String AlarmState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getDetectedValue() {
        return DetectedValue;
    }

    public void setDetectedValue(String detectedValue) {
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

    public String getUpperLimit() {
        return UpperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        UpperLimit = upperLimit;
    }

    public String getLowerLimit() {
        return LowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        LowerLimit = lowerLimit;
    }

    public String getAlarmUpperLimit() {
        return AlarmUpperLimit;
    }

    public void setAlarmUpperLimit(String alarmUpperLimit) {
        AlarmUpperLimit = alarmUpperLimit;
    }

    public String getAlarmLowerLimit() {
        return AlarmLowerLimit;
    }

    public void setAlarmLowerLimit(String alarmLowerLimit) {
        AlarmLowerLimit = alarmLowerLimit;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public Boolean getBlocked() {
        return Blocked;
    }

    public void setBlocked(Boolean blocked) {
        Blocked = blocked;
    }

    public String getAlarmState() {
        return AlarmState;
    }

    public void setAlarmState(String alarmState) {
        AlarmState = alarmState;
    }
}
