package com.zheng.business.bean;

import com.zheng.business.accept.AddCustomVisAcc;
import com.zheng.business.accept.UpdateCustomVisAcc;

/**
 * 客户拜访地址
 * Date:2022/1/2014:19
 **/
public class Custom_Visit {
    private long id;
    private Long custom_id;
    private Integer city_code;
    private String city_name;
    private String address;
    private String detail_address;
    private String contract_name;
    private String phone;
    private String position;
    private Double longitude;
    private Double latitude;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;

    public Custom_Visit(Custom custom) {
        this.custom_id = custom.getId();
        this.city_code = custom.getCity_code();
        this.city_name = custom.getCity_name();
        this.address = custom.getAddress();
        this.detail_address = custom.getDetail_address();
        this.longitude = custom.getLongitude();
        this.latitude = custom.getLatitude();
        this.create_time = custom.getCreate_time();
        this.update_time = custom.getUpdate_time();
    }
    public Custom_Visit(AddCustomVisAcc addCustomVisAcc) {
        this.custom_id=addCustomVisAcc.getCustom_id();
        this.city_code = addCustomVisAcc.getCity_code();
        this.city_name=addCustomVisAcc.getCity_name();
        this.address = addCustomVisAcc.getAddress();
        this.detail_address = addCustomVisAcc.getDetail_address();
        this.longitude = addCustomVisAcc.getLongitude();
        this.latitude = addCustomVisAcc.getLatitude();
        this.contract_name=addCustomVisAcc.getContract_name();
        this.phone=addCustomVisAcc.getPhone();
        this.position=addCustomVisAcc.getPosition();
    }

    public Custom_Visit(UpdateCustomVisAcc CustomVisAcc) {
        this.id=CustomVisAcc.getId();
        this.city_code = CustomVisAcc.getCity_code();
        this.city_name=CustomVisAcc.getCity_name();
        this.address = CustomVisAcc.getAddress();
        this.detail_address = CustomVisAcc.getDetail_address();
        this.longitude = CustomVisAcc.getLongitude();
        this.latitude = CustomVisAcc.getLatitude();
        this.contract_name=CustomVisAcc.getContract_name();
        this.phone=CustomVisAcc.getPhone();
        this.position=CustomVisAcc.getPosition();
    }
    public Custom_Visit() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(long custom_id) {
        this.custom_id = custom_id;
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

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    @Override
    public String toString() {
        return "Custom_Visit{" +
                "id=" + id +
                ", custom_id=" + custom_id +
                ", city_code=" + city_code +
                ", city_name='" + city_name + '\'' +
                ", address='" + address + '\'' +
                ", detail_address='" + detail_address + '\'' +
                ", contract_name='" + contract_name + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
