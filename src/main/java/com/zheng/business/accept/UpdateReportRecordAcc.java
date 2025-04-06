package com.zheng.business.accept;

import javax.validation.constraints.NotNull;

/**
 * author:
 * Date:2022/2/1115:26
 **/
public class UpdateReportRecordAcc {
    @NotNull
    Long id;
    String work_summary;
    String problem;

    public Long getId() {
        return id;
    }

    public String getWork_summary() {
        return work_summary;
    }

    public String getProblem() {
        return problem;
    }

    public UpdateReportRecordAcc(@NotNull Long id, String work_summary, String problem) {
        this.id = id;
        this.work_summary = work_summary;
        this.problem = problem;
    }
}
