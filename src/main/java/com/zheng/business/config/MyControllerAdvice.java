package com.zheng.business.config;

import com.zheng.business.bean.AnswerRet;
import com.zheng.business.bean.RrException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * @ControllerAdvice 注解主要用来实现一些全局性的功能
 * @ExceptionHandler 注解实现全局异常的处理
 * Date:2022/1/916:53
 **/
@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 处理抛出的RrException异常，返回answerret对象给前端
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RrException.class)
    public AnswerRet errorHandler(RrException ex) {
        AnswerRet answerRet=new AnswerRet();
        answerRet.setMsg(ex.getMsg());
        answerRet.setCode(ex.getCode());
        return answerRet;
    }

    /**
     * 普通参数(非 java bean)校验出错时抛出，返回answerret对象给前端
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public AnswerRet errorHandler1(ConstraintViolationException ex) {
        AnswerRet<String> answerRet=new AnswerRet(10001,ex.getMessage());
        return answerRet;
    }

    /**
     * 处理抛出的HttpMessageNotReadableException异常，返回answerret对象给前端
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public AnswerRet errorHandler1(HttpMessageNotReadableException ex) {
        AnswerRet<String> answerRet=new AnswerRet(10002,"请检查请求格式是否正确！");
        return answerRet;
    }

    /**
     * SQLIntegrityConstraintViolationException，返回answerret对象给前端
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public AnswerRet errorHandlersqlupdate(SQLIntegrityConstraintViolationException ex) {
        AnswerRet<String> answerRet=new AnswerRet(10005,"数据库约束条件异常！");
        return answerRet;
    }

    /**
     * 请求参数绑定到java bean上失败时抛出,返回answerret对象给前端
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public AnswerRet bindExceptionHandler(BindException ex){
        if(ex.hasErrors()){
            AnswerRet answerRet=new AnswerRet();
            List<FieldError> list = ex.getFieldErrors();
            StringBuffer sb = new StringBuffer("参数：");
            for(FieldError error : list){
                sb.append(error.getField()).append(",");
            }
            sb.append("不符合要求");
            answerRet.setCode(10001);
            answerRet.setMsg(sb.toString());
            return answerRet;
        }
        return new AnswerRet(10000, "系统繁忙!");
    }
}
