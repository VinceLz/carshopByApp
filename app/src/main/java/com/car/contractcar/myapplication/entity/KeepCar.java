package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/15.
 */

public class KeepCar {


    /**
     * status : 1
     * active : [{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车6","url":"http://www.qq.com"}]
     */

    private int status;
    /**
     * image : /img/menu_1_3.png
     * title : 养车1
     * url : http://www.qq.com
     */

    private List<ActiveBean> active;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ActiveBean> getActive() {
        return active;
    }

    public void setActive(List<ActiveBean> active) {
        this.active = active;
    }

    public static class ActiveBean {
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
}
