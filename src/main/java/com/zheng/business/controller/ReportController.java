package com.zheng.business.controller;

import com.zheng.business.accept.AddReportRuleAcc;
import com.zheng.business.accept.UpdateReportRecordAcc;
import com.zheng.business.annotations.AdminLogin;
import com.zheng.business.annotations.NormalLogin;
import com.zheng.business.bean.AnswerRet;
import com.zheng.business.bean.ListAndCount;
import com.zheng.business.bean.RrException;
import com.zheng.business.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.LinkedHashMap;

/**
 * author:zheng
 * Date:2022/2/915:21
 **/
@RestController
@Validated
public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * post
     * 增加汇报规则
     * 管理员权限
     * @param addReportRuleAcc
     * @return
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/addreportrule", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> addReportRule(@RequestBody @Valid AddReportRuleAcc addReportRuleAcc){
        try {
            reportService.insertReportRule(addReportRuleAcc,1);
        } catch (ParseException e) {
            throw new RrException(10006, "parse异常,增加汇报规则失败");
        }
        return new AnswerRet<String>(1, "增加汇报规则成功！");
    }

    // /**
    //  * get
    //  * 测试定时任务创建汇报记录的接口
    //  * @param id
    //  */
    // @ResponseBody
    // @GetMapping("/taddreportrec")
    // public void TaddReportRec(Long id) {
    //     try {
    //         reportService.autoAddReportRecord(id);
    //     } catch (ParseException e) {
    //         throw new RrException(10006, "时间的转换parse异常，增加汇报记录失败！");
    //     }
    // }

    /**
     * get
     * 删除汇报规则
     * @param id
     * @return
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @GetMapping("/deletereportrule")
    public AnswerRet<String> deleteReportRule(@NotNull Long id){
        reportService.deleteReportRule(id);
        return new AnswerRet<String>(1, "删除汇报规则成功！");
    }


    /**
     * post
     * 修改汇报规则
     * @param addReportRuleAcc
     * @return
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updatereportrule", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updateReportRule(@RequestBody @Valid AddReportRuleAcc addReportRuleAcc){
        //判断是否输入了id
        if(null==addReportRuleAcc.id)
            throw new RrException(10006, "汇报规则id输入异常！");
        reportService.updateReportRule(addReportRuleAcc);
        return new AnswerRet<String>(1, "修改汇报规则成功！");
    }

    /**
     * get
     * 查看可见的汇报规则列表
     * @param size
     * @param page
     * @return
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @GetMapping("/getreportruleList")
    public AnswerRet<ListAndCount> getReportRuleList(Integer size,Integer page){
        ListAndCount reportRuleList = reportService.getReportRuleList(page, size);
        return new AnswerRet<ListAndCount>(1, reportRuleList);
    }

    /**
     * get
     * 查看单个汇报规则
     * @param id
     * @return
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @GetMapping("/getreportrule")
    public AnswerRet<LinkedHashMap> getReportRule(@NotNull Long id){
        LinkedHashMap oneReportRule = reportService.getOneReportRule(id);
        return new AnswerRet<>(1, oneReportRule);
    }

    /**
     * post
     * bd提交汇报记录
     * @param updateReportRecord
     * @return
     */
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updatereportrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updateReportRecord(@RequestBody @Valid UpdateReportRecordAcc updateReportRecord){
        reportService.updateReportRecord(updateReportRecord);
        return new AnswerRet<>(1, "提交汇报记录成功");
    }



    /**
     * get
     * 查看汇报记录列表
     * @param input
     * @param atime
     * @param btime
     * @param page
     * @param size
     * @param status   1:提交未读 2:已读 null：全部  0:未提交
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/getreportrecordlist")
    public AnswerRet<ListAndCount> ugetReportRecordList(String input, Long atime, Long btime, Integer page, Integer size,Integer status){
        StringBuffer reportRecordListString = reportService.getReportRecordListString(input, atime, btime,status);
        ListAndCount canSeeReportRecordList = reportService.findCanSeeReportRecordList(page, size, reportRecordListString);
        return new AnswerRet<>(1, canSeeReportRecordList);
    }

    // /**
    //  * 查看汇报记录个数
    //  * @param input
    //  * @param atime
    //  * @param btime
    //  * @return
    //  */
    // @NormalLogin
    // @ResponseBody
    // @GetMapping("/getreportrecordlistcount")
    // public AnswerRet<Integer> ugetReportRecordListCount(String input,Long atime,Long btime){
    //     StringBuffer reportRecordListString = reportService.getReportRecordListString(input, atime, btime);
    //     Integer canSeeReportRecordCount = reportService.findCanSeeReportRecordCount(reportRecordListString);
    //     return new AnswerRet<>(1, canSeeReportRecordCount);
    // }

    /**
     * get
     * 查看单个汇报记录
     * @param id
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/getreportrecord")
    public AnswerRet<LinkedHashMap> getReportRecord(@NotNull Long id){
        LinkedHashMap reportRecordById = reportService.getReportRecordById(id);
        return new AnswerRet<>(1, reportRecordById);
    }

    /**
     * get
     * 已阅
     * @param id
     * @return
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @GetMapping("/readreport")
    public AnswerRet<String> readReport(@NotNull Long id){
        reportService.readReport(id);
        return new AnswerRet<>(1,"查看记录成功！");
    }
}
