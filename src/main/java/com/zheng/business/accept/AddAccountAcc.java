package com.zheng.business.accept;

import com.alibaba.fastjson.JSONArray;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 增加账号入参对象 注释来限制入参的内容
 * Date:2022/1/2413:51
 **/
public class AddAccountAcc {
    @NotBlank
    private String name;
    @NotEmpty
    private JSONArray city_code;
    @NotBlank
    @Size(min=7,max=11)
    private String phone;
    @Max(value = 2)
    @Min(value = 1)
    @NotNull
    private  Integer account_type;

    public String getName() {
        return name;
    }

    public JSONArray getCity_code() {
        return city_code;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getAccount_type() {
        return account_type;
    }
}
