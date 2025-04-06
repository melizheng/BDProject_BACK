package com.zheng.business.dao;

import com.zheng.business.bean.Visit;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * author:
 * Date:2022/2/716:39
 **/
@org.apache.ibatis.annotations.Mapper
public interface VisitMapper {
    //增加客户的拜访记录
    int insertVisit(Visit Visit);
    //查看客户的拜访记录的图片是否存在
    Integer insertVisitImgBeforeFind(@Param("visit_id")Long visit_id);
    //增加客户的拜访记录的图片
    int insertVisitImg(@Param("visit_id")Long visit_id,@Param("url")String url);
    //修改客户的拜访记录的图片
    int updateVisitImg(@Param("visit_id")Long visit_id,@Param("url")String url);
    //修改客户的拜访记录的备注
    int updateVisit(@Param("id")Long id,@Param("remark")String remark,@Param("time") java.sql.Timestamp time);
    //删除拜访记录
    int deleteVisitById(@Param("id")Long id);
    //查看拜访记录列表
    ArrayList<LinkedHashMap> findCanSeeVisitList(@Param("user_id")Long user_id,@Param("condition") StringBuffer condition);
    //管理者按照公司或者BD列表拜查看访记录列表 不需要判断是否是给自己拜访
    ArrayList<LinkedHashMap> findCanSeeVisitListBYADMIN(@Param("condition") StringBuffer condition);
    //得到可见的拜访记录的个数
    int findVisitsCount(@Param("user_id")Long user_id,@Param("condition") String condition);
    int findVisitsCountBYADMIN(@Param("condition") String condition);
    //通过id查看拜访记录
    LinkedHashMap findVisitbyId(@Param("id") Long id);
    //得到拜访记录对应的图片
    String findVisitImg(@Param("id") Long id);
    //已阅拜访记录
    int readVisit(@Param("id") Long id,@Param("director_id") Long director_id);

}
