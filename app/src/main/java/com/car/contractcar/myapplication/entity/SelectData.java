package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/20.
 */

public class SelectData {


    /**
     * status : 1
     * params : {"pageNo":1,"pageSize":10}
     * result : [{"gid":1,"gname":"宝马","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":123,"title":"直降500"},{"gid":2,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":12,"title":"直降500"},{"gid":3,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":44444,"minprice":44444,"title":"直降500"},{"gid":4,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":1,"minprice":1,"title":"直降500"}]
     * image : [{"image":"/img/menu_viewpager_5.png","url":"http://www.qq.com"},{"image":"/img/menu_viewpager_5.png","url":"http://www.qq.com"},{"image":"/img/menu_viewpager_5.png","url":"http://www.qq.com"}]
     */

    private int status;
    /**
     * pageNo : 1
     * pageSize : 10
     */

    private ParamsBean params;
    /**
     * gid : 1
     * gname : 宝马
     * gshowImage : /img/menu_viewpager_1.png
     * maxprice : 123
     * minprice : 123
     * title : 直降500
     */

    private List<ResultBean> result;
    /**
     * image : /img/menu_viewpager_5.png
     * url : http://www.qq.com
     */

    private List<ImageBean> image;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public List<ImageBean> getImage() {
        return image;
    }

    public void setImage(List<ImageBean> image) {
        this.image = image;
    }

    public static class ParamsBean {
        private Integer mid;
        private String gname;
        private String bname;// 店家名
        private List<String> level; // 等级
        private List<String> output;// 排量
        private List<String> drive;// 驱动
        private List<String> fuel;// 燃料
        private List<String> transmission;// 变速相
        private List<String> country;// 国家
        private List<String> produce;// 生产方式
        private List<String> structure;// 结构
        private List<String> seat;// 座位
        private String keyword; // 关键字
        private Double maxprice; // 最大价格
        private Double minprice; // 最小价格
        private int pageNo;
        private int pageSize;

        public Integer getMid() {
            return mid;
        }

        public void setMid(Integer mid) {
            this.mid = mid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public List<String> getLevel() {
            return level;
        }

        public void setLevel(List<String> level) {
            this.level = level;
        }

        public List<String> getOutput() {
            return output;
        }

        public void setOutput(List<String> output) {
            this.output = output;
        }

        public List<String> getDrive() {
            return drive;
        }

        public void setDrive(List<String> drive) {
            this.drive = drive;
        }

        public List<String> getFuel() {
            return fuel;
        }

        public void setFuel(List<String> fuel) {
            this.fuel = fuel;
        }

        public List<String> getTransmission() {
            return transmission;
        }

        public void setTransmission(List<String> transmission) {
            this.transmission = transmission;
        }

        public List<String> getCountry() {
            return country;
        }

        public void setCountry(List<String> country) {
            this.country = country;
        }

        public List<String> getProduce() {
            return produce;
        }

        public void setProduce(List<String> produce) {
            this.produce = produce;
        }

        public List<String> getStructure() {
            return structure;
        }

        public void setStructure(List<String> structure) {
            this.structure = structure;
        }

        public List<String> getSeat() {
            return seat;
        }

        public void setSeat(List<String> seat) {
            this.seat = seat;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public Double getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(Double maxprice) {
            this.maxprice = maxprice;
        }

        public Double getMinprice() {
            return minprice;
        }

        public void setMinprice(Double minprice) {
            this.minprice = minprice;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class ResultBean {
        private int gid;
        private String gname;
        private String gshowImage;
        private double maxprice;
        private double minprice;
        private String title;
        private int bid;

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
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

        public String getGshowImage() {
            return gshowImage;
        }

        public void setGshowImage(String gshowImage) {
            this.gshowImage = gshowImage;
        }

        public double getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(double maxprice) {
            this.maxprice = maxprice;
        }

        public double getMinprice() {
            return minprice;
        }

        public void setMinprice(double minprice) {
            this.minprice = minprice;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ImageBean {
        private String image;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
