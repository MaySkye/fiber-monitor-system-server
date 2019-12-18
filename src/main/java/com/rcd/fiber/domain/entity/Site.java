package com.rcd.fiber.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @Column(name = "site_id", length = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long siteId;

    //@Size(max = 255)
    @Column(name = "site_level")
    private long siteLevel;
    //@Size(max = 255)
    @Column(name = "site_localx")
    private double siteLocalx;
    //@Size(max = 255)
    @Column(name = "site_localy")
    private double siteLocaly;
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


    public long getSiteId() {
        return siteId;
      }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
      }


      public long getSiteLevel() {
        return siteLevel;
      }

      public void setSiteLevel(long siteLevel) {
        this.siteLevel = siteLevel;
      }


      public double getSiteLocalx() {
        return siteLocalx;
      }

      public void setSiteLocalx(double siteLocalx) {
        this.siteLocalx = siteLocalx;
      }


      public double getSiteLocaly() {
        return siteLocaly;
      }

      public void setSiteLocaly(double siteLocaly) {
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
            '}';
    }
}
