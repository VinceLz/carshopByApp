package com.car.contractcar.myapplication.entity;

/**
 * Created by macmini2 on 16/12/24.
 */

public class FloorPrice {


    /**
     * status : 1
     * business : {"baddress":"陕西省西安市","bid":1,"bname":"某某4S经销店","bphone":"18292882168","bshowImage":"/img/menu_viewpager_1.png","majorbusiness":"玩玩","title1":"进店就有大礼包 ","title2":"购车享1000元直补"}
     */

    private int status;
    /**
     * baddress : 陕西省西安市
     * bid : 1
     * bname : 某某4S经销店
     * bphone : 18292882168
     * bshowImage : /img/menu_viewpager_1.png
     * majorbusiness : 玩玩
     * title1 : 进店就有大礼包
     * title2 : 购车享1000元直补
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
        private String bshowImage;
        private String majorbusiness;
        private String title1;
        private String title2;

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

        public String getBshowImage() {
            return bshowImage;
        }

        public void setBshowImage(String bshowImage) {
            this.bshowImage = bshowImage;
        }

        public String getMajorbusiness() {
            return majorbusiness;
        }

        public void setMajorbusiness(String majorbusiness) {
            this.majorbusiness = majorbusiness;
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
