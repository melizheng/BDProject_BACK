package com.zheng.business.controller;


import com.zheng.business.bean.AnswerRet;
import com.zheng.business.bean.RrException;
import com.zheng.business.dao.ReportMapper;
import com.zheng.business.dao.VisitMapper;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/uploadImage")
public class UploadImageController {
    @Autowired
    VisitMapper visitMapper;
    @Autowired
    ReportMapper reportMapper;
    private String staticPath="/home"; //定义上传文件的存放位置

    /**
     * post
     * 上传文件到本地目录
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/visits", method = RequestMethod.POST)
    public AnswerRet<String> uploadImageByVisits(@RequestParam("files") List<MultipartFile> files, Long id) {
        // String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        if(files==null)
            throw new RrException(555,"file 空");
        else{
            File targetFile = new File(staticPath);
            if (!targetFile.exists()){
                targetFile.mkdirs();
            }

            StringBuffer stringBuffer=new StringBuffer();
            //获取项目classes/static的地址
            for(int i=0;i<files.size();i++){
                Random r = new Random();
                int randomI = r.nextInt(1000);
                Date date=new Date();
                String fileName ="/"+date.getTime()+randomI+".jpg";
                // 图片存储目录及图片名称
                String url_path = "/images"  + fileName;
                //图片保存路径
                String savePath = staticPath + url_path;
                System.out.println("图片保存地址："+savePath);
                // 访问路径=静态资源路径+文件目录路径
                String visitPath ="http://120.24.228.190:8080" + url_path;
                System.out.println("图片访问uri："+visitPath);

                File saveFile = new File(savePath);
                if (!saveFile.exists()){
                    saveFile.mkdirs();
                }
                try {
                    files.get(i).transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
                    //压缩
                    Thumbnails.of(savePath)
                            .scale(1f)
                            .outputQuality(0.5f)
                            .toFile(savePath);
                    stringBuffer.append(visitPath+",");
                } catch (IOException e) {
                    visitMapper.deleteVisitById(id);
                    throw new RrException(55555, e.getMessage());
                }
            }
            if(stringBuffer.length()<=1){
                visitMapper.deleteVisitById(id);
                throw new RrException(55555,"图片上传异常");

            }
            String url = stringBuffer.substring(0, stringBuffer.length() - 1);
            Integer img_id = visitMapper.insertVisitImgBeforeFind(id);
            if(img_id==null){
                int i = visitMapper.insertVisitImg(id, url);
                if(i==0)
                    throw new RrException(55555,"图片上传异常");
            }else{
                int i = visitMapper.updateVisitImg(id, url);
                if(i==0)
                    throw new RrException(55555,"图片上传异常");
            }

        }
        return new AnswerRet<>(1,"成功");
    }

    /**
     * post
     * 上传文件到项目的静态文件目录
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/report", method = RequestMethod.POST)
    public AnswerRet<String> uploadImageByReport(@RequestParam("files") List<MultipartFile> files,@RequestParam("id") Long id) {
        if(files==null)
            throw new RrException(555,"file 空");
        else{
            File targetFile = new File(staticPath);
            if (!targetFile.exists()){
                targetFile.mkdirs();
            }
            StringBuffer stringBuffer=new StringBuffer();
            //获取项目classes/static的地址
            for(int i=0;i<files.size();i++){
                Random r = new Random();
                int randomI = r.nextInt(1000);
                Date date=new Date();
                String fileName ="/"+date.getTime()+randomI+".jpg";
                // 图片存储目录及图片名称
                String url_path = "/images"  + fileName;
                //图片保存路径
                String savePath = staticPath + url_path;
                System.out.println("图片保存地址："+savePath);
                // 访问路径=静态资源路径+文件目录路径
                String visitPath ="http://120.24.228.190:8080" + url_path;
                System.out.println("图片访问uri："+visitPath);

                File saveFile = new File(savePath);
                if (!saveFile.exists()){
                    saveFile.mkdirs();
                }
                try {
                    files.get(i).transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
                    //压缩
                    Thumbnails.of(savePath)
                            .scale(1f)
                            .outputQuality(0.5f)
                            .toFile(savePath);
                    stringBuffer.append(visitPath+",");
                } catch (IOException e) {
                    reportMapper.cleanReportRecordById(id);
                    throw new RrException(55555, e.getMessage());
                }
            }
            if(stringBuffer.length()<=1){
                reportMapper.cleanReportRecordById(id);
                throw new RrException(55555,"图片上传异常");

            }
            String url = stringBuffer.substring(0, stringBuffer.length() - 1);
            int i = reportMapper.updateReportImages(id, url);
            if(i==0)
                throw new RrException(55555,"图片上传异常");

        }
        return new AnswerRet<>(1,"成功");
    }

}