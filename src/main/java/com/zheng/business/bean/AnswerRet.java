package com.zheng.business.bean;

/**
 * 返回消息msg给前端  code判断是否成功
 * Date:2022/1/916:59
 **/
public class AnswerRet<T> {
    private int code;
    private T msg;

    public AnswerRet(int code, T msg) {
        this.code = code;
        this.msg = msg;
    }

    public AnswerRet() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
