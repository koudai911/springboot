package com.study.minio;

import io.minio.MinioClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class MinioApplicationTests {

    @Autowired
    private MinioClient minioClient;

    @Test
    void contextLoads() {

        try {
            // Get input stream to have content of 'my-objectname' from 'my-bucketname'
            InputStream stream =
                    minioClient.getObject("test","罗尚财-绩效总结计划表.xlsx");

            // Read the input stream and print to the console till EOF.
            byte[] buf = new byte[16384];
            int bytesRead;
            while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
                System.out.println(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));
            }

            // Close the input stream.
            stream.close();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }

}
