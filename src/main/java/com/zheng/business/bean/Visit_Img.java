package com.zheng.business.bean;

/**
 * 拜访记录的图片
 * Date:2022/1/2015:08
 **/
public class Visit_Img {
    private long id;
    private long visit_id;
    private String url;

    public Visit_Img(long id, long visit_id, String url) {
        this.id = id;
        this.visit_id = visit_id;
        this.url = url;
    }

    public Visit_Img() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(long visit_id) {
        this.visit_id = visit_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
