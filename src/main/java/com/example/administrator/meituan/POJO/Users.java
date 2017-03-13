package com.example.administrator.meituan.POJO;

import java.util.Date;

public class Users {
    private Integer uid;

    private String uusername;

    private String upassword;

    private String uname;

    private String uhead;

    private String usex;

    private String uemail;

    private String uaddress;

    private String utelephone;

    private Integer credit;

    private Date ulastlogtime;

    private String ulastlogaddress;

    private Date uregisttime;

    private Integer usign;

    private Integer uisseal;

    private Date usealtime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUusername() {
        return uusername;
    }

    public void setUusername(String uusername) {
        this.uusername = uusername == null ? null : uusername.trim();
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword == null ? null : upassword.trim();
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getUhead() {
        return uhead;
    }

    public void setUhead(String uhead) {
        this.uhead = uhead == null ? null : uhead.trim();
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex == null ? null : usex.trim();
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail == null ? null : uemail.trim();
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress == null ? null : uaddress.trim();
    }

    public String getUtelephone() {
        return utelephone;
    }

    public void setUtelephone(String utelephone) {
        this.utelephone = utelephone == null ? null : utelephone.trim();
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Date getUlastlogtime() {
        return ulastlogtime;
    }

    public void setUlastlogtime(Date ulastlogtime) {
        this.ulastlogtime = ulastlogtime;
    }

    public String getUlastlogaddress() {
        return ulastlogaddress;
    }

    public void setUlastlogaddress(String ulastlogaddress) {
        this.ulastlogaddress = ulastlogaddress == null ? null : ulastlogaddress.trim();
    }

    public Date getUregisttime() {
        return uregisttime;
    }

    public void setUregisttime(Date uregisttime) {
        this.uregisttime = uregisttime;
    }

    public Integer getUsign() {
        return usign;
    }

    public void setUsign(Integer usign) {
        this.usign = usign;
    }

    public Integer getUisseal() {
        return uisseal;
    }

    public void setUisseal(Integer uisseal) {
        this.uisseal = uisseal;
    }

    public Date getUsealtime() {
        return usealtime;
    }

    public void setUsealtime(Date usealtime) {
        this.usealtime = usealtime;
    }
}