package com.zheng.business.controller;

import com.zheng.business.accept.AddAccountAcc;
import com.zheng.business.accept.UpdateCityAcc;
import com.zheng.business.accept.UpdateStatusAcc;
import com.zheng.business.accept.UserLoginAcc;
import com.zheng.business.annotations.AdminLogin;
import com.zheng.business.annotations.NormalLogin;
import com.zheng.business.bean.AnswerRet;
import com.zheng.business.bean.CityCode;
import com.zheng.business.bean.ListAndCount;
import com.zheng.business.bean.RrException;
import com.zheng.business.service.AccountService;
import com.zheng.business.utils.Md5Utils;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//RestController 所有返回的都不是网页，而是内容
//对非 java bean 参数的校验是 spring 框架“额外”提供的支持，需要用到 spring的@Validated注解
@RestController
@Validated
public class UserController {

    //new一个服务的实现类，利用服务的方法来得到数据
    //自动注入，服务的实现类需要注释为service
    @Autowired
    AccountService accountService;

    /**
     * post
     * 用户账号登录请求接口  须带账号、密码、账号类型
     * 无权限限制
     * @param userLoginAcc
     * @return LoginRet
     */
    @ResponseBody
    @RequestMapping(value = "/userlogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<HashMap> userLogin(@RequestBody @Valid UserLoginAcc userLoginAcc){
        return accountService.userLogin(userLoginAcc);
    }

    /**
     * post
     * 增加用户账号接口  须带姓名、账号、密码、账号类型、城市属性
     * 权限：管理员
     * @param addAccountAcc
     * @return AnswerRet
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/addaccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> addAccount(@RequestBody @Valid AddAccountAcc addAccountAcc){
        //增加账号密码，得到活动该对象的uid
        accountService.addAccount(addAccountAcc);
        return new AnswerRet<String>(1, "增加账号成功！");
    }

    /**
     * get
     * 删除账号+城市权限
     * 权限：管理员
     * @param id
     * @return AnswerRet
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/deleteaccount")
    @ResponseBody
    public AnswerRet<String> deleteAccount(@NotNull @Min(value = 1)Long id){
        accountService.deleteAccountAndCity(id);
        return new AnswerRet<String>(1, "删除账号成功！");
    }

    /**
     * 更新账号姓名
     * get
     * 权限：登录用户
     * @param name
     * @return AnswerRet
     */
    @NormalLogin
    @ResponseBody
    @GetMapping(value = "/updatename")
    public AnswerRet<String> updateName(@NotBlank  @Size(min=3,max=20) String name){
        accountService.updateAccountName(name);
        return new AnswerRet<String>(1, "更新账号姓名成功！");
    }

    /**
     * post
     * 更新账号密码
     * 权限：登录用户
     * @param jpson
     * @return AnswerRet
     */
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updatepassw", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updatePassw(@RequestBody Map jpson){
        if(null==jpson) throw new RrException(10003, "传入对象不能为空!");
        if(null==jpson.get("password")) throw new RrException(20003, "密码不能为空！");
        accountService.updateAccountPassw(UserUtils.getLoginUserId(),jpson.get("password").toString());
        return new AnswerRet<String>(1, "更新账号密码成功！");
    }

    /**
     * post
     * 更新账号密码
     * 权限：管理员重置
     * @param jpson
     * @return AnswerRet
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updatepasswbyadmin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updatePasswByAdmin(@RequestBody Map jpson){
        if(null==jpson) throw new RrException(10003, "传入对象不能为空!");
        if(null==jpson.get("id")) throw new RrException(20003, "id不能为空！");
        if(null==jpson.get("phone")) throw new RrException(20003, "phone不能为空！");
        String password = Md5Utils.MD5Encode(jpson.get("phone").toString().substring(0, 6), "utf-8", false);
        accountService.updateAccountPassw(Long.parseLong(jpson.get("id").toString()),password);
        return new AnswerRet<String>(1, "更新账号密码成功！");
    }

    /**
     * post
     * 更新账号状态
     * 权限：管理员
     * @param updatestatus
     * @return AnswerRet
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updatestatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updateStatus(@RequestBody @Valid UpdateStatusAcc updatestatus){
        accountService.updateAccountStatus(updatestatus);
        return new AnswerRet<String>(1, "更新账号状态成功！");
    }

    /**
     * post
     * 更新账号信息，包括姓名、城市
     * 权限：管理员
     * @param updateCityAcc
     * @return AnswerRet
     */
    @AdminLogin
    @NormalLogin
    @ResponseBody
    @RequestMapping(value = "/updateaccountmessage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public AnswerRet<String> updateAccountMessage(@RequestBody @Valid UpdateCityAcc updateCityAcc){
        accountService.updateAccountMessage(updateCityAcc);
        return new AnswerRet<String>(1, "更新账号信息成功！");
    }

    /**
     * get
     * 得到自己拥有权限的城市列表
     * 权限：登录用户
     * @return AnswerRet<ArrayList<Integer>>
     */
    @NormalLogin
    @GetMapping("/getallcitycode")
    @ResponseBody
    public AnswerRet<ArrayList<CityCode>> getMyAllCityCode(){
        long id = UserUtils.getLoginUserId();
        ArrayList<CityCode> accountAllCityCode = accountService.findAccountAllCityCodeandName(id);
        return new AnswerRet<ArrayList<CityCode>>(1,accountAllCityCode);
    }

    /**
     * get
     * 得到某用户拥有权限的城市列表
     * 权限：登录用户
     * @return AnswerRet<ArrayList<Integer>>
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/getaccountallcitycode")
    @ResponseBody
    public AnswerRet<ArrayList<CityCode>> getAccountAllCityCode(@NotNull Long id){
        accountService.findAccountbyId(id);
        ArrayList<CityCode> accountAllCityCode = accountService.findAccountAllCityCodeandName(id);
        return new AnswerRet<ArrayList<CityCode>>(1,accountAllCityCode);
    }

    /**
     * get
     * 得到可见的管理员信息列表   input 为输入查找项
     * @return AnswerRet<ArrayList<HashMap>>
     */
    @NormalLogin
    @GetMapping("/getadmin")
    @ResponseBody
    public AnswerRet<ListAndCount> getAdmin(Integer page,Integer size,String input){
        ListAndCount admin = accountService.findCanSeeAccountList(page, size, accountService.getAdminString(input));
        return new AnswerRet<>(1, admin);
    }


    /**
     * get
     * 得到可见的管理员个数 input 为输入查找项
     * @return
     */
    @NormalLogin
    @GetMapping("/getadmincount")
    @ResponseBody
    public AnswerRet<Integer> getAdmin(String input){
        int adminCount = accountService.findCanSeeAccountCount(accountService.getAdminString(input));
        return new AnswerRet<>(1,adminCount);
    }

    /**
     * get
     * 得到可见的BD信息列表   input 为输入查找项 citycode为限制的城市
     * @return AnswerRet<ArrayList<HashMap>>
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/getbd")
    @ResponseBody
    public AnswerRet<ListAndCount> getBD(Integer page,Integer size,String input,Integer citycode){
        ListAndCount bd = accountService.findCanSeeAccountList(page, size, accountService.getBDString(input, citycode));
        return new AnswerRet<>(1, bd);
    }
    /**
     * get
     * 得到可见的BD个数   input 为输入查找项 citycode为限制的城市
     * @return AnswerRet<ArrayList<HashMap>>
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/getbdcount")
    @ResponseBody
    public  AnswerRet<Integer> getBDCount(String input,Integer citycode){
        int bdAccountCount = accountService.findCanSeeAccountCount(accountService.getBDString(input,citycode));
        return new AnswerRet<>(1, bdAccountCount);
    }

    /**
     * get
     * 通过id查看BD账号信息 城市属性，公司总数，拜访次数，汇报次数
     * @param id
     * @return
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/getonebd")
    @ResponseBody
    public  AnswerRet<java.util.LinkedHashMap> getonebd(@NotNull Long id){
        java.util.LinkedHashMap account = accountService.findAccountbyId(id);
        ArrayList<CityCode> accountAllCityCodeandName = accountService.findAccountAllCityCodeandName(id);
        //增加用户的城市权限
        account.put("city",accountAllCityCodeandName);
        //得到用户的拜访次数，汇报次数，公司个数，合作个数
        int[] bdEfficiency = accountService.getBdEfficiency(id);
        account.put("visitscount", bdEfficiency[0]);
        account.put("reportscount", bdEfficiency[1]);
        account.put("customcount", bdEfficiency[2]);
        account.put("customcooperatecount", bdEfficiency[3]);
        return new AnswerRet<>(1, account);
    }

    /**
     * get
     * APP首页
     * @return
     */
    @NormalLogin
    @GetMapping("/getapphome")
    @ResponseBody
    public  AnswerRet<java.util.LinkedHashMap> getAppHome(Integer city_code){
        //得到用户的拜访次数，汇报次数，公司个数，合作个数
        java.util.LinkedHashMap account = new LinkedHashMap();
        int[] homeEfficiency = accountService.getHomeEfficiency(city_code);
        account.put("visitscount", homeEfficiency[0]);
        account.put("reportscount", homeEfficiency[1]);
        account.put("customcount", homeEfficiency[2]);
        account.put("customCooperateCount", homeEfficiency[3]);
        account.put("customAuditCount", homeEfficiency[4]);
        return new AnswerRet<>(1, account);
    }

    /**
     * get
     * 得到项目的所有城市
     * @return
     */
    @AdminLogin
    @NormalLogin
    @GetMapping("/findprojectallcity")
    @ResponseBody
    public  AnswerRet<ArrayList<CityCode>> findProjectAllCity(){
        ArrayList<CityCode> projectAllCity = accountService.findProjectAllCity();
        return new AnswerRet<>(1, projectAllCity);
    }
}
