package com.zheng.business.bean;

/**
 * author:
 * Date:2022/2/1612:08
 **/
public class Audit {
    private Long id;
    private Long custom_id;
    private Long user_id;
    private Integer status;

    public Audit(Long id, Long custom_id, Long user_id, Integer status) {
        this.id = id;
        this.custom_id = custom_id;
        this.user_id = user_id;
        this.status = status;
    }

    public Audit(long custom_id, long user_id, int status) {
        this.custom_id = custom_id;
        this.user_id = user_id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(long custom_id) {
        this.custom_id = custom_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
