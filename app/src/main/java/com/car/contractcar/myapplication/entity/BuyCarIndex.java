package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/12.
 */

public class BuyCarIndex {

    /**
     * status : 1
     * homeImage : [{"image":"/img/menu_viewpager_5.png","url":"http://www.baidu.com"},{"image":"/img/menu_viewpager_4.png","url":"http://www.taobao.com"},{"image":"/img/menu_viewpager_1.png","url":"http://www.qq.com"}]
     * homeActive : [{"image":"/img/menu_1_2.png","title":"活动1","url":"http://www.baidu.com"},{"image":"/img/menu_1_3.png","title":"活动2","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"活动3","url":"http://www.qq.com"}]
     * homeCar : [{"gfirstimage":"/img/menu_1_3.png","gid":1,"gname":"宝马","gprice":1234567}]
     */

    private int status;
    /**
     * image : /img/menu_viewpager_5.png
     * url : http://www.baidu.com
     */

    private List<HomeImageBean> homeImage;
    /**
     * image : /img/menu_1_2.png
     * title : 活动1
     * url : http://www.baidu.com
     */

    private List<HomeActiveBean> homeActive;
    /**
     * gfirstimage : /img/menu_1_3.png
     * gid : 1
     * gname : 宝马
     * gprice : 1234567
     */

    private List<HomeCarBean> homeCar;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<HomeImageBean> getHomeImage() {
        return homeImage;
    }

    public void setHomeImage(List<HomeImageBean> homeImage) {
        this.homeImage = homeImage;
    }

    public List<HomeActiveBean> getHomeActive() {
        return homeActive;
    }

    public void setHomeActive(List<HomeActiveBean> homeActive) {
        this.homeActive = homeActive;
    }

    public List<HomeCarBean> getHomeCar() {
        return homeCar;
    }

    public void setHomeCar(List<HomeCarBean> homeCar) {
        this.homeCar = homeCar;
    }

    public static class HomeImageBean {
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

    public static class HomeActiveBean {
        private String image;
        private String title;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class HomeCarBean {
        private String gfirstimage;
        private int gid;
        private String gname;
        private int gprice;

        public String getGfirstimage() {
            return gfirstimage;
        }

        public void setGfirstimage(String gfirstimage) {
            this.gfirstimage = gfirstimage;
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

        public int getGprice() {
            return gprice;
        }

        public void setGprice(int gprice) {
            this.gprice = gprice;
        }
    }
}
