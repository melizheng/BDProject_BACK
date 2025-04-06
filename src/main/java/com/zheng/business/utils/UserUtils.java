package com.zheng.business.utils;

import com.zheng.business.bean.Bd_Account;

/**
 * 储存/获取当前线程的用户信息工具类
 * Date:2022/1/2515:55
 **/
public abstract class UserUtils {
    //线程变量，存放user实体类信息，即使是静态的与其他线程也是隔离的
    private static ThreadLocal<Bd_Account> userThreadLocal = new ThreadLocal<Bd_Account>();

    //从当前线程变量中获取用户信息
    public static Bd_Account getLoginUser() {
        Bd_Account account = userThreadLocal.get();
        return account;
    }

    /**
     * 获取当前登录用户的ID
     * 未登录返回null
     * @return
     */
    public static Long getLoginUserId() {
        Bd_Account account = userThreadLocal.get();
        if (account != null && account.getId() != 0) {
            return account.getId();
        }
        return null;
    }

    public static Integer getLoginUsertype() {
        Bd_Account account = userThreadLocal.get();
        if (account != null) {
            return account.getAccount_type();
        }
        return null;
    }


    //为当前的线程变量赋值上用户信息
    public static void setLoginUser(Bd_Account account) {
        userThreadLocal.set(account);
    }

    //清除线程变量
    public static void removeUser() {
        userThreadLocal.remove();
    }
}
