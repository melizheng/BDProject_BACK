package com.zheng.business.bean;

/**
 * author:
 * Date:2022/1/2718:14
 **/
public class CityCode {
    private int city_code;
    private String city_name;

    public CityCode() {
    }

    public CityCode(int city_code, String city_name) {
        this.city_code = city_code;
        this.city_name = city_name;
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
}
