package com.zheng.business.accept;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author:
 * Date:2022/2/113:55
 **/
public class AddCustomVisAcc {
    @NotNull
    private Long custom_id;
    @NotNull
    private Integer city_code;
    @NotBlank
    private String city_name;
    @NotBlank
    private String address;
    private String detail_address;
    private String contract_name;
    private String phone;
    private String position;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;

    public String getCity_name() {
        return city_name;
    }

    public Long getCustom_id() {
        return custom_id;
    }

    public Integer getCity_code() {
        return city_code;
    }

    public String getAddress() {
        return address;
    }

    public String getDetail_address() {
        return detail_address;
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

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
