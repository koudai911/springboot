package com.study.minio.utils;

import com.study.minio.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: minio 工具类
 * @Author: luoshangcai
 * @Date 2020-07-03 16:44
 **/
@Component
@Configuration
@EnableConfigurationProperties({MinioProperties.class})
public class MinIoUtils {


    private static MinioClient instance;

    @Autowired
    private MinioClient minioClient;

    @PostConstruct
    private void beforeInit() {
        instance = this.minioClient;
    }



    /**
     * 判断 bucket是否存在
     * @param bucketName
     * @return
     */
    public static boolean bucketExists(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        return instance.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     * @param bucketName
     */
    public static void makeBucket(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException {

        boolean isExist = instance.bucketExists(bucketName);
        if(!isExist) {
            instance.makeBucket(bucketName);
        }

    }

    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param filename
     * @param mediaType 文件类型
     */
    public static void putObject(String bucketName, String objectName, String filename,String mediaType) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        instance.putObject(bucketName,objectName,filename,mediaType);
    }
    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param stream
     * * @param mediaType 文件类型
     */
    public static void putObject(String bucketName, String objectName, InputStream stream,String mediaType) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        instance.putObject(bucketName,objectName,stream,mediaType);

    }

    /**
     * 下载文件
     * @param bucketName 桶 目录
     * @param objectName  文件名
     * @param length
     */
    public static InputStream getObject(String bucketName, String objectName,long length) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
       return  instance.getObject(bucketName,objectName,0,length);
    }

    /**
     *  返回下载文件的url
     * @param bucketName 桶 目录
     * @param objectName  文件名
     */
    public static String getObjectUrl(String bucketName, String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        return instance.getObjectUrl(bucketName,objectName);

    }
    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    public static void removeObject(String bucketName, String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        instance.removeObject(bucketName,objectName);

    }

    /**
     * 查询文件类型
     * @param bucketName
     * @param objectName
     */
    public static ObjectStat statObject(String bucketName, String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {

        return instance.statObject(bucketName, objectName);

    }

}