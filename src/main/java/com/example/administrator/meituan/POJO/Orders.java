package com.example.administrator.meituan.POJO;

import java.util.Date;

public class Orders extends Goods{
    private Integer oid;

    private Integer uid;

    private Integer gid;

    private String ticket;

    private Date ocreatetime;

    private Date oreturntime;

    private Date opaytime;

    private Date ousetime;

    private Date ocanceltime;

    private Integer osign;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    public Date getOcreatetime() {
        return ocreatetime;
    }

    public void setOcreatetime(Date ocreatetime) {
        this.ocreatetime = ocreatetime;
    }

    public Date getOreturntime() {
        return oreturntime;
    }

    public void setOreturntime(Date oreturntime) {
        this.oreturntime = oreturntime;
    }

    public Date getOpaytime() {
        return opaytime;
    }

    public void setOpaytime(Date opaytime) {
        this.opaytime = opaytime;
    }

    public Date getOusetime() {
        return ousetime;
    }

    public void setOusetime(Date ousetime) {
        this.ousetime = ousetime;
    }

    public Date getOcanceltime() {
        return ocanceltime;
    }

    public void setOcanceltime(Date ocanceltime) {
        this.ocanceltime = ocanceltime;
    }

    public Integer getOsign() {
        return osign;
    }

    public void setOsign(Integer osign) {
        this.osign = osign;
    }
}