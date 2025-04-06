package com.zheng.business.service;

import com.zheng.business.bean.Audit;
import com.zheng.business.bean.Custom;
import com.zheng.business.bean.Custom_Visit;
import com.zheng.business.bean.ListAndCount;
import com.zheng.business.bean.RrException;
import com.zheng.business.bean.Visit;
import com.zheng.business.dao.CustomMapper;
import com.zheng.business.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 服务的实体类，供控制器调用
 * Date:2022/1/2812:10
 **/
@Service
@Transactional
public class CustomService {
    @Autowired
    CustomMapper customMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    CitycodeService citycodeService;

    /**
     * 增加客户拜访地址
     * @param custom_visit
     */
    public void addCustomVisit(Custom_Visit custom_visit){
        findCustombyId(custom_visit.getCustom_id());
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        custom_visit.setCreate_time(now);
        custom_visit.setUpdate_time(now);
        if(customMapper.insertCustomVisit(custom_visit)!=1){
            throw new RrException(10006, "数据库增加拜访地址操作异常！");
        }
    }

    /**
     * 修改客户拜访地址
     * @param custom_visit
     */
    public void updateCustomVisit(Custom_Visit custom_visit){
        // CityCode city = citycodeService.findCityname(custom_visit.getCity_code());
        // custom_visit.setCity_name(city.getCity_name());
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        custom_visit.setUpdate_time(now);
        if(customMapper.updateCustomVisit(custom_visit)!=1){
            throw new RrException(10006, "数据库修改拜访地址失败！");
        }
    }

    /**
     * 删除拜访地址
     * @param id
     */
    public void deleteCustomVisit(long id){
        if(customMapper.deleteCustomVisit(id)!=1){
            throw new RrException(10006, "不存在该拜访地址");
        }
    }

    /**
     * 展示所有客户的所有拜访地址
     * @return
     */
    public ArrayList<LinkedHashMap> findCustomVisitListWentMakeVisits(String input){
        StringBuffer stringBuffer=new StringBuffer();
        if(input!=null&&input.trim().length()>0)
            stringBuffer.append("and custom.company_name like '%"+input+"%'");
        ArrayList<LinkedHashMap> customVisit = customMapper.findCustomVisitListWentMakeVisits(UserUtils.getLoginUserId(),stringBuffer.toString());
        if(customVisit.size()==0)
            customVisit=null;
        else{
            for (int i=0;i<customVisit.size();i++){
                if(customVisit.get(i).get("detail_address")!=null&&customVisit.get(i).get("detail_address").toString().trim().length()>0)
                    customVisit.get(i).put("address",customVisit.get(i).get("address").toString().trim()+customVisit.get(i).get("detail_address").toString().trim());
            }
        }
        return customVisit;
    }


    /**
     * 展示客户的拜访地址
     * @param id
     * @return
     */
    public ListAndCount findCustomVisit(Long id,Integer page,Integer size){
        int customVisitCount = findCustomVisitCount(id);
        if(customVisitCount==0){
            return new ListAndCount(null, 0);
        }
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(" ORDER BY id");
        //分页显示
        if(null!=page&&null!=size){
            int begin=(page-1)*size;
            stringBuffer.append(" LIMIT "+begin+","+size);
        }
        ArrayList<LinkedHashMap> customVisit = customMapper.findCustomVisit(id,stringBuffer.toString());
        if(customVisit.size()==0)
            customVisit=null;
        return new ListAndCount(customVisit, customVisitCount);
    }

    /**
     * 拜访地址个数
     * @param id
     * @return
     */
    public int findCustomVisitCount(long id){
        return customMapper.findCustomVisitCount(id);
    }

    /**
     * 查看单个拜访地址
     * @param id
     * @return
     */
    public java.util.LinkedHashMap findCustomVisitbyId(long id){
        LinkedHashMap customVisitbyId = customMapper.findCustomVisitbyId(id);
        if(null==customVisitbyId){
            throw new RrException(20002, "拜访地址不存在!");
        }
        return customVisitbyId;
    }

    /**
     * 查看单个拜访地址坐标
     * @param id
     * @return
     */
    public Visit findCustomVisitzuobiaobyId(long id){
        Visit xandy = customMapper.findCustomVisitzuobiaobyId(id);
        if(null==xandy){
            throw new RrException(20002, "拜访地址不存在!");
        }
        return xandy;

    }

    /**
     * 增加客户信息
     * @param custom
     * @return
     */
    public Custom_Visit addCustom(Custom custom){
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        custom.setCreate_time(now);
        custom.setUpdate_time(now);
        custom.setStatus(2);
        int i = customMapper.insertCustom(custom);
        if(i!=1){
            throw new RrException(10006, "增加客户失败");
        }
        Custom_Visit custom_visit=new Custom_Visit(custom);
        return custom_visit;
    }

    /**
     * 修改客户信息
     * @param custom
     */
    public void updateCustom(Custom custom){
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        custom.setUpdate_time(now);
        if(customMapper.updateCustom(custom)!=1){
            throw new RrException(10006, "数据库修改客户信息失败！");
        }
    }


    /**
     * 修改客户状态
     * @param id
     * @param status
     * @param time
     */
    @Transactional
    public void updateCustomStatus(long id,int status,java.sql.Timestamp time){
        if(customMapper.updateCustomStatus(id,status,time)!=1){
            throw new RrException(10006, "数据库修改客户状态异常！");
        }
    }

    /**
     * 修改客户对接人
     * @param id
     * @param bd
     * @param time
     */
    public void updateCustomBD(long id,long bd,java.sql.Timestamp time){
        //判断是否有该bd
        accountService.findAccountbyId(bd);
        if(customMapper.updateCustomBD(id,bd,time)!=1){
            throw new RrException(10006, "数据库修改客户对接人异常！！");
        }
    }


    /**
     * 修改客户备注
     * @param id
     * @param remark
     */
    public void updateCustomRemark(long id,String remark){
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        if(customMapper.updateCustomRemark(id,remark,now)!=1){
            throw new RrException(10006, "修改客户备注异常！！");
        }
    }


    /**
     * 得到客户的string语句
     * @param input
     * @param citycode
     * @param status
     * @return
     */
    public StringBuffer getCustomString(String input,Integer citycode,Integer status,Long user_id){
        StringBuffer stringBuffer=new StringBuffer();
        if(UserUtils.getLoginUsertype()==1){
            if(null==user_id){
                //管理员查看  先得到可见的bd的id们
                ListAndCount canSeeAccountListAndCount = accountService.findCanSeeAccountList(null, null, accountService.getBDString(null, citycode));
                if(canSeeAccountListAndCount.getCount()==0){
                    stringBuffer.append("and user_id in("+UserUtils.getLoginUserId()+")");
                }
                else {
                    ArrayList<LinkedHashMap> canSeeAccountList =canSeeAccountListAndCount.getData();
                    stringBuffer.append("and user_id in(");
                    for(int i=0;i<canSeeAccountList.size();i++){
                        stringBuffer.append(canSeeAccountList.get(i).get("id"));
                        if(i==canSeeAccountList.size()-1)
                            stringBuffer.append(","+UserUtils.getLoginUserId()+")");
                        else
                            stringBuffer.append(",");
                    }
                }
                if(null!=input){
                    input=input.trim();
                    if(input.length()>0)
                        stringBuffer.append(" and (custom.company_name like '%"+input+"%' or bd_account.phone like '%"+input+"%' or bd_account.name like '%"+input+"%')");
                }
            }
            else {
                stringBuffer.append("and user_id="+user_id);
                if(null!=input){
                    input=input.trim();
                    if(input.length()>0)
                        stringBuffer.append(" and custom.company_name like '%"+input+"%'");
                }
            }
        }else {
            //BD查看搜索公司名称
            stringBuffer.append("and user_id="+UserUtils.getLoginUserId());
            if(null!=input){
                input=input.trim();
                if(input.length()>0)
                    stringBuffer.append(" and custom.company_name like '%"+input+"%'");
            }
            if(null!=citycode)
                stringBuffer.append( " and custom.city_code="+citycode);
        }
        if(null!=status){
            stringBuffer.append(" and custom.status="+status);
        }
        return stringBuffer;
    }

    /**
     * 得到可见客户列表
     * @param page
     * @param size
     * @param condition
     * @return
     */
    public ListAndCount findCustom(Integer page,Integer size,StringBuffer condition){
        if(condition==null) {
            return new ListAndCount(null, 0);
        }
        int customCount = findCustomCount(condition);
        if(customCount==0){
            return new ListAndCount(null, 0);
        }
        condition.append(" ORDER BY custom.id desc");
        //分页显示
        if(null!=page&&null!=size){
            int begin=(page-1)*size;
            condition.append(" LIMIT "+begin+","+size);
        }
        //System.out.println(condition.toString());
        ArrayList<LinkedHashMap> custom = customMapper.findCustom(condition.toString());
        if(custom.size()==0)
            custom=null;
        return new ListAndCount(custom, customCount);

    }

    /**
     * 可见客户个数
     * @param condition
     * @return
     */
    public int findCustomCount(StringBuffer condition){
        return customMapper.findCustomCount(condition.toString());
    }


    /**
     * 单个客户信息查看
     * @param id
     * @return
     */
    public LinkedHashMap findCustombyId(Long id){
        LinkedHashMap custombyId = customMapper.findCustombyId(id);
        if(null==custombyId){
            throw new RrException(20002, "客户不存在!");
        }
        return custombyId;
    }

    /**
     * bd增加公司审核记录
     * @param custom_id
     */
    public void addCustomAudit(long custom_id){
        //判断该公司是否存在
        LinkedHashMap custombyId = findCustombyId(custom_id);
        if(Integer.parseInt(custombyId.get("status").toString())==3){
            //存在在审核的情况
            throw new RrException(20002, "该客户在审核中!");
        }
        //创建audit表的记录
        Audit audit = new Audit(custom_id, UserUtils.getLoginUserId(), 0);
        int i = customMapper.insertAudit(audit);
        if(i!=1){
            throw new RrException(20002, "插入审核表异常!");
        }
        //修改公司的状态至审核中
        updateCustomStatus(custom_id, 3, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * 管理员审核公司合作状态
     * @param audit_id
     * @param status  1：同意 2：驳回
     */
    public void changeCustomAuditStatus(long audit_id,int status){
        Audit auditById = customMapper.findAuditById(audit_id);
        if(null==auditById)
            throw new RrException(20002, "审核申请不存在!");
        //修改该公司的状态
        if(auditById.getStatus()!=0){
            throw new RrException(20002, "审核已完成，无法修改!");
        }
        updateCustomStatus(auditById.getCustom_id(), status,new Timestamp(System.currentTimeMillis()));
        int i = customMapper.updateAudit(audit_id, status);
        if(i!=1){
            throw new RrException(20002, "修改审核表异常!");
        }
    }

    public StringBuffer getCustomAuditString(String input){
        StringBuffer stringBuffer=new StringBuffer();
            //管理员查看  先得到可见的bd的id们
            ListAndCount canSeeAccountListAndCount = accountService.findCanSeeAccountList(null, null, accountService.getBDString(null, null));
            if(canSeeAccountListAndCount.getCount()==0){
                stringBuffer.append("and audit.user_id in(");
                stringBuffer.append(UserUtils.getLoginUserId());
                stringBuffer.append(")");
            }
            else {
                ArrayList<LinkedHashMap> canSeeAccountList =canSeeAccountListAndCount.getData();
                stringBuffer.append("and audit.user_id in(");
                for(int i=0;i<canSeeAccountList.size();i++){
                    stringBuffer.append(canSeeAccountList.get(i).get("id"));
                    if(i==canSeeAccountList.size()-1)
                        stringBuffer.append(","+UserUtils.getLoginUserId()+")");
                    else
                        stringBuffer.append(",");
                }
            }
            if(null!=input){
                input=input.trim();
                if(input.length()>0)
                    stringBuffer.append(" and (custom.company_name like '%"+input+"%' or bd_account.phone like '%"+input+"%' or bd_account.name like '%"+input+"%')");
            }
        return stringBuffer;

    }

    /**
     * 查看审核列表
     * @param page
     * @param size
     * @param condition
     * @return
     */
    public ListAndCount findCustomAudit(Integer page,Integer size,StringBuffer condition){
        if(condition==null) {
            return new ListAndCount(null, 0);
        }
        int AuditListCount = customMapper.findAuditListCount(condition.toString());
        if(AuditListCount==0){
            return new ListAndCount(null, 0);
        }
        condition.append(" ORDER BY audit.id desc");
        //分页显示
        if(null!=page&&null!=size){
            int begin=(page-1)*size;
            condition.append(" LIMIT "+begin+","+size);
        }

        ArrayList<LinkedHashMap> AuditList = customMapper.findAuditList(condition.toString());
        if(AuditList.size()==0)
            AuditList=null;
        return new ListAndCount(AuditList, AuditListCount);
    }
}
