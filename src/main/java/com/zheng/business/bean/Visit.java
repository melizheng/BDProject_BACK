package com.zheng.business.bean;

import com.zheng.business.accept.AddVisitAcc;

/**
 * 拜访记录
 * Date:2022/1/2014:44
 **/
public class Visit {
    private long id;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;
    private String user_name;
    private long director_id;
    private long user_id;
    private long custom_id;
    private long visit_id;
    private String remark;
    private int status;
    private int read_status;
    private int edit_status;
    private double longitude;
    private double latitude;



    public Visit() {
    }
    public Visit(AddVisitAcc addVisitAcc) {
        this.director_id=addVisitAcc.getDirector_id();
        this.visit_id=addVisitAcc.getVisit_id();
        this.remark=addVisitAcc.getRemark();
        this.latitude=addVisitAcc.getLatitude();
        this.longitude=addVisitAcc.getLongitude();
        this.read_status=0;
        this.edit_status=0;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public java.sql.Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(java.sql.Timestamp create_time) {
        this.create_time = create_time;
    }

    public java.sql.Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(java.sql.Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public long getDirector_id() {
        return director_id;
    }

    public void setDirector_id(long director_id) {
        this.director_id = director_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(long custom_id) {
        this.custom_id = custom_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRead_status() {
        return read_status;
    }

    public void setRead_status(int read_status) {
        this.read_status = read_status;
    }

    public int getEdit_status() {
        return edit_status;
    }

    public void setEdit_status(int edit_status) {
        this.edit_status = edit_status;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(long visit_id) {
        this.visit_id = visit_id;
    }
}
