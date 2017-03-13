package com.example.administrator.meituan.POJO;

import java.util.Date;

public class Message {
    private Integer mid;

    private String uusername;

    private String mtitle;

    private Integer mtype;

    private Integer msign;

    private String mimage;

    private String mimage2;

    private String mcontent;

    private Date usertime;

    private Date admintime;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getUusername() {
        return uusername;
    }

    public void setUusername(String uusername) {
        this.uusername = uusername == null ? null : uusername.trim();
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle == null ? null : mtitle.trim();
    }

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }

    public Integer getMsign() {
        return msign;
    }

    public void setMsign(Integer msign) {
        this.msign = msign;
    }

    public String getMimage() {
        return mimage;
    }

    public void setMimage(String mimage) {
        this.mimage = mimage == null ? null : mimage.trim();
    }

    public String getMimage2() {
        return mimage2;
    }

    public void setMimage2(String mimage2) {
        this.mimage2 = mimage2 == null ? null : mimage2.trim();
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent == null ? null : mcontent.trim();
    }

    public Date getUsertime() {
        return usertime;
    }

    public void setUsertime(Date usertime) {
        this.usertime = usertime;
    }

    public Date getAdmintime() {
        return admintime;
    }

    public void setAdmintime(Date admintime) {
        this.admintime = admintime;
    }
}