package com.rcd.fiber.domain.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author: HUHU
 * @Date: 2019/7/16 11:37
 * 图片数据
 */
@Entity
@Table(name = "telemetry_image")
public class TelemetryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "siteName")
    private String siteName;
    @Column(name = "stateImage")
    private String stateImage;
    @Column(name = "timestamp")
    private Timestamp timestamp;

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

    public String getStateImage() {
        return stateImage;
    }

    public void setStateImage(String stateImage) {
        this.stateImage = stateImage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
