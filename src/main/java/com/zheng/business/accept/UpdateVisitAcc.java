package com.zheng.business.accept;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author:
 * Date:2022/2/720:54
 **/
public class UpdateVisitAcc {
    @NotNull
    private Long id;
    @NotBlank
    private String remark;
    private String url;

    public Long getId() {
        return id;
    }

    public String getRemark() {
        return remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
