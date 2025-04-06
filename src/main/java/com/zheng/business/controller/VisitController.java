package com.zheng.business.controller;

import com.zheng.business.accept.AddVisitAcc;
import com.zheng.business.annotations.AdminLogin;
import com.zheng.business.annotations.NormalLogin;
import com.zheng.business.bean.AnswerRet;
import com.zheng.business.bean.ListAndCount;
import com.zheng.business.bean.Visit;
import com.zheng.business.service.VisitService;
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
import java.util.LinkedHashMap;

/**
 * author:
 * Date:2022/2/716:42
 **/
@RestController
@Validated
public class VisitController {

    @Autowired
    VisitService visitService;
    /**
     * post
     * 增加拜访记录
     * @param addVisitAcc
     * @return
     */
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/addvisitrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> addVisitRecord(@RequestBody @Valid AddVisitAcc addVisitAcc){
        Visit visit=new Visit(addVisitAcc);
        Long aLong = visitService.addCustomVisit(visit, addVisitAcc.getUrl());
        return new AnswerRet<String>(1, aLong.toString());
    }

    // /**
    //  * post
    //  * 修改拜访记录
    //  * @param updateVisitAcc
    //  * @return
    //  */
    // @NormalLogin
    // @ResponseBody
    // @RequestMapping(value = "/updatevisitrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    // public AnswerRet<String> updateVisitRecord(@RequestBody @Valid UpdateVisitAcc updateVisitAcc){
    //     visitService.updateVisit(updateVisitAcc);
    //     return new AnswerRet<String>(1, "修改拜访记录成功！");
    // }


    /**
     * get
     * 查看拜访记录列表
     * @param input
     * @param status
     * @param page
     * @param size
     * @param atime
     * @param btime
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/getvisitlist")
    public AnswerRet<ListAndCount> getVisitList(String input, Integer status, Integer page, Integer size, Long atime, Long btime,Long custom_id,Long user_id,Integer read_status){
        StringBuffer canSeeVisitListString = visitService.findCanSeeVisitListString(input, status,atime,btime,custom_id,user_id,read_status);
        int type=1;
        if(null!=custom_id||null!=user_id){
            type=2;
        }
        ListAndCount canSeeVisitList = visitService.findCanSeeVisitList(page, size, canSeeVisitListString,type);
        return new AnswerRet<>(1,canSeeVisitList);
    }

    /**
     * get
     * 通过id查看拜访记录信息
     * @param id
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/getvisitbyid")
    public AnswerRet<LinkedHashMap> getVisitbyId(@NotNull Long id){
        LinkedHashMap visitbyId = visitService.findVisitbyId(id);
        return new AnswerRet<>(1,visitbyId);
    }

    /**
     * get
     * 已读拜访记录
     * @param id
     * @return
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @GetMapping("/readvisit")
    public AnswerRet<String> readVisits(@NotNull Long id){
        visitService.readVisit(id);
        return new AnswerRet<>(1,"查看拜访记录成功！");
    }

}
