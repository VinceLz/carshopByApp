package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/24.
 */

public class ShopInfo {


    /**
     * status : 1
     * business : {"baddress":"西安市未央区明光路与凤城七路十字东南角","bid":3,"bimage":["/img/1482973911503133.jpg","/img/1482973931647920.jpg","/img/1482973952622798.jpg","/img/1482973970723943.jpg","/img/1482973991767183.png","/img/1482974009354126.jpg","/img/1482974060613378.jpg"],"bname":"陕西新利亨","bphone":"4008721283","childs":[{"gid":5,"gname":"风行SX6","gshowImage":"/img/1482974276291407.jpg","maxprice":10.29,"minprice":6.99,"title":"购风行享6000元优惠"},{"gid":6,"gname":"景逸S50","gshowImage":"/img/1482974321267271.jpg","maxprice":10.29,"minprice":6.99,"title":"购风行享6000元优惠"}],"majorbusiness":"东风风行系列","stages":"联系开发人员添加"}
     */

    private int status;
    /**
     * baddress : 西安市未央区明光路与凤城七路十字东南角
     * bid : 3
     * bimage : ["/img/1482973911503133.jpg","/img/1482973931647920.jpg","/img/1482973952622798.jpg","/img/1482973970723943.jpg","/img/1482973991767183.png","/img/1482974009354126.jpg","/img/1482974060613378.jpg"]
     * bname : 陕西新利亨
     * bphone : 4008721283
     * childs : [{"gid":5,"gname":"风行SX6","gshowImage":"/img/1482974276291407.jpg","maxprice":10.29,"minprice":6.99,"title":"购风行享6000元优惠"},{"gid":6,"gname":"景逸S50","gshowImage":"/img/1482974321267271.jpg","maxprice":10.29,"minprice":6.99,"title":"购风行享6000元优惠"}]
     * majorbusiness : 东风风行系列
     * stages : 联系开发人员添加
     */

    private BusinessBean business;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public static class BusinessBean {
        private String baddress;
        private int bid;
        private String bname;
        private String bphone;
        private String majorbusiness;
        private String stages;
        private List<String> bimage;
        /**
         * gid : 5
         * gname : 风行SX6
         * gshowImage : /img/1482974276291407.jpg
         * maxprice : 10.29
         * minprice : 6.99
         * title : 购风行享6000元优惠
         */

        private List<ChildsBean> childs;

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public String getBphone() {
            return bphone;
        }

        public void setBphone(String bphone) {
            this.bphone = bphone;
        }

        public String getMajorbusiness() {
            return majorbusiness;
        }

        public void setMajorbusiness(String majorbusiness) {
            this.majorbusiness = majorbusiness;
        }

        public String getStages() {
            return stages;
        }

        public void setStages(String stages) {
            this.stages = stages;
        }

        public List<String> getBimage() {
            return bimage;
        }

        public void setBimage(List<String> bimage) {
            this.bimage = bimage;
        }

        public List<ChildsBean> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBean> childs) {
            this.childs = childs;
        }

        public static class ChildsBean {
            private int gid;
            private String gname;
            private String gshowImage;
            private double maxprice;
            private double minprice;
            private String title;

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
    }
}
