package com.car.contractcar.myapplication.keepcar.bean;

import java.util.List;

/**
 * Created by macmini2 on 17/3/16.
 */

public class KeepHomeBean {


    /**
     * status : 1
     * homeImage : [{"image":"/img/menu_viewpager_1.png","url":"http://www.qq.com"},{"image":"/img/menu_viewpager_1.png","url":"http://www.qq.com"}]
     * ycstore : [{"baddress":"2","bphone":"2","bshowimage":"2","commentcount":0,"distance":"12862692","isHot":0,"mbid":2,"mbname":"2","purchase":0,"score":0,"title1":"2","title2":"11111"}]
     */

    private int status;
    /**
     * image : /img/menu_viewpager_1.png
     * url : http://www.qq.com
     */

    private List<HomeImageBean> homeImage;
    /**
     * baddress : 2
     * bphone : 2
     * bshowimage : 2
     * commentcount : 0
     * distance : 12862692
     * isHot : 0
     * mbid : 2
     * mbname : 2
     * purchase : 0
     * score : 0
     * title1 : 2
     * title2 : 11111
     */

    private List<YcstoreBean> ycstore;

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

    public List<YcstoreBean> getYcstore() {
        return ycstore;
    }

    public void setYcstore(List<YcstoreBean> ycstore) {
        this.ycstore = ycstore;
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

    public static class YcstoreBean {
        private String baddress;
        private String bphone;
        private String bshowimage;
        private int commentcount;
        private String distance;
        private int isHot;
        private int mbid;
        private String mbname;
        private int purchase;
        private int score;
        private String title1;
        private String title2;
        private String longitude;
        private String latitude;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public String getBphone() {
            return bphone;
        }

        public void setBphone(String bphone) {
            this.bphone = bphone;
        }

        public String getBshowimage() {
            return bshowimage;
        }

        public void setBshowimage(String bshowimage) {
            this.bshowimage = bshowimage;
        }

        public int getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(int commentcount) {
            this.commentcount = commentcount;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public int getMbid() {
            return mbid;
        }

        public void setMbid(int mbid) {
            this.mbid = mbid;
        }

        public String getMbname() {
            return mbname;
        }

        public void setMbname(String mbname) {
            this.mbname = mbname;
        }

        public int getPurchase() {
            return purchase;
        }

        public void setPurchase(int purchase) {
            this.purchase = purchase;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }
    }
}
