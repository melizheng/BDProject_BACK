package com.zheng.business.accept;

import javax.validation.constraints.NotNull;

/**
 * author:
 * Date:2022/2/719:54
 **/
public class AddVisitAcc {
    @NotNull
    private Long director_id;
    @NotNull
    private Long visit_id;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
    private String remark;
    private String url;

    public Long getDirector_id() {
        return director_id;
    }


    public Long getVisit_id() {
        return visit_id;
    }

    public String getRemark() {
        return remark;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getUrl() {
        return url;
    }
}
