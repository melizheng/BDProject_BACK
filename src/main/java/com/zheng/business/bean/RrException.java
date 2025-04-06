package com.zheng.business.bean;

/**
 * 异常类，用于处理异常抛出该类，在MyControllerAdvice抓到该异常会返回answerret给前端
 * msg:返回信息
 * code:异常编码
 * Date:2022/1/916:52
 **/
public class RrException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;


    public RrException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RrException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RrException( int code,String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RrException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
