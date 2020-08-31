package com.study.minio.utils;

import java.util.UUID;

/**
 * @Description: 判断文件类型工具类
 * @Author: luoshangcai
 * @Date 2020-07-02 18:12
 **/
public class MinioTypeUtil {


    /**
     * 获取文件名
     *
     * @param suffix 后缀
     * @return 返回上传文件名
     */
    public static String getFileName(String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid +  suffix;
    }
}

