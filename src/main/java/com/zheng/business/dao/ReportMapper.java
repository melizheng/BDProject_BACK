package com.zheng.business.dao;

import com.zheng.business.bean.Report_Record;
import com.zheng.business.bean.Report_Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * author:
 * Date:2022/2/914:31
 **/
@Mapper
public interface ReportMapper {
    //增加汇报规则
    int insertReportRule(Report_Rule report_rule);
    //增加相关联的report中间表
    int insertReportMiddle(@Param("creator_id")Long creator_id,@Param("report_rule_id")Long report_rule_id,@Param("reporter_id")Long reporter_id);
    //【定时任务以及创建规则时】增加汇报记录表
    int insertReportRecordBySystem(Report_Record report_record);
    //定时任务查看汇报规则列表【判断是否增加汇报记录】
    ArrayList<Report_Rule> scheduledTasksGetReportRule(@Param("condition")String condition);
    //通过汇报规则id得到对应的汇报人们的id
    ArrayList<Long> getReportRuleReporterIdByRuleId(@Param("report_rule_id")Long report_rule_id);
    //删除汇报规则、以及相关的未填写的汇报记录
    int deleteReportRule(@Param("id")Long id,@Param("time")String time);
    //通过id查找汇报规则
    LinkedHashMap getReportRuleByID(@Param("id")Long id);
    //管理员得到汇报规则列表
    ArrayList<LinkedHashMap> getReportRuleList(@Param("creator_id")Long creator_id,@Param("condition")String condition);
    //得到汇报规则的个数
    Integer getRuleCount(@Param("id")Long id);
    //得到规则对应的人数
    Integer getRuleBDNumber(@Param("report_rule_id")Long report_rule_id);
    //查看是否存在该汇报记录
    Report_Record existReportRecordById(@Param("id")Long id);
    //提交汇报记录
    int updateReportRecord(Report_Record report_record);
    //得到拜访记录的个数
    Integer getVisitsCountByBd(Report_Record report_record);
    //得到汇报记录列表
    ArrayList<LinkedHashMap> getReportRecordList(@Param("user_id")Long user_id,@Param("condition") StringBuffer condition);
    //得到汇报记录个数
    Integer getReportRecordListCount(@Param("user_id")Long user_id,@Param("condition") StringBuffer condition);
    LinkedHashMap getReportRecordById(@Param("id")Long id);
    //已阅记录
    int readReport(@Param("id") Long id,@Param("director_id") Long director_id);
    //插入图片
    int updateReportImages(@Param("id") Long id,@Param("img_url") String img_url);

    //清空汇报记录
    int cleanReportRecordById(@Param("id")Long id);
}
