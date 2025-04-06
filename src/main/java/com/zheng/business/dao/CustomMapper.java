package com.zheng.business.dao;

import com.zheng.business.bean.Audit;
import com.zheng.business.bean.Custom;
import com.zheng.business.bean.Custom_Visit;
import com.zheng.business.bean.Visit;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * custom、custom_visit表操作
 * Date:2022/1/2718:11
 **/
@org.apache.ibatis.annotations.Mapper
public interface CustomMapper {
    //增加客户的拜访地址
    int insertCustomVisit(Custom_Visit custom_visit);
    //删除拜访地址
    int deleteCustomVisit(long id);
    //查看所有客户的所有拜访地址all
    ArrayList<LinkedHashMap> findCustomVisitListWentMakeVisits(long id,@Param("condition") String condition);
    //查看客户的拜访地址all
    ArrayList<LinkedHashMap> findCustomVisit(long id,@Param("condition") String condition);
    //查看客户拜访地址个数
    int findCustomVisitCount(long id);
    //查看单个拜访地址信息
    java.util.LinkedHashMap findCustomVisitbyId(@Param("id") long id);
    //查看单个拜访地址的坐标
    Visit findCustomVisitzuobiaobyId(@Param("id") long id);
    //修改拜访地址
    int updateCustomVisit(Custom_Visit custom_visit);
    //增加客户信息
    int insertCustom(Custom custom);
    //修改客户状态
    int updateCustomStatus(long id,int status,java.sql.Timestamp time);
    //修改客户对接人
    int updateCustomBD(long id,long bd,java.sql.Timestamp time);
    //修改客户的备注
    int updateCustomRemark(@Param("id") long id,@Param("remark") String remark,java.sql.Timestamp time);
    //查看可见客户
    ArrayList<LinkedHashMap> findCustom(@Param("condition") String condition);
    //查看可见客户个数
    int findCustomCount(@Param("condition") String condition);
    //修改客户信息
    int updateCustom(Custom custom);
    //查看单个客户信息
    java.util.LinkedHashMap findCustombyId(@Param("id") long id);
    //增加审核表的信息
    int insertAudit(Audit audit);
    //修改审核表的信息
    int updateAudit(@Param("id") long id,@Param("status") long status);
    //判断是否存在该审核表
    Audit findAuditById(@Param("id") long id);
    //查看审核列表
    ArrayList<LinkedHashMap> findAuditList(@Param("condition") String condition);
    //查看个数
    int findAuditListCount(@Param("condition") String condition);
}
