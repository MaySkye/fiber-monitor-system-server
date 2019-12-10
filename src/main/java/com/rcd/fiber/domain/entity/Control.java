package com.rcd.fiber.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 10:11 2019/9/25
 * @Modify By:
 */
@Entity
@Table(name = "control")
public class Control {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 255)
    @Column(name = "site_name", length = 255)
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
    @Column(name = "value")
    private String Value;
    @Size(max = 255)
    @Column(name = "state")
    private String State;
    @Size(max = 255)
    @Column(name = "blocked")
    private String Blocked;

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
        Timestamp = timestamp;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getBlocked() {
        return Blocked;
    }

    public void setBlocked(String blocked) {
        Blocked = blocked;
    }
}
