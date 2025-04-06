package com.zheng.business.config;

import com.zheng.business.annotations.AdminLogin;
import com.zheng.business.annotations.NormalLogin;
import com.zheng.business.bean.Bd_Account;
import com.zheng.business.bean.RrException;
import com.zheng.business.service.AccountService;
import com.zheng.business.utils.JwtUtils;
import com.zheng.business.utils.UserUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
/**
 * 所有请求前的关卡，查看是否有权限
 * Date:2022/1/2415:02
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查有没有需要普通用户权限的注解
        if (method.isAnnotationPresent(NormalLogin.class)) {
            NormalLogin userLoginToken = method.getAnnotation(NormalLogin.class);
            if (userLoginToken.required()) {
                if (token == null||token.trim().length()==0) {
                    throw new RrException(30001,"无token，请先登录！");
                }
                // 解析 token
                Claims claims= JwtUtils.getClaimByToken(token);
                String phone;
                phone =String.valueOf(claims.get("phone"));
                int type;
                type =Integer.parseInt(String.valueOf(claims.get("type")));
                //取 phone和type查看存在用户否
                Bd_Account account = accountService.findAccount(phone,type);
                if (account == null) {
                    throw new RrException(30003,"用户不存在");
                }
                //查看用户是否被禁用
                if(account.getStatus()!=1)
                    throw new RrException(30004,"用户已被禁用！");
                //检查有没有需要管理员用户权限的注解
                if (method.isAnnotationPresent(AdminLogin.class)) {
                    AdminLogin AdminLoginToken = method.getAnnotation(AdminLogin.class);
                    if (AdminLoginToken.required()) {
                        if(type==2) throw new RrException(30005,"你没有该权限！");
                    }
                }
                UserUtils.setLoginUser(account);
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        UserUtils.removeUser();
    }
}
