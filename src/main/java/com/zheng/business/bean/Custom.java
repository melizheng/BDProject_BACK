package com.zheng.business.bean;

import com.zheng.business.accept.AddCustomAcc;
import com.zheng.business.accept.UpdateCustomAcc;

/**
 * 客户表
 * Date:2022/1/2014:09
 **/
public class Custom {
    private Long id;
    private Long user_id;
    private String company_name;
    private Integer status;
    private Integer city_code;
    private String city_name;
    private String address;
    private String detail_address;
    private Double longitude;
    private Double latitude;
    private String remark;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;

    public Custom(Long id,Long user_id, String company_name, Integer status, Integer city_code, String city_name, String address, String detail_address, Double longitude, Double latitude, String remark, java.sql.Timestamp create_time, java.sql.Timestamp update_time) {
        this.id = id;
        this.user_id=user_id;
        this.company_name = company_name;
        this.status = status;
        this.city_code = city_code;
        this.city_name = city_name;
        this.address = address;
        this.detail_address = detail_address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remark = remark;
        this.create_time = create_time;
        this.update_time = update_time;
    }
    public Custom( AddCustomAcc addCustomAcc) {
        this.company_name = addCustomAcc.getCompany_name();
        this.city_code = addCustomAcc.getCity_code();
        this.city_name=addCustomAcc.getCity_name();
        this.address = addCustomAcc.getAddress();
        this.detail_address = addCustomAcc.getDetail_address();
        this.longitude = addCustomAcc.getLongitude();
        this.latitude = addCustomAcc.getLatitude();
        this.remark = addCustomAcc.getRemark();
    }

    public Custom( UpdateCustomAcc updateCustomAcc) {
        this.id=updateCustomAcc.getId();
        this.company_name = updateCustomAcc.getCompany_name();
        this.city_code = updateCustomAcc.getCity_code();
        this.city_name=updateCustomAcc.getCity_name();
        this.address = updateCustomAcc.getAddress();
        this.detail_address = updateCustomAcc.getDetail_address();
        this.longitude = updateCustomAcc.getLongitude();
        this.latitude = updateCustomAcc.getLatitude();
        this.remark = updateCustomAcc.getRemark();
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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

    public Custom() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCity_code() {
        return city_code;
    }

    public void setCity_code(int city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
