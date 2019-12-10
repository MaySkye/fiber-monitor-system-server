package com.rcd.fiber.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @Author: HUHU
 * @Date: 2019/6/27 14:22
 */
@Entity
@Table(name = "alarm_device")
public class AlarmDevice {

    @Size(max = 25)
    @Column(name = "site_name", length = 25)
    public String siteName;
    @Size(max = 255)
    @Column(name = "device_name")
    public String deviceName;
    @Size(max = 255)
    @Column(name = "resource_name")
    public String resourceName;
    @Size(max = 50)
    @Column(name = "timestamp")
    public String timeStamp;
    @Size(max = 50)
    @Column(name = "alarm_value")
    public String alarmValue;
    @Size(max = 50)
    @Column(name = "fault_type")
    public String faultType;
    @Size(max = 50)
    @Column(name = "alarm_upper_limit")
    public String upperLimit;
    @Size(max = 50)
    @Column(name = "alarm_lower_limit")
    public String lowerLimit;
    @Column(name = "status", columnDefinition = "tinyint default 0")
    public Integer status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAlarmValue() {
        return alarmValue;
    }

    public void setAlarmValue(String alarmValue) {
        this.alarmValue = alarmValue;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }
}
