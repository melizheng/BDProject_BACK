package com.zheng.business.accept;

import com.alibaba.fastjson.JSONArray;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改城市属性入参
 * Date:2022/1/2418:39
 **/
public class UpdateCityAcc {
    @NotNull
    @Min(value = 1)
    public Long id;
    @NotEmpty
    public JSONArray city_code;
    @NotBlank
    public String name;

}
