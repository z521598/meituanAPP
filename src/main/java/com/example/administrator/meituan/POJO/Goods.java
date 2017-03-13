package com.example.administrator.meituan.POJO;

import java.util.Date;
import java.util.List;

public class Goods extends Store{
    private Integer gid;

    private String gname;

    private Integer gnum;

    private String gpersonnum;

    private String gtype;

    private Integer uid;

    private Double goldprice;

    private Double gprice;

    private String gcontent;

    private String gcover;

    private Date onsaletime;

    private Integer isonsale;

    private Integer gstate;

    private String gdescription;
    //一对多
    private List<Comment> commentlist;

    public List<Comment> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<Comment> commentlist) {
        this.commentlist = commentlist;
    }
    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname == null ? null : gname.trim();
    }

    public Integer getGnum() {
        return gnum;
    }

    public void setGnum(Integer gnum) {
        this.gnum = gnum;
    }

    public String getGpersonnum() {
        return gpersonnum;
    }

    public void setGpersonnum(String gpersonnum) {
        this.gpersonnum = gpersonnum == null ? null : gpersonnum.trim();
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype == null ? null : gtype.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getGoldprice() {
        return goldprice;
    }

    public void setGoldprice(Double goldprice) {
        this.goldprice = goldprice;
    }

    public Double getGprice() {
        return gprice;
    }

    public void setGprice(Double gprice) {
        this.gprice = gprice;
    }

    public String getGcontent() {
        return gcontent;
    }

    public void setGcontent(String gcontent) {
        this.gcontent = gcontent == null ? null : gcontent.trim();
    }

    public String getGcover() {
        return gcover;
    }

    public void setGcover(String gcover) {
        this.gcover = gcover == null ? null : gcover.trim();
    }

    public Date getOnsaletime() {
        return onsaletime;
    }

    public void setOnsaletime(Date onsaletime) {
        this.onsaletime = onsaletime;
    }

    public Integer getIsonsale() {
        return isonsale;
    }

    public void setIsonsale(Integer isonsale) {
        this.isonsale = isonsale;
    }

    public Integer getGstate() {
        return gstate;
    }

    public void setGstate(Integer gstate) {
        this.gstate = gstate;
    }

    public String getGdescription() {
        return gdescription;
    }

    public void setGdescription(String gdescription) {
        this.gdescription = gdescription == null ? null : gdescription.trim();
    }
}