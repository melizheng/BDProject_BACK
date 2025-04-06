package com.zheng.business.accept;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author:
 * Date:2022/2/716:06
 **/
public class UpdateCustomAcc {
    @NotNull
    private Long id;
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

    public String getCity_name() {
        return city_name;
    }
    public Long getId() {
        return id;
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

}
