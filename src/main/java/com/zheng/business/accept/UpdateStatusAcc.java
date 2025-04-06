package com.zheng.business.accept;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 修改状态的入参
 * Date:2022/1/2416:56
 **/
public class UpdateStatusAcc {
    @NotNull
    @Min(value = 1)
    private Long id;
    @NotNull
    @Max(value = 1)
    @Min(value = 0)
    private Integer status;
    private java.sql.Timestamp time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
