package com.zheng.business.accept;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author:
 * Date:2022/2/914:54
 **/
public class AddReportRuleAcc {
    public Long id;
    @NotBlank
    String rule_name;
    @NotBlank
    String report_rate;
    @NotBlank
    String report_date;
    @NotNull
    Long start_time;
    @NotNull
    Long end_time;
    @NotBlank
    String reporter_ids;

    public Long getId() {
        return id;
    }

    public String getRule_name() {
        return rule_name;
    }

    public String getReport_rate() {
        return report_rate;
    }

    public String getReport_date() {
        return report_date;
    }

    public Long getStart_time() {
        return start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public String getReporter_ids() {
        return reporter_ids;
    }
}
