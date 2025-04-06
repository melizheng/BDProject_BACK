package com.zheng.business.bean;

/**
 * 汇报中间表 bd与主管
 * Date:2022/1/2014:23
 **/
public class Report {
    private long creator_id;
    private long report_rule_id;
    private long reporter_id;

    public Report(long creator_id, long report_rule_id, long reporter_id) {
        this.creator_id = creator_id;
        this.report_rule_id = report_rule_id;
        this.reporter_id = reporter_id;
    }

    public Report() {
    }

    public long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(long creator_id) {
        this.creator_id = creator_id;
    }

    public long getReport_rule_id() {
        return report_rule_id;
    }

    public void setReport_rule_id(long report_rule_id) {
        this.report_rule_id = report_rule_id;
    }

    public long getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(long reporter_id) {
        this.reporter_id = reporter_id;
    }
}
