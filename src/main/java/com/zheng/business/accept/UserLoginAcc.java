package com.zheng.business.accept;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 登录账号入参
 * Date:2022/1/2415:38
 **/
public class UserLoginAcc {
    @Size(min=7,max=11)
    public String phone;
    @Size(min = 6)
    public String password;
    @Min(value = 1)
    @Max(value = 2)
    @NotNull
    public Integer account_type;
}
