package com.study.minio.controller;

import com.alibaba.fastjson.JSON;
import com.study.base.common.ResultMsg;
import com.study.base.common.ResultStatusCode;
import com.study.base.util.FileType;
import com.study.base.util.FileTypeJudge;
import com.study.minio.utils.MinIoUtils;
import io.minio.ObjectStat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: minio 操作
 * @Author: luoshangcai
 * @Date 2020-07-03 16:52
 **/
@Slf4j
@RestController
@Api(tags = { "web服务：minio上传,下载,删除接口" })
@RequestMapping("/upload")
public class MinioController {


    /**
     * @Description //TODO  上传文件
     * consumes参数就是用来控制接收文件的Content-Type的
     **/
    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload", consumes="multipart/form-data", produces="application/json")
    public ResultMsg upload(@RequestParam("file") MultipartFile file,@RequestParam("bucketName") String bucketName) {
        if (file.isEmpty() || file.getSize() == 0) {
            return ResultMsg.createByErrorMessage(ResultStatusCode.FILE_NULL.getMsg());
        }
        if(StringUtils.isEmpty(bucketName)){
            return ResultMsg.createByErrorMessage(ResultStatusCode.BUCKETNAME_NULL.getMsg());
        }
        InputStream inputStream=null;
        try {
            inputStream = file.getInputStream();
            FileType type = FileTypeJudge.getType(inputStream);


            boolean isExists = MinIoUtils.bucketExists(bucketName);
            if(!isExists){
                //先创建文件桶
                log.info("创建桶文件夹:{}",bucketName);
                MinIoUtils.makeBucket(bucketName);
            }

            MinIoUtils.putObject(bucketName,file.getOriginalFilename(),inputStream,type.getValue());

            log.info("上传文件成功,OriginalFilename:{};type:{}",file.getOriginalFilename(),type.getValue());


            String url = MinIoUtils.getObjectUrl(bucketName, file.getOriginalFilename());
            log.info("上传文件minio的访问地址:{}",url);
            return ResultMsg.createBySuccess(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMsg.createByErrorMessage("上传文件失败");
        } finally {
            //关闭流
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @Description //TODO  下载文件
     * consumes参数就是用来控制接收文件的Content-Type的
     **/
    @ApiOperation(value = "下载文件")
    @RequestMapping(value = "/download")
    public void download(@RequestParam("fileName")String fileName, @RequestParam("bucketName") String bucketName, HttpServletResponse  response) throws IOException {
        if(StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(fileName)){
             return;
        }
        InputStream in = null;
        ServletOutputStream out = null;
        ObjectStat stat=null;
        try {
            //获取文件对象 stat原信息
            stat = MinIoUtils.statObject(bucketName, fileName);
            log.info(JSON.toJSONString(stat));
            in = MinIoUtils.getObject(bucketName, fileName,stat.length());

            response.setContentType(stat.contentType());
            response.reset();
            response.addHeader("Content-Disposition"," attachment;filename=" + new String(fileName.getBytes(),"iso-8859-1"));
//            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");

            out = response.getOutputStream();

            int copy = IOUtils.copy(in, out);
            if(copy>0){
                log.info("下载文件成功,bucketName:{};fileName:{}",bucketName,fileName);
            }else{
                log.info("下载文件失败,bucketName:{};fileName:{}",bucketName,fileName);
            }
        } catch (Exception e) {
            if(null !=stat){
                e.printStackTrace();
            }else{
                //如果minio中没有这个文件
                //继续将响应结果输出调用方
                log.info("minio中没有这个文件");
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                response.setStatus(404);
                response.getWriter().write(ResultStatusCode.MINIO_NOT_FILE.getMsg());
                return;
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/del/file")
    public ResultMsg del(@RequestParam("fileName")String fileName, @RequestParam("bucketName") String bucketName)  {
        if(StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(fileName)){
            return ResultMsg.createByErrorMessage(ResultStatusCode.NOT_PARAMETER.getMsg());
        }
        try {
            MinIoUtils.removeObject(bucketName,fileName);
            log.info("删除文件成功,bucketName:{};fileName:{}",bucketName,fileName);
            return ResultMsg.createBySuccess(ResultStatusCode.DEL_FILE_SUCC.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除文件失败bucketName:{};fileName:{};errorMsg:{}",bucketName,fileName,e);
            return ResultMsg.createByErrorMessage(ResultStatusCode.DEL_FILE_ERROR.getMsg());
        }
    }
}
