package com.zheng.business.accept;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author:
 * Date:2022/1/2812:40
 **/
public class AddCustomAcc {
    @NotBlank
    private String company_name;
    @NotNull
    private Integer city_code;
    @NotBlank
    private String city_name;
    @NotBlank
    private String address;
    private String detail_address;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
    private String remark;
    private String contract_name;
    private String phone;
    private String position;

    public String getCity_name() {
        return city_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public int getCity_code() {
        return city_code;
    }

    public String getAddress() {
        return address;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getRemark() {
        return remark;
    }

    public String getContract_name() {
        return contract_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }
}
