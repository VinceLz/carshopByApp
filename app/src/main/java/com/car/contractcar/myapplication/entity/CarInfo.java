package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/12/6.
 */

public class CarInfo {


    /**
     * status : 1
     * car : {"bid":1,"bphone":"18292882168","childs":[{"gprice":2,"guidegprice":123,"mid":1,"mname":"1","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"},{"gprice":3,"guidegprice":123,"mid":2,"mname":"2","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"}],"gid":1,"gimage":["/img/menu_viewpager_1.png"],"gname":"宝马","maxprice":123,"minprice":123,"stages":"分期政策，可分12期，每期20000元","title":"直降500"}
     */

    private int status;
    /**
     * bid : 1
     * bphone : 18292882168
     * childs : [{"gprice":2,"guidegprice":123,"mid":1,"mname":"1","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"},{"gprice":3,"guidegprice":123,"mid":2,"mname":"2","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"}]
     * gid : 1
     * gimage : ["/img/menu_viewpager_1.png"]
     * gname : 宝马
     * maxprice : 123
     * minprice : 123
     * stages : 分期政策，可分12期，每期20000元
     * title : 直降500
     */

    private CarBean car;

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

    public static class CarBean {
        private int bid;
        private String bphone;
        private int gid;
        private String gname;
        private int maxprice;
        private int minprice;
        private String stages;
        private String title;
        private double stages12;
        private double stages24;
        private double stages36;

        public double getStages12() {
            return stages12;
        }

        public void setStages12(double stages12) {
            this.stages12 = stages12;
        }

        public double getStages24() {
            return stages24;
        }

        public void setStages24(double stages24) {
            this.stages24 = stages24;
        }

        public double getStages36() {
            return stages36;
        }

        public void setStages36(double stages36) {
            this.stages36 = stages36;
        }

        /**
         * gprice : 2
         * guidegprice : 123
         * mid : 1
         * mname : 1
         * mshowImage : /img/menu_viewpager_1.png
         * mtitle : 优惠1000
         */

        private List<ChildsBean> childs;
        private List<String> gimage;

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

        public int getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(int maxprice) {
            this.maxprice = maxprice;
        }

        public int getMinprice() {
            return minprice;
        }

        public void setMinprice(int minprice) {
            this.minprice = minprice;
        }

        public String getStages() {
            return stages;
        }

        public void setStages(String stages) {
            this.stages = stages;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ChildsBean> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBean> childs) {
            this.childs = childs;
        }

        public List<String> getGimage() {
            return gimage;
        }

        public void setGimage(List<String> gimage) {
            this.gimage = gimage;
        }

        public static class ChildsBean {
            private double gprice;
            private String title;
            private double guidegprice;
            private int mid;
            private String mname;
            private String mshowImage;
            private String mtitle;
            private String bid;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public double getGprice() {
                return gprice;
            }

            public void setGprice(double gprice) {
                this.gprice = gprice;
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
}
