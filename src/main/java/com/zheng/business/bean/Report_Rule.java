package com.zheng.business.bean;

import com.zheng.business.accept.AddReportRuleAcc;

/**
 * 汇报规则
 * Date:2022/1/2014:34
 **/
public class Report_Rule {
    private long id;
    private long creator_id;
    private String rule_name;
    private String report_rate;
    private String report_date;
    private long start_time;
    private long end_time;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;

    public Report_Rule(long id, long creator_id, String rule_name, String report_rate, String report_date, long start_time, long end_time, java.sql.Timestamp create_time, java.sql.Timestamp update_time) {
        this.id = id;
        this.creator_id = creator_id;
        this.rule_name = rule_name;
        this.report_rate = report_rate;
        this.report_date = report_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.create_time = create_time;
        this.update_time = update_time;
    }
    //增加规则调用构造器
    public Report_Rule(AddReportRuleAcc addReportRuleAcc) {
        this.rule_name=addReportRuleAcc.getRule_name();
        this.report_date=addReportRuleAcc.getReport_date();
        this.report_rate=addReportRuleAcc.getReport_rate();
        this.start_time=addReportRuleAcc.getStart_time();
        this.end_time=addReportRuleAcc.getEnd_time();
    }
    //修改
    public Report_Rule(AddReportRuleAcc addReportRuleAcc,Long id) {
        this.id=id;
        this.rule_name=addReportRuleAcc.getRule_name();
        this.report_date=addReportRuleAcc.getReport_date();
        this.report_rate=addReportRuleAcc.getReport_rate();
        this.start_time=addReportRuleAcc.getStart_time();
        this.end_time=addReportRuleAcc.getEnd_time();
    }
    public Report_Rule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(long creator_id) {
        this.creator_id = creator_id;
    }

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getReport_rate() {
        return report_rate;
    }

    public void setReport_rate(String report_rate) {
        this.report_rate = report_rate;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
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

}
