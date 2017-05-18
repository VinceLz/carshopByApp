package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/12/7.
 */

public class CarDetail {


    /**
     * status : 1
     * car : {"bid":1,"bphone":"18292882168","gid":1,"gname":"123","guidegprice":123,"mid":1,"mimage":["/img/menu_viewpager_1.png"],"mname":"1","mtitle":"优惠1000"}
     * recommend : [{"gid":1,"gname":"123","guidegprice":123,"mid":2,"mname":"2","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"},{"gid":2,"gname":"123","guidegprice":123,"mid":3,"mname":"3","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"}]
     */

    private int status;
    /**
     * bid : 1
     * bphone : 18292882168
     * gid : 1
     * gname : 123
     * guidegprice : 123
     * mid : 1
     * mimage : ["/img/menu_viewpager_1.png"]
     * mname : 1
     * mtitle : 优惠1000
     */

    private CarBean car;
    /**
     * gid : 1
     * gname : 123
     * guidegprice : 123
     * mid : 2
     * mname : 2
     * mshowImage : /img/menu_viewpager_1.png
     * mtitle : 优惠1000
     */

    private List<RecommendBean> recommend;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CarBean getCar() {
        return car;
    }

    public void setCar(CarBean car) {
        this.car = car;
    }

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public static class CarBean {
        private int bid;
        private String bphone;
        private int gid;
        private String gname;
        private double guidegprice;
        private int mid;
        private String mname;
        private String mtitle;
        private List<String> mimage;
        private String configure;

        public String getConfigure() {
            return configure;
        }

        public void setConfigure(String configure) {
            this.configure = configure;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getBphone() {
            return bphone;
        }

        public void setBphone(String bphone) {
            this.bphone = bphone;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public double getGuidegprice() {
            return guidegprice;
        }

        public void setGuidegprice(double guidegprice) {
            this.guidegprice = guidegprice;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getMtitle() {
            return mtitle;
        }

        public void setMtitle(String mtitle) {
            this.mtitle = mtitle;
        }

        public List<String> getMimage() {
            return mimage;
        }

        public void setMimage(List<String> mimage) {
            this.mimage = mimage;
        }
    }

    public static class RecommendBean {
        private int gid;
        private String gname;
        private double guidegprice;
        private int mid;
        private String mname;
        private String mshowImage;
        private String mtitle;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public double getGuidegprice() {
            return guidegprice;
        }

        public void setGuidegprice(double guidegprice) {
            this.guidegprice = guidegprice;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getMshowImage() {
            return mshowImage;
        }

        public void setMshowImage(String mshowImage) {
            this.mshowImage = mshowImage;
        }

        public String getMtitle() {
            return mtitle;
        }

        public void setMtitle(String mtitle) {
            this.mtitle = mtitle;
        }
    }
}
