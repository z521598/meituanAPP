package com.example.administrator.meituan.POJO;

import java.util.Date;
import java.util.List;

public class Store {
    private Integer sid;

    private Integer uid;

    private String stype;

    private String saddress;

    private Integer srating;

    private String spermit;

    private String sperson;

    private String spidcard;

    private String shomephone;

    private String simage1;

    private String simage2;

    private String simage3;

    private String simage4;

    private String simage5;

    private Integer scredit;

    private Integer ssign;

    private Date suptime;

    private Date spasstime;

    private Date sdealtime;

    private Date ssealtime;

    private String sdescription;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype == null ? null : stype.trim();
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress == null ? null : saddress.trim();
    }

    public Integer getSrating() {
        return srating;
    }

    public void setSrating(Integer srating) {
        this.srating = srating;
    }

    public String getSpermit() {
        return spermit;
    }

    public void setSpermit(String spermit) {
        this.spermit = spermit == null ? null : spermit.trim();
    }

    public String getSperson() {
        return sperson;
    }

    public void setSperson(String sperson) {
        this.sperson = sperson == null ? null : sperson.trim();
    }

    public String getSpidcard() {
        return spidcard;
    }

    public void setSpidcard(String spidcard) {
        this.spidcard = spidcard == null ? null : spidcard.trim();
    }

    public String getShomephone() {
        return shomephone;
    }

    public void setShomephone(String shomephone) {
        this.shomephone = shomephone == null ? null : shomephone.trim();
    }

    public String getSimage1() {
        return simage1;
    }

    public void setSimage1(String simage1) {
        this.simage1 = simage1 == null ? null : simage1.trim();
    }

    public String getSimage2() {
        return simage2;
    }

    public void setSimage2(String simage2) {
        this.simage2 = simage2 == null ? null : simage2.trim();
    }

    public String getSimage3() {
        return simage3;
    }

    public void setSimage3(String simage3) {
        this.simage3 = simage3 == null ? null : simage3.trim();
    }

    public String getSimage4() {
        return simage4;
    }

    public void setSimage4(String simage4) {
        this.simage4 = simage4 == null ? null : simage4.trim();
    }

    public String getSimage5() {
        return simage5;
    }

    public void setSimage5(String simage5) {
        this.simage5 = simage5 == null ? null : simage5.trim();
    }

    public Integer getScredit() {
        return scredit;
    }

    public void setScredit(Integer scredit) {
        this.scredit = scredit;
    }

    public Integer getSsign() {
        return ssign;
    }

    public void setSsign(Integer ssign) {
        this.ssign = ssign;
    }

    public Date getSuptime() {
        return suptime;
    }

    public void setSuptime(Date suptime) {
        this.suptime = suptime;
    }

    public Date getSpasstime() {
        return spasstime;
    }

    public void setSpasstime(Date spasstime) {
        this.spasstime = spasstime;
    }

    public Date getSdealtime() {
        return sdealtime;
    }

    public void setSdealtime(Date sdealtime) {
        this.sdealtime = sdealtime;
    }

    public Date getSsealtime() {
        return ssealtime;
    }

    public void setSsealtime(Date ssealtime) {
        this.ssealtime = ssealtime;
    }

    public String getSdescription() {
        return sdescription;
    }

    public void setSdescription(String sdescription) {
        this.sdescription = sdescription == null ? null : sdescription.trim();
    }
    //一对多配置
    List<Goods> goodsList;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

}