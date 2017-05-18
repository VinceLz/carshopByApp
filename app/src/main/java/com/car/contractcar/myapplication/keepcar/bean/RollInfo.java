package com.car.contractcar.myapplication.keepcar.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/21.
 */

public class RollInfo implements Serializable {

    private Integer ruid;
    private int rid;
    private int type; //0表示全场通用卷 1,2,3表示对应的服务类型的专用卷
    private String rname;
    private double price;
    private String pastdate;// 到期日期
    private String createdate;// 开始日期
    private int status;// 优惠劵状态 0 待
    private double condition; //使用条件

    public Integer getRuid() {
        return ruid;
    }

    public void setRuid(Integer ruid) {
        this.ruid = ruid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPastdate() {
        return pastdate;
    }

    public void setPastdate(String pastdate) {
        this.pastdate = pastdate;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCondition() {
        return condition;
    }

    public void setCondition(double condition) {

        this.condition = condition;
    }

    @Override
    public String toString() {
        return "RollInfo{" +
                "rid=" + rid +
                ", type=" + type +
                ", rname='" + rname + '\'' +
                ", price=" + price +
                ", pastdate='" + pastdate + '\'' +
                ", createdate='" + createdate + '\'' +
                ", status=" + status +
                ", condition=" + condition +
                '}';
    }
}
