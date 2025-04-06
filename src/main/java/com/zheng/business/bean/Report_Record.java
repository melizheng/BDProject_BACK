package com.zheng.business.bean;

import com.zheng.business.accept.UpdateReportRecordAcc;

import java.sql.Timestamp;

/**
 * 汇报记录
 * Date:2022/1/2014:29
 **/
public class Report_Record {
    private long id;
    private long rule_id;
    private String rule_name;
    private String report_rate;
    private long reporter_id;
    private String reporter_name;
    private long director_id;
    private String director_name;
    private String work_summary;
    private String problem;
    private String img_url;
    private int status;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;
    private long visit_total;
    private java.sql.Timestamp start_time;
    private java.sql.Timestamp end_time;
    private java.sql.Timestamp submittime;
    private java.sql.Timestamp deadline;

    public Report_Record() {
    }
    public Report_Record(Report_Rule report_rule) {
        this.rule_id=report_rule.getId();
        this.rule_name=report_rule.getRule_name();
        this.report_rate=report_rule.getReport_rate();
        this.director_id=report_rule.getCreator_id();
    }
    public Report_Record(UpdateReportRecordAcc updateReportRecordAcc) {
        this.id=updateReportRecordAcc.getId();
        this.work_summary=updateReportRecordAcc.getWork_summary();
        this.problem=updateReportRecordAcc.getProblem();
    }
    public Timestamp getSubmittime() {
        return submittime;
    }

    public void setSubmittime(Timestamp submittime) {
        this.submittime = submittime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRule_id() {
        return rule_id;
    }

    public void setRule_id(long rule_id) {
        this.rule_id = rule_id;
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

    public long getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(long reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getReporter_name() {
        return reporter_name;
    }

    public void setReporter_name(String reporter_name) {
        this.reporter_name = reporter_name;
    }

    public long getDirector_id() {
        return director_id;
    }

    public void setDirector_id(long director_id) {
        this.director_id = director_id;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

    public String getWork_summary() {
        return work_summary;
    }

    public void setWork_summary(String work_summary) {
        this.work_summary = work_summary;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public long getVisit_total() {
        return visit_total;
    }

    public void setVisit_total(long visit_total) {
        this.visit_total = visit_total;
    }

    public java.sql.Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(java.sql.Timestamp start_time) {
        this.start_time = start_time;
    }

    public java.sql.Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(java.sql.Timestamp end_time) {
        this.end_time = end_time;
    }

    public java.sql.Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(java.sql.Timestamp deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Report_Record{" +
                "id=" + id +
                ", rule_id=" + rule_id +
                ", rule_name='" + rule_name + '\'' +
                ", report_rate='" + report_rate + '\'' +
                ", reporter_id=" + reporter_id +
                ", reporter_name='" + reporter_name + '\'' +
                ", director_id=" + director_id +
                ", director_name='" + director_name + '\'' +
                ", work_summary='" + work_summary + '\'' +
                ", problem='" + problem + '\'' +
                ", img_url='" + img_url + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", visit_total=" + visit_total +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", submittime=" + submittime +
                ", deadline=" + deadline +
                '}';
    }
}
