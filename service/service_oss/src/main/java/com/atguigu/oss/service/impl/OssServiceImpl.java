package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantProertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // 工具类获取值
        String endpoint = ConstantProertiesUtils.END_POIND;
        String accessKeyId = ConstantProertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantProertiesUtils.BUCKET_NAME;

        try {

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            //获取文件名 第二个参数
            String fileName = file.getOriginalFilename();

            //在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");//uuid中的-替换成空
            fileName=uuid+fileName;

            //把文件按照日期进行分类
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接 2021/12/23/e2e24df687er7er2r24h01.jpg
            fileName = datePath+"/"+fileName;

            // 调用oss方法实现上传
            // 第一个参数 Bucket名称
            // 第二个参数 上传到oss文件路径和文件名称
            // 第三个参数 上传文件的输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 把上传之后文件路径返回
            // 需要把上传到阿里云oss路径手动拼接出来
            // https://xc-guli.oss-cn-beijing.aliyuncs.com/01.gif

            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;

            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
