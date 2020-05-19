package com.rcd.fiber.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @Column(name = "site_id", length = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long siteId;

    //@Size(max = 255)
    @Column(name = "site_level")
    private Long siteLevel;
    //@Size(max = 255)
    @Column(name = "site_localx")
    private Double siteLocalx;
    //@Size(max = 255)
    @Column(name = "site_localy")
    private Double siteLocaly;
    //@Size(max = 255)
    @Column(name = "site_name")
    private String siteName;
    //@Size(max = 255)
    @Column(name = "site_info")
    private String siteInfo;
    //@Size(max = 255)
    @Column(name = "site_type")
    private String siteType;
    //@Size(max = 255)
    @Column(name = "site_address")
    private String siteAddress;
    //@Size(max = 255)
    @Column(name = "connect")
    private String connect;

    @Column(name = "state")
    private String state;

    @Column(name = "devcount")
    private Integer devcount;

    public Site() {
    }


    public Site(Long siteLevel, Double siteLocalx, Double siteLocaly, String siteName, String siteInfo, String siteType, String siteAddress, String connect, String state, Integer devcount) {
        this.siteLevel = siteLevel;
        this.siteLocalx = siteLocalx;
        this.siteLocaly = siteLocaly;
        this.siteName = siteName;
        this.siteInfo = siteInfo;
        this.siteType = siteType;
        this.siteAddress = siteAddress;
        this.connect = connect;
        this.state = state;
        this.devcount = devcount;
    }

    public Long getSiteId() {
        return siteId;
      }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
      }


      public Long getSiteLevel() {
        return siteLevel;
      }

      public void setSiteLevel(Long siteLevel) {
        this.siteLevel = siteLevel;
      }


      public Double getSiteLocalx() {
        return siteLocalx;
      }

      public void setSiteLocalx(Double siteLocalx) {
        this.siteLocalx = siteLocalx;
      }


      public Double getSiteLocaly() {
        return siteLocaly;
      }

      public void setSiteLocaly(Double siteLocaly) {
        this.siteLocaly = siteLocaly;
      }


      public String getSiteName() {
        return siteName;
      }

      public void setSiteName(String siteName) {
        this.siteName = siteName;
      }


      public String getSiteInfo() {
        return siteInfo;
      }

      public void setSiteInfo(String siteInfo) {
        this.siteInfo = siteInfo;
      }


      public String getSiteType() {
        return siteType;
      }

      public void setSiteType(String siteType) {
        this.siteType = siteType;
      }


      public String getSiteAddress() {
        return siteAddress;
      }

      public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
      }


      public String getConnect() {
        return connect;
      }

      public void setConnect(String connect) {
        this.connect = connect;
      }

      public String getState() {
        return state;
      }

      public void setState(String state) {
        this.state = state;
      }

    public Integer getDevcount() {
        return devcount;
    }

    public void setDevcount(Integer devcount) {
        this.devcount = devcount;
    }

    @Override
    public String toString() {
        return "Site{" +
            "siteId=" + siteId +
            ", siteLevel=" + siteLevel +
            ", siteLocalx=" + siteLocalx +
            ", siteLocaly=" + siteLocaly +
            ", siteName='" + siteName + '\'' +
            ", siteInfo='" + siteInfo + '\'' +
            ", siteType='" + siteType + '\'' +
            ", siteAddress='" + siteAddress + '\'' +
            ", connect='" + connect + '\'' +
            ", state='" + state + '\'' +
            ", devcount=" + devcount +
            '}';
    }
}
