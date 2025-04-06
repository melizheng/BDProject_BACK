package com.zheng.business.dao;

import com.zheng.business.accept.UpdateStatusAcc;
import com.zheng.business.accept.UserLoginAcc;
import com.zheng.business.bean.Bd_Account;
import com.zheng.business.bean.Bd_Account_City;
import com.zheng.business.bean.CityCode;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * mapper接口 对数据库操作的方法
 * 对bd_account、bd_account_city表操作
 * Date:2022/1/2016:07
 **/
@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    //登录账号bd_account表 sql     已知账号，密码，账号类型
    Bd_Account login(UserLoginAcc account);
    //查看是否存在账号bd_account表   已知账号，账号类型
    Bd_Account findAccount(@Param("phone") String phone,@Param("account_type") int account_type);
    //查看是否存在账号bd_account表    已知id
    java.util.LinkedHashMap findAccountbyId(@Param("id") long id);
    //增加账号bd_account表
    int insertAccount(Bd_Account account);
    //增加账号城市属性bd_account_city表
    int insertAccountCityCode(Bd_Account_City bd_account_city);
    //删除账号 级联bd_eaccount_city表  已知id
    int deleteAccount(@Param("id") long id);
    int updateBdCustom(@Param("id") long id);
    //修改用户的名称  已知id,修改后名称
    int updateAccountName(@Param("id") long id,@Param("name") String name,@Param("time") java.sql.Timestamp time);
    //修改用户的密码   已知id,修改后密码
    int updateAccountPassw(@Param("id") long id,@Param("password") String password,@Param("time") java.sql.Timestamp time);
    //修改用户的状态   已知id，修改的状态
    int updateAccountStatus(UpdateStatusAcc updatestatus);
    //得到code的list
    ArrayList<Integer> findAccountAllCityCode(@Param("uid") long uid);
    //得到citycode对象的list
    ArrayList<CityCode> findAccountAllCityCodeandName(@Param("uid") long uid);
    //删除账号城市属性 已知用户id，城市code
    int deleteAccountCityCode(@Param("uid") long uid,@Param("city_code") int city_code);
    //查看可见的账号
    ArrayList<java.util.LinkedHashMap> findCanSeeAccountList(@Param("condition") String condition);
    //查看可见账号的个数
    int findCanSeeAccountCount(@Param("condition") String condition);
    //查看项目的所有的城市
    ArrayList<CityCode> findProjectAllCity();
    //单个BD信息查询
    int CountBDVisits(@Param("id") long id);
    int CountBDReport(@Param("id") long id);
    int CountBDCustom(@Param("id") long id);
    int CountBDCustomcooperate(@Param("id") long id);
    //app主页查询
    int CountBDVisitsByApp(@Param("condition") String condition);
    int CountBDReportByApp(@Param("condition") String condition);
    int CountBDCustomByApp(@Param("condition") String condition);
    int CountBDCustomCooperateByApp(@Param("condition") String condition);
    int CountBDCustomAuditByApp(@Param("condition") String condition);
}
