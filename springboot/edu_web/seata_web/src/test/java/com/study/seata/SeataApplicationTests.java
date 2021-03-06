package com.study.seata;

import com.aliyun.com.viapi.FileUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * 阿里身份证识别
 **/
@SpringBootTest
public class SeataApplicationTests {

    @SneakyThrows
    @Test
    public void contextLoads() {
        String accessKey = "LTAI4G4q9aLoEtWZYGhZPT1p";
        String accessKeySecret = "gjXK82PtPgOLeUljTADOXcFYhsMIx6";
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        RecognizeIdentityCardRequest request = new RecognizeIdentityCardRequest();
        request.setRegionId("cn-shanghai");
        request.setSide("face");
//        图片格式：JPEG、JPG、PNG、BMP、GIF。
//        图像大小：图像大小不超过3M。
//        图像分辨率：不限制图片分辨率，但图片分辨率太高可能会导致API识别超时，超时时间为5秒。
//        URL地址中不能包含中文字符。

        //图片URL地址。当前仅支持上海地域的OSS链接，如何生成URL请参见
        // https://help.aliyun.com/document_detail/155645.html?spm=a2c4g.11186623.2.23.5a005edb1IM1SN
        //对于非OSS用户，或者您的OSS所属地域不是华东2（上海），推荐您使用SDK生成URL。
        String photoUrl = "C:\\Users\\CW6487\\Desktop\\timg.jpg";
        String url = testUploadFile(accessKey, accessKeySecret, photoUrl);
        request.setImageURL(url);

        try {
            RecognizeIdentityCardResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    //将非 oos 图片 装换为url 给身份证 图像识别
    public static String testUploadFile(String accessKey, String accessKeySecret, String file) throws ClientException, IOException {
        FileUtils fileUtils = FileUtils.getInstance(accessKey, accessKeySecret);
        String ossurl = fileUtils.upload(file);
        System.out.println(ossurl);
        return ossurl;
    }

}
