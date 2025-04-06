package com.zheng.business.controller;

import com.zheng.business.accept.AddCustomAcc;
import com.zheng.business.accept.AddCustomVisAcc;
import com.zheng.business.accept.UpdateCustomAcc;
import com.zheng.business.accept.UpdateCustomVisAcc;
import com.zheng.business.annotations.AdminLogin;
import com.zheng.business.annotations.NormalLogin;
import com.zheng.business.bean.AnswerRet;
import com.zheng.business.bean.Custom;
import com.zheng.business.bean.Custom_Visit;
import com.zheng.business.bean.ListAndCount;
import com.zheng.business.service.CustomService;
import com.zheng.business.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * author:zheng
 * Date:2022/1/2812:43
 **/
//RestController 所有返回的都不是网页，而是内容
//对非 java bean 参数的校验是 spring 框架“额外”提供的支持，需要用到 spring的@Validated注解
@RestController
@Validated
public class CustomController {
    @Autowired
    CustomService customService;


    /**
     * post
     * 增加拜访地址接口
     * @param addCustomVisAcc
     * @return
     */
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/addacustomvisit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> addCustomVisit(@RequestBody @Valid AddCustomVisAcc addCustomVisAcc){
        //增加拜访地址
        Custom_Visit custom_visit=new Custom_Visit(addCustomVisAcc);
        customService.addCustomVisit(custom_visit);
        return new AnswerRet<String>(1, "增加拜访地址成功！");
    }


    /**
     * get
     * 删除拜访地址
     * @param id
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/deletecustomvisit")
    public AnswerRet<String> deleteCustomVisit(@NotNull Long id){
        customService.deleteCustomVisit(id);
        return new AnswerRet<String>(1, "删除客户拜访地址成功！");
    }

    /**
     * get
     * 搜索查看客户拜访地址
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/getcustomvisitwentmakevisits")
    public AnswerRet<ArrayList<LinkedHashMap>> findCustomVisitListWentMakeVisits(String input){
        return new AnswerRet<>(1, customService.findCustomVisitListWentMakeVisits(input));
    }


    /**
     * get
     * 查看客户拜访地址列表 分页
     * @param id
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/getcustomvisit")
    public AnswerRet<ListAndCount> getCustomVisit(@NotNull Long id, Integer page, Integer size){
        return new AnswerRet<>(1, customService.findCustomVisit(id,page,size));
    }



    /**
     * get
     * 通过id查看拜访地址信息
     * @param id
     * @return
     */
    @NormalLogin
    @GetMapping("/getonecustomvisit")
    @ResponseBody
    public  AnswerRet<java.util.LinkedHashMap> getOneCustomVisit(@NotNull Long id){
        java.util.LinkedHashMap customVisit = customService.findCustomVisitbyId(id);
        return new AnswerRet<>(1, customVisit);
    }

    /**
     * post
     * 修改拜访地址接口
     * @param updateCustomVisAcc
     * @return
     */
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updatecustomvisit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updateCustomVisit(@RequestBody @Valid UpdateCustomVisAcc updateCustomVisAcc){
        //修改拜访地址
        Custom_Visit custom_visit=new Custom_Visit(updateCustomVisAcc);
        customService.updateCustomVisit(custom_visit);
        return new AnswerRet<String>(1, "修改拜访地址成功！");
    }


    /**
     * post
     * 增加客户接口  须带客户名称、地址、城市、坐标等
     * @param addCustomAcc
     * @return
     */
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/addacustom", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> addCustom(@RequestBody @Valid AddCustomAcc addCustomAcc){
        //得到活动该BD对象的uid
        Custom custom=new Custom(addCustomAcc);
        custom.setUser_id(UserUtils.getLoginUserId());
        //增加客户表的客户，同时得到拜访地址，在拜访地址表增加地址
        Custom_Visit custom_visit = customService.addCustom(custom);
        custom_visit.setContract_name(addCustomAcc.getContract_name());
        custom_visit.setPhone(addCustomAcc.getPhone());
        custom_visit.setPosition(addCustomAcc.getPosition());
        customService.addCustomVisit(custom_visit);
        return new AnswerRet<String>(1, "增加客户成功！");
    }

    /**
     * post
     * 修改客户信息接口
     * @param updateCustomAcc
     * @return
     */
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updatecustom", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updateCustom(@RequestBody @Valid UpdateCustomAcc updateCustomAcc){
        //修改拜访地址
        Custom custom=new Custom(updateCustomAcc);
        customService.updateCustom(custom);
        return new AnswerRet<String>(1, "修改客户信息成功！");
    }

    /**
     * get
     * 修改备注
     * @param id
     * @param remark
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/updatecustomremark")
    public AnswerRet<String> updateCustomRemark(@NotNull Long id,String remark){
        customService.updateCustomRemark(id, remark);
        return new AnswerRet<String>(1, "修改客户备注成功！");
    }

    /**
     * get
     * 修改客户合作状态
     * @param id
     * @param status
     * @return
     */
    @NormalLogin
    @AdminLogin
    @ResponseBody
    @GetMapping("/updatecustomstatus")
    public AnswerRet<String> updateCustomStatus(@NotNull Long id,@Max(value = 2) @Min(value = 1)Integer status){
        customService.updateCustomStatus(id,status, new Timestamp(System.currentTimeMillis()));
        return new AnswerRet<String>(1, "修改客户状态成功！");
    }

    /**
     * get
     * 修改客户对接人
     * @param id
     * @param bd
     * @return
     */
    @NormalLogin
    @AdminLogin
    @ResponseBody
    @GetMapping("/updatecustomtobd")
    public AnswerRet<String> updateCustomBD(@NotNull Long id,@NotNull Long bd){
        customService.updateCustomBD(id,bd, new Timestamp(System.currentTimeMillis()));
        return new AnswerRet<String>(1, "修改客户对接人成功！");
    }

    /**
     * get
     * 得到可见客户列表
     * @param page
     * @param size
     * @param input
     * @param citycode
     * @param status
     * @return
     */
    @NormalLogin
    @ResponseBody
    @GetMapping("/getcustom")
    public AnswerRet<ListAndCount> getCustom(Integer page,Integer size,String input,Integer citycode,Integer status,Long user_id){
        StringBuffer customString = customService.getCustomString(input, citycode,status,user_id);
        ListAndCount custom = customService.findCustom(page, size, customString);
        return new AnswerRet<>(1, custom);

    }


    /**
     * get
     * 通过id查看客户信息
     * @param id
     * @return
     */
    @NormalLogin
    @GetMapping("/getonecustom")
    @ResponseBody
    public  AnswerRet<java.util.LinkedHashMap> getonecustom(@NotNull Long id){
        java.util.LinkedHashMap custom = customService.findCustombyId(id);
        return new AnswerRet<>(1, custom);
    }

    /**
     *
     * get
     * 申请审核该公司
     * @param custom_id
     * @return
     */
    @NormalLogin
    @GetMapping("/addaudit")
    @ResponseBody
    public  AnswerRet<String> addAudit(@NotNull Long custom_id){
        customService.addCustomAudit(custom_id);
        return new AnswerRet<>(1, "申请审核成功！");
    }


    /**
     * get
     * 判断审核结果
     * @param audit_id
     * @param status
     * @return
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/updateaudit")
    @ResponseBody
    public  AnswerRet<String> updateAudit(@NotNull Long audit_id,@NotNull Integer status){
        customService.changeCustomAuditStatus(audit_id,status);
        return new AnswerRet<>(1, "修改审核结果成功！");
    }


    /**
     * get
     * 查看审核列表 包含可见个数
     * @param page
     * @param size
     * @param input
     * @return
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/findauditlist")
    @ResponseBody
    public  AnswerRet<ListAndCount> findAuditList(Integer page,Integer size,String input){
        StringBuffer customAuditString = customService.getCustomAuditString(input);
        ListAndCount customAudit = customService.findCustomAudit(page, size, customAuditString);
        return new AnswerRet<>(1, customAudit);
    }

}
