package com.car.contractcar.myapplication.keepcar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by macmini2 on 17/3/28.
 */

public class KeepCarShopInfo implements Serializable {


    /**
     * status : 1
     * ycstore : {"baddress":"玉祥门才智大厦","bdate":"2017-03-29 15:35:12.0","bphone":"test","clean":[{"mbid":26,"newprice":10,"oldprice":10,"sdesc":"服务","sid":22,"sname":"服务","type":0},{"mbid":26,"newprice":10,"oldprice":20,"sdesc":"测试数据","sid":20,"sname":"测试数据","type":0},{"mbid":26,"newprice":20,"oldprice":30,"sdesc":"整车泡沫冲洗、轮胎轮毅冲洗轮毅清洁 / 车内吸尘／室内简单清洁","sid":19,"sname":"普通洗车-5坐轿车","type":0}],"commentcount":0,"decoration":[{"mbid":26,"newprice":10,"oldprice":20,"sdesc":"测试","sid":6,"sname":"测试","type":0}],"isHot":0,"mainclean":[{"mbid":26,"newprice":0,"oldprice":1,"sdesc":"梁朕","sid":21,"sname":"梁朕","type":0},{"mbid":26,"newprice":2222,"oldprice":1222,"sdesc":"测试","sid":3,"sname":"测试","type":0}],"mbid":26,"mbname":"DOUDOU","purchase":0,"score":0,"time":"9：00\u2014\u201410:00","uname":"豆豆"}
     */

    private int status;
    /**
     * baddress : 玉祥门才智大厦
     * bdate : 2017-03-29 15:35:12.0
     * bphone : test
     * clean : [{"mbid":26,"newprice":10,"oldprice":10,"sdesc":"服务","sid":22,"sname":"服务","type":0},{"mbid":26,"newprice":10,"oldprice":20,"sdesc":"测试数据","sid":20,"sname":"测试数据","type":0},{"mbid":26,"newprice":20,"oldprice":30,"sdesc":"整车泡沫冲洗、轮胎轮毅冲洗轮毅清洁 / 车内吸尘／室内简单清洁","sid":19,"sname":"普通洗车-5坐轿车","type":0}]
     * commentcount : 0
     * decoration : [{"mbid":26,"newprice":10,"oldprice":20,"sdesc":"测试","sid":6,"sname":"测试","type":0}]
     * isHot : 0
     * mainclean : [{"mbid":26,"newprice":0,"oldprice":1,"sdesc":"梁朕","sid":21,"sname":"梁朕","type":0},{"mbid":26,"newprice":2222,"oldprice":1222,"sdesc":"测试","sid":3,"sname":"测试","type":0}]
     * mbid : 26
     * mbname : DOUDOU
     * purchase : 0
     * score : 0
     * time : 9：00——10:00
     * uname : 豆豆
     */

    private YcstoreBean ycstore;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public YcstoreBean getYcstore() {
        return ycstore;
    }

    public void setYcstore(YcstoreBean ycstore) {
        this.ycstore = ycstore;
    }

    public static class YcstoreBean {
        private String baddress;
        private String bdate;
        private String bphone;
        private int commentcount;
        private int isHot;
        private int mbid;
        private String mbname;
        private int purchase;
        private int score;
        private String time;
        private String uname;
        private String PreferentialInformation;

        public String getPreferentialInformation() {
            return PreferentialInformation;
        }

        public void setPreferentialInformation(String preferentialInformation) {
            PreferentialInformation = preferentialInformation;
        }

        /**
         * mbid : 26
         * newprice : 10
         * oldprice : 10
         * sdesc : 服务
         * sid : 22
         * sname : 服务
         * type : 0
         */

        private List<BaseBean> clean;
        /**
         * mbid : 26
         * newprice : 10
         * oldprice : 20
         * sdesc : 测试
         * sid : 6
         * sname : 测试
         * type : 0
         */

        private List<BaseBean> decoration;

        public List<String> getBimage() {
            return bimage;
        }

        public void setBimage(List<String> bimage) {
            this.bimage = bimage;
        }

        /**
         * mbid : 26
         * newprice : 0
         * oldprice : 1
         * sdesc : 梁朕
         * sid : 21
         * sname : 梁朕
         * type : 0
         */

        private List<String> bimage;

        private List<BaseBean> mainclean;

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public String getBdate() {
            return bdate;
        }

        public void setBdate(String bdate) {
            this.bdate = bdate;
        }

        public String getBphone() {
            return bphone;
        }

        public void setBphone(String bphone) {
            this.bphone = bphone;
        }

        public int getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(int commentcount) {
            this.commentcount = commentcount;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public List<BaseBean> getClean() {
            return clean;
        }

        public void setClean(List<BaseBean> clean) {
            this.clean = clean;
        }

        public List<BaseBean> getDecoration() {
            return decoration;
        }

        public void setDecoration(List<BaseBean> decoration) {
            this.decoration = decoration;
        }

        public List<BaseBean> getMainclean() {
            return mainclean;
        }

        public void setMainclean(List<BaseBean> mainclean) {
            this.mainclean = mainclean;
        }


        public static class BaseBean {
            private int mbid;
            private Double newprice;
            private Double oldprice;
            private String sdesc;
            private int sid;
            private String sname;
            private int type;

            public int getMbid() {
                return mbid;
            }

            public void setMbid(int mbid) {
                this.mbid = mbid;
            }

            public Double getNewprice() {
                return newprice;
            }

            public void setNewprice(Double newprice) {
                this.newprice = newprice;
            }

            public Double getOldprice() {
                return oldprice;
            }

            public void setOldprice(Double oldprice) {
                this.oldprice = oldprice;
            }

            public String getSdesc() {
                return sdesc;
            }

            public void setSdesc(String sdesc) {
                this.sdesc = sdesc;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getSname() {
                return sname;
            }

            public void setSname(String sname) {
                this.sname = sname;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "BaseBean{" +
                        "mbid=" + mbid +
                        ", newprice=" + newprice +
                        ", oldprice=" + oldprice +
                        ", sdesc='" + sdesc + '\'' +
                        ", sid=" + sid +
                        ", sname='" + sname + '\'' +
                        ", type=" + type +
                        '}';
            }
        }
    }
}
