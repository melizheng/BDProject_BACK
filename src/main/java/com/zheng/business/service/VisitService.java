package com.zheng.business.service;

import com.zheng.business.bean.ListAndCount;
import com.zheng.business.bean.RrException;
import com.zheng.business.bean.Visit;
import com.zheng.business.dao.VisitMapper;
import com.zheng.business.utils.ObjectUtils;
import com.zheng.business.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * author:
 * Date:2022/2/716:41
 **/
@Service
public class VisitService {
    @Autowired
    VisitMapper visitMapper;
    @Autowired
    CustomService customService;
    @Autowired
    AccountService accountService;

    /**
     * bd增加客户拜访记录
     * @param visit
     */
    @Transactional
    public Long addCustomVisit(Visit visit, String visit_img){
        //得到BD的名称
        LinkedHashMap BDaccount = accountService.findAccountbyId(UserUtils.getLoginUserId());
        visit.setUser_id(UserUtils.getLoginUserId());
        visit.setUser_name(BDaccount.get("name").toString());
        //通过拜访地址对应的客户id
        //可能抛出不存在拜访地址的异常
        LinkedHashMap custombyId = customService.findCustomVisitbyId(visit.getVisit_id());
        String custom_id = custombyId.get("custom_id").toString();
        visit.setCustom_id(Long.parseLong(custom_id));
        //存入当前时间
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        visit.setCreate_time(now);
        visit.setUpdate_time(now);
        //判断坐标，判断打卡状态
        Visit xandy = customService.findCustomVisitzuobiaobyId(visit.getVisit_id());
        //坐标误差在1以内
        if(Math.abs(xandy.getLatitude()-visit.getLatitude())<0.0001&&Math.abs(xandy.getLongitude()-visit.getLongitude())<0.0001){
            visit.setStatus(1);
        }else
            visit.setStatus(2);
        if(visitMapper.insertVisit(visit)!=1){
            //拜访记录插入异常
            throw new RrException(10006, "数据库插入拜访记录异常！");
        }
        return visit.getId();
    }
    //
    // /**
    //  * 修改拜访记录的内容
    //  * @param updateVisitAcc
    //  */
    // @Transactional
    // public void updateVisit(UpdateVisitAcc updateVisitAcc){
    //     java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
    //     //修改visits这个表的备注
    //     int i1 = visitMapper.updateVisit(updateVisitAcc.getId(), updateVisitAcc.getRemark(), now);
    //     if(i1!=1)
    //         throw new RrException(10006, "数据库修改拜访记录异常！");
    //     //有图片需要修改的情况
    //     if(null!=updateVisitAcc.getUrl()&&updateVisitAcc.getUrl().trim().length()>0){
    //         updateVisitAcc.setUrl(updateVisitAcc.getUrl().trim());
    //         int i = visitMapper.updateVisitImg(updateVisitAcc);
    //         if(i!=1){
    //             //updateVisitImg失败，即原先没有图片的情况 需要新增图片
    //             visitMapper.insertVisitImg(updateVisitAcc.getId(), updateVisitAcc.getUrl());
    //         }
    //     }
    // }

    /**
     * 封装查看包含的查找内容、时间、状态  可见拜访记录的string语句
     * user_id 和custom_id互斥 只能同时出现一个
     * @param input
     * @param status
     */
    public StringBuffer findCanSeeVisitListString(String input,Integer status,Long atime,Long btime,Long custom_id,Long user_id,Integer read_status){
        StringBuffer condition=new StringBuffer();
        //比较时间
        if(atime!=null&&btime!=null){
            Date first = new Date(atime);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String firsttime = formatter.format(first);
            Date last = new Date(btime);
            String lasttime = formatter.format(last);
            condition.append(" and visits.create_time BETWEEN '"+firsttime+"' and '"+lasttime+"'");
        }
        if(null!=user_id){
            //确定bd对象
            condition.append(" and visits.user_id="+user_id);
            if(null!=input){
                input=input.trim();
                if(input.length()>0){
                    condition.append(" and custom.company_name like '%"+input+"%'");
                }
            }
        }else if(null!=custom_id){
            //确定筛选的公司
            condition.append(" and visits.custom_id="+custom_id);
            if(null!=input){
                input=input.trim();
                if(input.length()>0){
                    if(UserUtils.getLoginUsertype()==1)
                        condition.append(" and (bd_account.name like '%"+input+"%' or bd_account.phone like '%"+input+"%')");
                }
            }
        }else {
            if(null!=input){
                input=input.trim();
                if(input.length()>0){
                    //拜访记录只能搜索公司的名称
                    condition.append(" and custom.company_name like '%"+input+"%'");
                    // if(UserUtils.getLoginUsertype()==1)
                    //     condition.append(" and (bd_account.name like '%"+input+"%' or bd_account.phone like '%"+input+"%' or custom.company_name like '%"+input+"%')");
                    // else condition.append(" and custom.company_name like '%"+input+"%'");
                }
            }
        }
        if(null!=status){
            //打卡状态的情况
            if(UserUtils.getLoginUsertype()==1)
            condition.append(" and visits.status="+status);
        }
        if(null!=read_status){
            //是否已读
            // if(UserUtils.getLoginUsertype()==1)
                condition.append(" and visits.read_status="+read_status);
        }
        return condition;
    }

    /**
     * 查看拜访记录列表
     * @param page
     * @param size
     * @param condition
     * @return
     */
    public ListAndCount findCanSeeVisitList(Integer page,Integer size,StringBuffer condition,int type){
        int visitsCount=0;
        if(type==1)//限制匹配汇报给自己的或者自己汇报的内容
        visitsCount = visitMapper.findVisitsCount(UserUtils.getLoginUserId(),condition.toString());
        else //bd或者公司搜索不需要是确定汇报对象是自己
            visitsCount = visitMapper.findVisitsCountBYADMIN(condition.toString());
        if(visitsCount==0){
            return new ListAndCount(null, 0);
        }
        condition.append(" ORDER BY visits.id desc");
        //分页显示
        if(null!=page&&null!=size){
            int begin=(page-1)*size;
            condition.append(" LIMIT "+begin+","+size);
        }

        ArrayList<LinkedHashMap> canSeeVisitList=null;
        if(type==1)//限制匹配汇报给自己的或者自己汇报的内容
            canSeeVisitList = visitMapper.findCanSeeVisitList(UserUtils.getLoginUserId(),condition);
        else //安装bd或者公司搜索不需要是确定汇报对象是自己
            canSeeVisitList=visitMapper.findCanSeeVisitListBYADMIN(condition);

        if(canSeeVisitList.size()>0) {
            for (int i=0;i<canSeeVisitList.size();i++){
                try {
                    long director_id = Long.parseLong(canSeeVisitList.get(i).get("director_id").toString());
                    //得到主管id后移除
                    canSeeVisitList.get(i).remove("director_id");
                    //得到主管的名字
                    LinkedHashMap accountbyId = accountService.findAccountbyId(director_id);
                    canSeeVisitList.get(i).put("director_name",accountbyId.get("name").toString());
                }catch (RrException e){
                    //查看主管名称时发现异常主管账号
                    canSeeVisitList.get(i).put("director_name","主管已删除");
                }
                //增加拜访时拜访的拜访地址
                try {
                    long visit_id = Long.parseLong(canSeeVisitList.get(i).get("visit_id").toString());
                    //得到拜访地址id后移除
                    canSeeVisitList.get(i).remove("visit_id");
                    //得到拜访地址的内容
                    LinkedHashMap customVisitbyId = customService.findCustomVisitbyId(visit_id);
                    if(ObjectUtils.isNotEmpty(customVisitbyId.get("detail_address")))
                        canSeeVisitList.get(i).put("address",customVisitbyId.get("address").toString()+customVisitbyId.get("detail_address").toString());
                    else
                        canSeeVisitList.get(i).put("address",customVisitbyId.get("address").toString());

                }catch (RrException e){
                    //查看拜访地址的内容时发现不存在
                    canSeeVisitList.get(i).put("address","拜访地址已删除");
                }
                //增加图片
                String visitImg = visitMapper.findVisitImg(Long.parseLong(canSeeVisitList.get(i).get("id").toString()));
                if(visitImg!=null){
                    String trim = visitImg.trim();
                    if(trim.length()>0)
                        canSeeVisitList.get(i).put("url", trim);
                }
                else
                    canSeeVisitList.get(i).put("url", null);
                //时间传时间戳
                canSeeVisitList.get(i).put("create_time",Timestamp.valueOf(canSeeVisitList.get(i).get("create_time").toString()).getTime());


            }

        }
        else
            canSeeVisitList=null;
        return new ListAndCount(canSeeVisitList, visitsCount);
    }

    // public int findVisitsCount(Long id,StringBuffer condition){
    //     return visitMapper.findVisitsCount(id,condition.toString());
    // }

    /**
     * 查看单个拜访记录
     * @param id
     * @return
     */
    public LinkedHashMap findVisitbyId(Long id){
        LinkedHashMap visitbyId = visitMapper.findVisitbyId(id);
        if (visitbyId==null)
            throw new RrException(20002, "记录不存在!");
        //时间改成long
        visitbyId.put("create_time",Timestamp.valueOf(visitbyId.get("create_time").toString()).getTime());
        visitbyId.put("update_time",Timestamp.valueOf(visitbyId.get("update_time").toString()).getTime());
        //加传输图片
        String visitImg = visitMapper.findVisitImg(id);
        if(visitImg!=null){
            String trim = visitImg.trim();
            if(trim.length()>0)
            visitbyId.put("url", trim);
        }
        else
            visitbyId.put("url", null);
        //在此加拜访地址获得的内容
        try {
            long visit_id = Long.parseLong(visitbyId.get("visit_id").toString());
            LinkedHashMap customVisitbyId = customService.findCustomVisitbyId(visit_id);
            visitbyId.put("address",customVisitbyId.get("address").toString());
            if(ObjectUtils.isNotEmpty(customVisitbyId.get("detail_address")))
            visitbyId.put("detail_address",customVisitbyId.get("detail_address").toString());
            else
                visitbyId.put("detail_address",null);
        }catch (RrException e){
            //拜访地址被删除则抓取异常
            visitbyId.put("address",null);
            visitbyId.put("detail_address",null);
        }
        return visitbyId;
    }

    /**
     * 已阅
     * @param id
     */
    public void readVisit(Long id){
        int i = visitMapper.readVisit(id,UserUtils.getLoginUserId());
        // if(i!=1)
        //     throw new RrException(20002, "修改阅读状态失败，是否汇报给你？是否已阅读？是否存在");
    }
}
