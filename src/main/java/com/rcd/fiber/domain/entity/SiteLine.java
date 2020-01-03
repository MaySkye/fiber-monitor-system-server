package com.rcd.fiber.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 15:37 2019/12/13
 * @Modify By:
 */

@Entity
@Table(name = "site_line")
public class SiteLine {
    @Id
    @Column(name = "line_id", length = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lineId;
    //@Size(max = 255)
    @Column(name = "point1")
    private String point1;
    //@Size(max = 255)
    @Column(name = "point2")
    private String point2;
    //@Size(max = 255)
    @Column(name = "line_name")
    private String lineName;
    //@Size(max = 255)
    @Column(name = "line_type")
    private String lineType;
    //@Size(max = 255)
    @Column(name = "line_info")
    private String lineInfo;
    //@Size(max = 255)
    @Column(name = "length")
    private Double len;

    @Column(name = "stable")
    private Double stable;
    @Column(name = "transspeed")
    private Double transspeed;
    @Column(name = "state")
    private String state;

    public long getLineId() {
        return lineId;
    }

    public void setLineId(long lineId) {
        this.lineId = lineId;
    }

    public String getPoint1() {
        return point1;
    }

    public void setPoint1(String point1) {
        this.point1 = point1;
    }

    public String getPoint2() {
        return point2;
    }

    public void setPoint2(String point2) {
        this.point2 = point2;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getLineInfo() {
        return lineInfo;
    }

    public void setLineInfo(String lineInfo) {
        this.lineInfo = lineInfo;
    }

    public Double getLen() {
        return len;
    }

    public void setLen(Double len) {
        this.len = len;
    }

    public Double getStable() {
        return stable;
    }

    public void setStable(Double stable) {
        this.stable = stable;
    }

    public Double getTransspeed() {
        return transspeed;
    }

    public void setTransspeed(Double transspeed) {
        this.transspeed = transspeed;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SiteLine{" +
            "lineId=" + lineId +
            ", point1='" + point1 + '\'' +
            ", point2='" + point2 + '\'' +
            ", lineName='" + lineName + '\'' +
            ", lineType='" + lineType + '\'' +
            ", lineInfo='" + lineInfo + '\'' +
            ", len=" + len +
            ", stable=" + stable +
            ", transspeed=" + transspeed +
            ", state='" + state + '\'' +
            '}';
    }
}
