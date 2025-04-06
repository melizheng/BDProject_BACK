package com.zheng.business.service;

import com.zheng.business.accept.AddReportRuleAcc;
import com.zheng.business.accept.UpdateReportRecordAcc;
import com.zheng.business.bean.ListAndCount;
import com.zheng.business.bean.Report_Record;
import com.zheng.business.bean.Report_Rule;
import com.zheng.business.bean.RrException;
import com.zheng.business.dao.ReportMapper;
import com.zheng.business.utils.DateUtil;
import com.zheng.business.utils.NumberUtil;
import com.zheng.business.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * author:
 * Date:2022/2/914:34
 **/
@Service
//事务回滚
@Transactional
public class ReportService {

    @Autowired
    ReportMapper reportMapper;
    @Autowired
    AccountService accountService;

    /**
     * 增加汇报规则
     * @param addReportRuleAcc
     */
    public void insertReportRule(AddReportRuleAcc addReportRuleAcc,int type) throws ParseException {
        Report_Rule report_rule;
        if(type==1){
            //增加接口调用
            report_rule=new Report_Rule(addReportRuleAcc);
        }
        else //修改接口调用
            report_rule=new Report_Rule(addReportRuleAcc,addReportRuleAcc.getId());
        report_rule.setCreator_id(UserUtils.getLoginUserId());
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        report_rule.setCreate_time(now);
        report_rule.setUpdate_time(now);
        //转换为大写
        String s = report_rule.getReport_rate().toUpperCase();
        report_rule.setReport_rate(s);
        //增加规则
        int i = reportMapper.insertReportRule(report_rule);
        if(i!=1){
            throw new RrException(10006, "数据库插入汇报规则异常！");
        }
        // 得到当前时间的Date
        // Date date=new Date(now.getTime());
        String[] reporter_ids = addReportRuleAcc.getReporter_ids().split(",");
        for (int num=0;num<reporter_ids.length;num++){
            //判断BDids有没有输入异常
            LinkedHashMap accountbyId = accountService.findAccountbyId(Long.parseLong(reporter_ids[num]));
            //增加中间表的数据
            if(reportMapper.insertReportMiddle(UserUtils.getLoginUserId(), report_rule.getId(), Long.parseLong(reporter_ids[num]))!=1)
                throw new RrException(10006, "数据库插入汇报规则中间表异常！");
        }
        // long todayReportTime = DateUtil.simpleDateFormat.parse(DateUtil.getToday(now)).getTime() + addReportRuleAcc.getStart_time();
        // System.out.println(todayReportTime);
        // System.out.println(now.getTime());
        // if(now.getTime()<todayReportTime)
        //     autoAddReportRecord(report_rule.getId());
    }

    /**
     * 自动增加汇报记录对数据库表操作
     * #{create_time},#{update_time}为null 
     * @param report_rule
     * @param reporter_id
     */
    public Report_Record insertReportRecord(Report_Rule report_rule,Long reporter_id,Date date){
        //得到汇报记录中规则id、名称、主管id、rate周期
        Report_Record report_record=new Report_Record(report_rule);
        //得到汇报人名称 存id，名称
        LinkedHashMap bd = accountService.findAccountbyId(reporter_id);
        report_record.setReporter_name(bd.get("name").toString());
        report_record.setReporter_id(reporter_id);
        //得到主管名称并存下
        LinkedHashMap leader = accountService.findAccountbyId(report_rule.getCreator_id());
        report_record.setDirector_name(leader.get("name").toString());
        //设置汇报记录的状态未读
        report_record.setStatus(0);
        //计算周期时间
        String report_rate = report_rule.getReport_rate();
        String[] strings = calculateTime(report_rate,date);
        report_record.setStart_time(Timestamp.valueOf(strings[0]));
        report_record.setEnd_time(Timestamp.valueOf(strings[1]));
        //计算汇报的时间段
        //得到提交开始时间段
        StringBuffer START=new StringBuffer(DateUtil.getToday(date));
        StringBuffer END=new StringBuffer(DateUtil.getToday(date));
        int start_time_hour = (int) (report_rule.getStart_time() / 3600000);
        int start_time_min = (int)((report_rule.getStart_time() % 3600000)/60000);
        START.append(" "+start_time_hour+":"+start_time_min+":00");
        report_record.setSubmittime(Timestamp.valueOf(START.toString()));
        //得到提交结束时间的时间段
        int end_time_hour = (int) (report_rule.getEnd_time() / 3600000);
        int end_time_min = (int)((report_rule.getEnd_time() % 3600000)/60000);
        END.append(" "+end_time_hour+":"+end_time_min+":00");
        report_record.setDeadline(Timestamp.valueOf(END.toString()));
        //插入汇报记录表中
        int i = reportMapper.insertReportRecordBySystem(report_record);
        if(i!=1)
            throw new RrException(10006, "数据库插入汇报记录表异常！");
        return report_record;
    }

    /**
     * 计算汇报记录的周期时间
     * @param report_rate
     * @param date
     * @return
     */
    public String[] calculateTime(String report_rate,Date date){
        if(report_rate!=null){
            String s = report_rate.toUpperCase();
            //计算周期时间
            StringBuffer FirstDay=new StringBuffer();
            StringBuffer LastDay=new StringBuffer();
            switch (s){
                //按年计算周期  暂时不要年
                // case "Y":
                //     FirstDay.append(DateUtil.getYearFirstDay(date));
                //     LastDay.append(DateUtil.getYearLastDay(date));
                //     break;
                //按月计算周期
                case "M":
                    FirstDay.append(DateUtil.getMonthFirstDay(date));
                    LastDay.append(DateUtil.getMonthLastDay(date));
                    break;
                //按周计算周期
                case "W":
                    FirstDay.append(DateUtil.getWeekFirstDay(date));
                    LastDay.append(DateUtil.getWeeklastDay(date));
                    break;
                //按日计算周期
                case "D":
                    StringBuffer Today=new StringBuffer(DateUtil.getToday(date));
                    FirstDay.append(Today);
                    LastDay.append(Today);
                    break;
                default :
                    throw new RrException(10006, "report_rate 规则汇报周期异常！");
            }
            FirstDay.append(" 00:00:00");
            LastDay.append(" 23:59:59");
            String[] strings=new String[]{FirstDay.toString(),LastDay.toString()};
            return strings;
        }
        throw new RrException(10006, "report_record的周期计算异常！");
    }

    /**
     * 自动判断是否增加汇报记录并增加
     * @param id
     * @throws ParseException
     */
    public  ArrayList<Report_Record> autoAddReportRecord(Long id) throws ParseException {
        ArrayList<Report_Record> report_records=new ArrayList<>();
        String condition=null;
        if (null!=id){
            //创建了规则后立刻判断该规则是否需要立刻创建汇报记录
            condition=" where id="+id;
        }
        //得到当前的年月日的date形式,long形式
        Date date=new Date();
        Long today = DateUtil.simpleDateFormat.parse(DateUtil.getToday(date)).getTime();
        //用来与today比较的anotherday即要汇报的当天
        Long anotherday=null;
        //得到相应的汇报规则列表
        ArrayList<Report_Rule> ReportRecords = reportMapper.scheduledTasksGetReportRule(condition);
        if(null!=ReportRecords){
            StringBuffer Unchangedanotherday=new StringBuffer();
            for (int i=0;i<ReportRecords.size();i++){
                //得到单个汇报规则
                Report_Rule ReportRecord = ReportRecords.get(i);
                //得到对应的汇报周期
                String report_rate = ReportRecord.getReport_rate().toUpperCase();
                //得到对应的汇报日期,由逗号隔开
                String[] report_dates = ReportRecord.getReport_date().split(",");
                //"D"——按天汇报的规则无需判断report_dates
                if (report_rate.equals("D")){
                    ArrayList<Long> reportRuleReporterIds = reportMapper.getReportRuleReporterIdByRuleId(ReportRecord.getId());
                    if(null!=reportRuleReporterIds){
                        for (int ReporterIds=0;ReporterIds<reportRuleReporterIds.size();ReporterIds++){
                            Report_Record report_record = insertReportRecord(ReportRecord, reportRuleReporterIds.get(ReporterIds), date);
                            report_records.add(report_record);
                        }
                    }
                    continue;
                }
                if(report_dates.length==0){
                    throw new RrException(10006, "report_date 规则汇报日期异常！");
                }
                //每次都要清空Unchangedanotherday
                Unchangedanotherday.setLength(0);
                switch (report_rate){
                    //按年判断
                    // case "Y":
                    //     break;
                    //按月判断
                    case "M":
                        //得到当月的第一天
                        Unchangedanotherday.append(DateUtil.getMonthFirstDay(date));
                        break;
                    //按周判断
                    case "W":
                        //得到当周的第一天
                        Unchangedanotherday.append(DateUtil.getWeekFirstDay(date));
                        break;
                    default :
                        throw new RrException(10006, "report_rate 规则汇报周期异常！");
                }
                //report_dates 多个汇报日期 只要一个判断和当前时间相同就可以break
                for (int j=0;j<report_dates.length;j++){
                    //数据report_dates异常的情况排除异常
                    try {
                        Integer.parseInt(report_dates[j]);
                    }catch (NumberFormatException e){
                        throw new RrException(10006, "report_date 规则汇报日期异常！");
                    }
                    //1号不用减 因此Integer.parseInt(report_dates[j])-1  Unchangedanotherday增加需要增加的天数即为anotherday
                    anotherday = DateUtil.getAddDay(Unchangedanotherday, Integer.parseInt(report_dates[j])-1);
                    if(anotherday.equals(today)){
                        //增加汇报记录
                        ArrayList<Long> reportRuleReporterIds = reportMapper.getReportRuleReporterIdByRuleId(ReportRecord.getId());
                        if(null!=reportRuleReporterIds){
                            for (int ReporterIds=0;ReporterIds<reportRuleReporterIds.size();ReporterIds++){
                                Report_Record report_record = insertReportRecord(ReportRecord, reportRuleReporterIds.get(ReporterIds), date);
                                report_records.add(report_record);
                            }
                        }
                        break;
                    }
                }

            }
        }
        return report_records;
    }

    /**
     * 删除规则、以及定时任务提早创建出来的汇报记录
     * @param id
     */
    public void deleteReportRule(Long id){
        //判断是否有该规则
        getReportRuleByID(id);
        //删除规则、以及定时任务提早创建出来的汇报记录
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime = formatter.format(now);
        System.out.println(nowtime+" id:"+id);
        reportMapper.deleteReportRule(id,nowtime);
    }

    /**
     * 修改规则 先删后加
     * @param addReportRuleAcc
     */
    public void updateReportRule(AddReportRuleAcc addReportRuleAcc){
        deleteReportRule(addReportRuleAcc.getId());
        try {
            insertReportRule(addReportRuleAcc,2);
        } catch (ParseException e) {
            throw new RrException(10006, "parse异常,增加汇报规则失败");
        }
    }

    /**
     * 通过id查看单个规则
     * @param id
     * @return
     */
    public LinkedHashMap getReportRuleByID(Long id){
        LinkedHashMap reportRuleByID = reportMapper.getReportRuleByID(id);
        if(null==reportRuleByID)
            throw new RrException(10006, "不存在该规则！");
        return reportRuleByID;
    }

    /**
     * 通过规则id查看该规则对应的bds的id
     * @param report_rule_id
     * @return
     */
    public ArrayList<Long> getReportRuleReporterIdByRuleId(Long report_rule_id){
        return reportMapper.getReportRuleReporterIdByRuleId(report_rule_id);
    }

    /**
     * 管理员查看单个具体的规则内容与对应的bd用户们
     * @param id
     * @return
     */
    public LinkedHashMap getOneReportRule(Long id){
        //reportRuleByID 得到的是规则表的规则
        LinkedHashMap reportRuleByID = getReportRuleByID(id);
        //bds对应该规则的bd们的id
        ArrayList<Long> bds = getReportRuleReporterIdByRuleId(id);
        if(bds==null){
            reportRuleByID.put("reporter_ids",null);
            reportRuleByID.put("reporter_ids_count",0);
        }
        else{
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<bds.size();i++){
                stringBuffer.append(bds.get(i));
                if(i<bds.size()-1)
                    stringBuffer.append(",");
            }
            reportRuleByID.put("reporter_ids",stringBuffer);
            reportRuleByID.put("reporter_ids_count",bds.size());
        }

        // ArrayList<LinkedHashMap> BDs=new ArrayList<>();
        // for (int bdnum=0;bdnum< bds.size();bdnum++){
        //     LinkedHashMap accountbyId = accountService.findAccountbyId(bds.get(bdnum).longValue());
        //     BDs.add(accountbyId);
        // }
        // reportRuleByID.put("bds",BDs);
        return reportRuleByID;
    }


    /**
     * 得到自己创建的规则列表
     * @param page
     * @param size
     * @return
     */
    public ListAndCount getReportRuleList(Integer page,Integer size){
        StringBuffer condition=new StringBuffer();
        Integer canSeeCount = reportMapper.getRuleCount(UserUtils.getLoginUserId());
        if(canSeeCount==0){
            return new ListAndCount(null, 0);
        }
        //分页显示
        if(null!=page&&null!=size){
            int begin=(page-1)*size;
            condition.append(" LIMIT "+begin+","+size);
        }
        ArrayList<LinkedHashMap> reportRuleList = reportMapper.getReportRuleList(UserUtils.getLoginUserId(), condition.toString());
        if(reportRuleList.size()>0){
            for (int i=0;i<reportRuleList.size();i++){
                switch (reportRuleList.get(i).get("report_rate").toString()){
                    case "W":
                        reportRuleList.get(i).put("report_rate","每周");
                        String[] report_dates = reportRuleList.get(i).get("report_date").toString().split(",");
                        if(report_dates.length>1){
                            StringBuffer report_date=new StringBuffer();
                            for(int n=0;n<report_dates.length;n++){
                                report_date.append("周");
                                int week = Integer.parseInt(report_dates[n]);
                                if(week==7)
                                    report_date.append("日");
                                else
                                    report_date.append(NumberUtil.int2chineseNum(week));
                                if(n<report_dates.length-1)
                                    report_date.append("、");
                            }
                            reportRuleList.get(i).put("report_date",report_date.toString());
                        }else{
                            String report_date = NumberUtil.int2chineseNum(Integer.parseInt(reportRuleList.get(i).get("report_date").toString()));
                            if(Integer.parseInt(reportRuleList.get(i).get("report_date").toString())==7)
                                report_date="日";
                            reportRuleList.get(i).put("report_date","周"+report_date);
                        }
                        break;
                    case "D":
                        reportRuleList.get(i).put("report_rate","每天");
                        break;
                }
                Integer bdsCount = reportMapper.getRuleBDNumber(Long.parseLong(reportRuleList.get(i).get("id").toString()));
                reportRuleList.get(i).put("bdsCount",bdsCount);
            }
            return new ListAndCount(reportRuleList, canSeeCount);
        }
        return new ListAndCount(null, 0);
    }


    /**
     * 判断是否存在该汇报记录
     * @param id
     * @return
     */
    public Report_Record existReportRecordById(Long id){
        Report_Record report_record = reportMapper.existReportRecordById(id);
        if(null==report_record)
            throw new RrException(10006, "汇报记录不存在");
        return report_record;

    }

    /**
     * BD提交汇报记录
     * @param updateReportRecordAcc
     */
    public void updateReportRecord(UpdateReportRecordAcc updateReportRecordAcc){
        //先得到该汇报记录的存在 取得汇报人id，回报周期的时间
        Report_Record have_record = existReportRecordById(updateReportRecordAcc.getId());
        Report_Record report_record=new Report_Record(updateReportRecordAcc);
        //设置状态为已提交
        report_record.setStatus(1);
        java.sql.Timestamp now=new Timestamp(System.currentTimeMillis());
        report_record.setCreate_time(now);
        report_record.setUpdate_time(now);
        //取得汇报人id，在回报周期的时间 查拜访记录的次数
        int visitsCount=reportMapper.getVisitsCountByBd(have_record);
        report_record.setVisit_total(visitsCount);
        int i = reportMapper.updateReportRecord(report_record);
        if(i!=1){
            throw new RrException(10006, "提交汇报记录异常");
        }
    }

    /**
     *查看汇报记录列表的string
     * @param input
     * @param atime
     * @param btime
     * @param status 1:提交未读 2:已读 null：全部  0:未提交
     * @return
     */
    public StringBuffer getReportRecordListString(String input,Long atime,Long btime,Integer status){
        StringBuffer condition=new StringBuffer();
        if(null!=input){
            input=input.trim();
            if(input.length()>0){
                if(UserUtils.getLoginUsertype()==1){
                    condition.append(" and (report_record.reporter_name like '%"+input+"%' or report_record.rule_name like '%"+input+"%')");
                }
                else condition.append(" and  report_record.rule_name like '%"+input+"%'");
            }
        }
        //比较时间
        if(atime!=null&&btime!=null){
            Date first = new Date(atime);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String firsttime = formatter.format(first);
            Date last = new Date(btime);
            String lasttime = formatter.format(last);
            condition.append(" and report_record.submittime BETWEEN '"+firsttime+"' and '"+lasttime+"'");
        }
        if(null!=status){
            condition.append(" and report_record.status="+status);
        }
        return condition;
    }

    /**
     * 查看汇报记录列表
     * @param page
     * @param size
     * @param condition
     * @return
     */
    public ListAndCount findCanSeeReportRecordList(Integer page,Integer size,StringBuffer condition){
        Integer canSeeReportRecordCount = findCanSeeReportRecordCount(condition);
        if(canSeeReportRecordCount==0){
            return new ListAndCount(null, 0);
        }
        condition.append(" ORDER BY report_record.id desc");
        //分页显示
        if(null!=page&&null!=size){
            int begin=(page-1)*size;
            condition.append(" LIMIT "+begin+","+size);
        }
        ArrayList<LinkedHashMap> reportRecordList = reportMapper.getReportRecordList(UserUtils.getLoginUserId(),condition);
        if(reportRecordList.size()==0) {
            reportRecordList=null;
        }
        else {
            for (int num=0;num<reportRecordList.size();num++){
                reportRecordList.get(num).put("submittime",Timestamp.valueOf(reportRecordList.get(num).get("submittime").toString()).getTime());
                reportRecordList.get(num).put("deadline",Timestamp.valueOf(reportRecordList.get(num).get("deadline").toString()).getTime());
            }
        }
        return new ListAndCount(reportRecordList, canSeeReportRecordCount);
    }

    /**
     * 查看汇报记录个数
     * @param condition
     * @return
     */
    public Integer findCanSeeReportRecordCount(StringBuffer condition){
        return reportMapper.getReportRecordListCount(UserUtils.getLoginUserId(), condition);
    }

    /**
     * 查看单个汇报记录
     * @param id
     * @return
     */
    public LinkedHashMap getReportRecordById(Long id){
        LinkedHashMap reportRecordById = reportMapper.getReportRecordById(id);
        if(null!=reportRecordById) {
            reportRecordById.put("start_time",Timestamp.valueOf(reportRecordById.get("start_time").toString()).getTime());
            reportRecordById.put("end_time",Timestamp.valueOf(reportRecordById.get("end_time").toString()).getTime());
            reportRecordById.put("deadline",Timestamp.valueOf(reportRecordById.get("deadline").toString()).getTime());
            if(0!=Integer.parseInt(reportRecordById.get("status").toString()))
                reportRecordById.put("update_time",Timestamp.valueOf(reportRecordById.get("update_time").toString()).getTime());

            return reportRecordById;
        }
        return null;
    }

    /**
     * 已阅
     * @param id
     */
    public void readReport(Long id){
        int i = reportMapper.readReport(id,UserUtils.getLoginUserId());
        // if(i!=1)
        //     throw new RrException(20002, "修改阅读状态失败，是否汇报给你？是否已阅读？是否存在");
    }
}
