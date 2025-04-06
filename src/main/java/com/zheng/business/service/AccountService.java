package com.zheng.business.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zheng.business.accept.AddAccountAcc;
import com.zheng.business.accept.UpdateCityAcc;
import com.zheng.business.accept.UpdateStatusAcc;
import com.zheng.business.accept.UserLoginAcc;
import com.zheng.business.bean.AnswerRet;
import com.zheng.business.bean.Bd_Account;
import com.zheng.business.bean.Bd_Account_City;
import com.zheng.business.bean.CityCode;
import com.zheng.business.bean.ListAndCount;
import com.zheng.business.bean.RrException;
import com.zheng.business.dao.Mapper;
import com.zheng.business.utils.JwtUtils;
import com.zheng.business.utils.Md5Utils;
import com.zheng.business.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 服务的实体类，供控制器调用
 * Date:2022/1/2017:59
 **/
@Service
public class AccountService {

    //mapper,服务通过mapper映射的查询数据库的方法来查询数据库
    //mapper是接口,mapper.xml相当于实现类
    //自动注入所以mapper接口需要注释mapper
    @Autowired
    Mapper mapper;
    @Autowired
    CitycodeService citycodeService;
    /**
     * 用户登录服务
     * @param account
     * @return HashMap 返回code,token,id
     */
    public AnswerRet<HashMap> userLogin(UserLoginAcc account){
        AnswerRet<HashMap> answerRet=new AnswerRet();
        Bd_Account bd_account=mapper.login(account);
        if(bd_account==null) {
            throw new RrException(20000,"账号或密码错误！");
        }
        else {
            if(bd_account.getStatus()!=1)
                throw new RrException(30004,"用户已被禁用！");
            answerRet.setCode(1);
            HashMap map=new HashMap();
            map.put("token",JwtUtils.generateToken(account.phone, account.account_type));
            map.put("id",bd_account.getId());
            map.put("name",bd_account.getName());
            answerRet.setMsg(map);
        }
        return answerRet;
    }

    /**
     * 增加用户  账号密码服务
     * @param addAccountAcc
     * @return 返回增加的用户的id
     */
    @Transactional
    public void addAccount(AddAccountAcc addAccountAcc){

        Bd_Account account=new Bd_Account(addAccountAcc.getName(),addAccountAcc.getPhone(),addAccountAcc.getPhone().substring(0, 6),addAccountAcc.getAccount_type(),UserUtils.getLoginUserId(),1);
        if(mapper.findAccount(account.getPhone(),account.getAccount_type())!=null)
            throw new RrException(20001,"已存在该账户!");
        account.setPassword(Md5Utils.MD5Encode(account.getPassword(),"utf-8",false)
        );
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        account.setCreate_time(now);
        account.setUpdate_time(now);
        mapper.insertAccount(account);
        JSONObject bd_account_city_json=new JSONObject();
        bd_account_city_json.put("uid", account.getId());
        bd_account_city_json.put("city_code",addAccountAcc.getCity_code());
        addAccountCity(bd_account_city_json);
    }

    /**
     * 通过手机号和账号类型查找用户是否存在
     * 目的：解析token时查看是否有该用户
     * @param   phone
     * @return   返回查找的用户的对象，存放id,status
     */
    public Bd_Account findAccount(String phone,int type){
        return mapper.findAccount(phone,type);
    }

    /**
     * 通过id查找用户
     * @param id
     * @return 返回查找的用户的对象，存放id,status,name,phone
     */
    public java.util.LinkedHashMap findAccountbyId(long id){
        LinkedHashMap accountbyId = mapper.findAccountbyId(id);
        if(null==accountbyId){
            throw new RrException(20002, "账号不存在!");
        }
        return accountbyId;
    }


    /**
     * 增加用户账号城市服务
     * @param bd_account_city_json
     */
    @Transactional
    public void addAccountCity(JSONObject bd_account_city_json){
        JSONArray city_code = bd_account_city_json.getJSONArray("city_code");
        Long uid = bd_account_city_json.getLong("uid");
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        //增加用户的城市属性
        for(int i=0;i<city_code.size();i++) {
            CityCode city = citycodeService.findCityname(city_code.getInteger(i));
            Bd_Account_City bd_account_city=new Bd_Account_City(uid,city.getCity_code(),city.getCity_name());
            bd_account_city.setCreate_time(now);
            bd_account_city.setUpdate_time(now);
            mapper.insertAccountCityCode(bd_account_city);
        }
    }

    /**
     * 删除账号信息  包括城市权限 汇报中间表等
     * @param id
     */
    @Transactional
    public void deleteAccountAndCity(long id){
        int i = mapper.deleteAccount(id);
        if(i==0) throw new RrException(20002, "账号不存在!");
        //修改对接的客户
        mapper.updateBdCustom(id);
    }

    /**
     * 修改自己的账号名称
     * @param name
     */
    @Transactional
    public void updateAccountName(String name){
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        int i = mapper.updateAccountName(UserUtils.getLoginUserId(),name,now);
        if(i==0) throw new RrException(20002, "账号不存在!");
    }

    /**
     * 修改账号密码
     * @param password
     */
    @Transactional
    public void updateAccountPassw(Long id,String password){
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        int i = mapper.updateAccountPassw(id,password,now);
        if(i==0) throw new RrException(20002, "账号不存在!");
    }

    /**
     * 修改账号状态  禁用
     * @param updatestatus
     */
    @Transactional
    public void updateAccountStatus(UpdateStatusAcc updatestatus){
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        updatestatus.setTime(now);
        int i = mapper.updateAccountStatus(updatestatus);
        if(i==0) throw new RrException(20002, "账号不存在!");
    }

    /**
     * 修改账号信息和城市属性  citycode1临时存放两者交集
     * @param updateCityAcc
     */
    @Transactional
    public void updateAccountMessage(UpdateCityAcc updateCityAcc){

        //判断是否有该账户并修改名称 没有则抛出异常
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        int x = mapper.updateAccountName(updateCityAcc.id,updateCityAcc.name,now);
        if(x==0)
            throw new RrException(20002, "账号不存在!");

        ArrayList<Integer> citycode=new ArrayList<>();
        ArrayList<Integer> citycode1=new ArrayList<>();
        for(int i=0;i<updateCityAcc.city_code.size();i++){
            citycode.add(updateCityAcc.city_code.getInteger(i));
            citycode1.add(updateCityAcc.city_code.getInteger(i));
        }
        ArrayList<Integer> accountAllCityCode = findAccountAllCityCode(updateCityAcc.id);
        //citycode1存放已有的城市属性、更新后的城市属性的交集
        citycode1.retainAll(accountAllCityCode);
        if(citycode1.size()!=0)
        {
            //已有的和新增的存在交集，把他移除   则accountAllCityCode剩下的为需要删除的
            accountAllCityCode.removeAll(citycode1);
            //已有的和新增的存在交集，把他移除   则citycode是即将新增的城市
            citycode.removeAll(citycode1);
        }
        //删除已有的和新增的完全不重合的城市属性
        for (int i=0;i<accountAllCityCode.size();i++){
            mapper.deleteAccountCityCode(updateCityAcc.id,accountAllCityCode.get(i));
        }
        //增加没有权限的城市
        JSONObject bd_account_city_json=new JSONObject();
        bd_account_city_json.put("uid", updateCityAcc.id);
        bd_account_city_json.put("city_code",JSONArray.parseArray(JSONObject.toJSONString(citycode)));
        addAccountCity(bd_account_city_json);
    }


    /**
     * 查看有权限的城市列表
     * @param id
     * @return  citycode的list
     */
    public ArrayList<Integer> findAccountAllCityCode(long id){
        ArrayList<Integer> accountAllCityCode = mapper.findAccountAllCityCode(id);
        if(accountAllCityCode.size()==0)
            throw new RrException(10007, "账号城市权限异常！");
        return accountAllCityCode;
    }

    /**
     * 查看有权限的城市列表
     * @param id
     * @return  citycode对象的list
     */
    public ArrayList<CityCode> findAccountAllCityCodeandName(long id){
        ArrayList<CityCode> accountAllCityCodeandName = mapper.findAccountAllCityCodeandName(id);
        if(accountAllCityCodeandName.size()==0) throw new RrException(10007, "账号城市权限异常！");
        else return accountAllCityCodeandName;
    }


    /**
     * 封装查看所有可见管理员的string语句
     * @param input
     * @return
     */
    public StringBuffer getAdminString(String input){
        StringBuffer condition=new StringBuffer();
        if(UserUtils.getLoginUsertype()==1) {
            //管理员得到所有管理员的信息
            condition.append(" where account_type=1");
        }
        else {
            //BD得到对应城市管理员的信息
            ArrayList<Integer> accountAllCityCode = findAccountAllCityCode(UserUtils.getLoginUserId());
            condition.append(",bd_account_city where account_type=1 and bd_account.id=bd_account_city.uid and city_code in(");
            for (int i=0;i<accountAllCityCode.size();i++){
                condition.append(accountAllCityCode.get(i));
                if(i==accountAllCityCode.size()-1)
                    condition.append(")");
                else
                    condition.append(",");
            }
        }
        if(null!=input){
            input=input.trim();
            if(input.length()>0)
                condition.append(" and (bd_account.name like '%"+input+"%' or bd_account.phone like '%"+input+"%')");
        }
        return condition;
    }

    /**
     * 封装查看所有可见BD的string语句
     * @param input
     * @return
     */
    public StringBuffer getBDString(String input,Integer citycode){
        StringBuffer condition=new StringBuffer();
        condition.append(",bd_account_city where account_type=2 and bd_account.id=bd_account_city.uid and city_code");
        ArrayList<Integer> accountAllCityCode = findAccountAllCityCode(UserUtils.getLoginUserId());
        //没有城市限制要求
        if(null==citycode){
            condition.append(" in(");
            for (int i=0;i<accountAllCityCode.size();i++){
                condition.append(accountAllCityCode.get(i));
                if(i==accountAllCityCode.size()-1)
                    condition.append(")");
                else
                    condition.append(",");
            }
        }
        //限制搜索单个城市
        else {
            if(accountAllCityCode.contains(citycode)){
                condition.append("="+citycode);
            }
            else throw new RrException(10004, "没有该城市权限！");
        }
        //搜索框输入内容
        if(null!=input){
            input=input.trim();
            if(input.length()>0)
                condition.append(" and (bd_account.name like '%"+input+"%' or bd_account.phone like '%"+input+"%')");
        }
        return condition;
    }

    /**
     * 查看可见的账号列表
     * @return
     */
    public ListAndCount findCanSeeAccountList(Integer page,Integer size,StringBuffer condition){
        int canSeeAccountCount = mapper.findCanSeeAccountCount(condition.toString());
        if(canSeeAccountCount==0) {
            return new ListAndCount(null, 0);
        }
        condition.append(" ORDER BY id");
        //分页显示
        if(null!=page&&null!=size){
            int begin=(page-1)*size;
            condition.append(" LIMIT "+begin+","+size);
        }
        ArrayList<LinkedHashMap> canSeeAccountList = mapper.findCanSeeAccountList(condition.toString());
        if(canSeeAccountList.size()==0)
            canSeeAccountList=null;
        return new ListAndCount(canSeeAccountList, canSeeAccountCount);

    }

    /**
     * 查看可见账号的个数
     * @param condition
     * @return
     */
    public int findCanSeeAccountCount(StringBuffer condition){
        return mapper.findCanSeeAccountCount(condition.toString());
    }

    /**
     * 得到项目的所有城市
     * @return
     */
    public ArrayList<CityCode> findProjectAllCity(){
        return mapper.findProjectAllCity();
    }

    /**
     * 得到BD的数据
     * @param id
     * @return
     */
    public int[] getBdEfficiency(long id){
        int[] ints = new int[4];
        ints[0] = mapper.CountBDVisits(id);
        ints[1] =mapper.CountBDReport(id);
        ints[2] =mapper.CountBDCustom(id);
        ints[3] =mapper.CountBDCustomcooperate(id);
        return ints;
    }

    /**
     * 得到首页的数据
     * @return
     */
    public int[] getHomeEfficiency(Integer city_code){
        int[] ints = new int[5];
        StringBuffer stringBuffer=new StringBuffer();
        if(UserUtils.getLoginUsertype()==1){
                //管理员查看  先得到可见的bd的id们
                ListAndCount canSeeAccountListAndCount = findCanSeeAccountList(null, null, getBDString(null, city_code));
                if(canSeeAccountListAndCount.getCount()==0){
                    stringBuffer.append(" in("+UserUtils.getLoginUserId()+")");
                }
                else {
                    ArrayList<LinkedHashMap> canSeeAccountList =canSeeAccountListAndCount.getData();
                    stringBuffer.append(" in(");
                    for(int i=0;i<canSeeAccountList.size();i++){
                        stringBuffer.append(canSeeAccountList.get(i).get("id"));
                        if(i==canSeeAccountList.size()-1)
                            stringBuffer.append(","+UserUtils.getLoginUserId()+")");
                        else
                            stringBuffer.append(",");
                    }
                }

            ints[0] = mapper.CountBDVisitsByApp(stringBuffer.toString());
            ints[1] =mapper.CountBDReportByApp(stringBuffer.toString());
            ints[2] =mapper.CountBDCustomByApp(stringBuffer.toString());
            ints[3] =mapper.CountBDCustomCooperateByApp(stringBuffer.toString());
            ints[4] =mapper.CountBDCustomAuditByApp(stringBuffer.toString());
        }
        else{
            ints[0] = mapper.CountBDVisits(UserUtils.getLoginUserId());
            ints[1] =mapper.CountBDReport(UserUtils.getLoginUserId());
            ints[2] =mapper.CountBDCustom(UserUtils.getLoginUserId());
            ints[3] =mapper.CountBDCustomcooperate(UserUtils.getLoginUserId());
            ints[4] =mapper.CountBDCustomAuditByApp("="+UserUtils.getLoginUserId());
        }
        return ints;
    }
}
