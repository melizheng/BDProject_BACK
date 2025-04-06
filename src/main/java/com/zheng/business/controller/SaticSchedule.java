package com.zheng.business.controller;

import com.zheng.business.bean.Report_Record;
import com.zheng.business.service.ReportService;
import com.zheng.business.socket.ServerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;

/**
 * author:
 * Date:2022/3/2111:47
 **/
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@EnableAsync        // 2.开启多线程
public class SaticSchedule {

    @Autowired
    ReportService reportService;
    //3.添加定时任务 每天凌晨1点查看任务
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public  void configureTasks() {
        try {
            ArrayList<Report_Record> report_records = reportService.autoAddReportRecord(null);
            if(report_records.size()>0){
               for (int i=0;i<report_records.size();i++)
                   sendMessage(report_records.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Report_Record report_record){
        new Thread(new Runnable(){
            @Override
            public void run() {
                long l = report_record.getSubmittime().getTime()-new Date().getTime();
                try {
                    Thread.sleep(l-60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ServerThread.sendToClients(report_record.getReporter_id() + "", report_record.getId() + ",你有一条汇报待提交," + report_record.getDirector_name() + "," + report_record.getDeadline().getTime() + ",3");
            }
        }).start();
    }
}