package com.zheng.business.bean;

/**
 * Bd系统用户
 * Date:2022/1/917:06
 **/
public class Bd_Account {
    private long id;
    private String name;
    private String phone;
    private String password;
    private int account_type;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;
    private long creator_id;
    private int status;

    public Bd_Account() {
    }

    public Bd_Account(long id, String name, String phone, String password, int account_type, java.sql.Timestamp create_time, java.sql.Timestamp update_time, long creator_id, int status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.account_type = account_type;
        this.create_time = create_time;
        this.update_time = update_time;
        this.creator_id=creator_id;
        this.status = status;
    }

    public Bd_Account(String name, String phone, String password, int account_type, java.sql.Timestamp create_time, java.sql.Timestamp update_time, long creator_id, int status) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.account_type = account_type;
        this.create_time = create_time;
        this.update_time = update_time;
        this.creator_id = creator_id;
        this.status = status;
    }
    public Bd_Account(String name, String phone, String password, int account_type, long creator_id, int status) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.account_type = account_type;
        this.creator_id = creator_id;
        this.status = status;
    }

    public Bd_Account(String phone, String password, int account_type) {
        this.phone = phone;
        this.password = password;
        this.account_type = account_type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccount_type() {
        return account_type;
    }

    public void setAccount_type(int account_type) {
        this.account_type = account_type;
    }

    public java.sql.Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(java.sql.Timestamp create_time) {
        this.create_time = create_time;
    }

    public java.sql.Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(java.sql.Timestamp update_time) {
        this.update_time = update_time;
    }

    public long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(long creator_id) {
        this.creator_id = creator_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bd_Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
