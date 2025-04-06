package com.zheng.business.bean;

/**
 * bd与城市
 * Date:2022/1/2013:58
 **/
public class Bd_Account_City {
    private long id;
    private long uid;
    private int city_code;
    private String city_name;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;

    public Bd_Account_City(long id, long uid, int city_code, String city_name, java.sql.Timestamp create_time, java.sql.Timestamp update_time) {
        this.id = id;
        this.uid = uid;
        this.city_code = city_code;
        this.city_name = city_name;
        this.create_time = create_time;
        this.update_time = update_time;
    }
    public Bd_Account_City(long uid, int city_code, String city_name) {
        this.uid = uid;
        this.city_code = city_code;
        this.city_name = city_name;
    }

    public Bd_Account_City() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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
}
