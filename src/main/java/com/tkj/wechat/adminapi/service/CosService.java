package com.tkj.wechat.adminapi.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tkj.wechat.util.LocalFileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class CosService {

    @Value("${cos.secretId}")
    String secretId;

    @Value("${cos.secretKey}")
    String secretKey;

    @Value("${cos.region}")
    String regionValue;

    @Value("${cos.publicBucketName}")
    String publicBucketName;

    @Value("${cos.identityBucketName}")
    String identityBucketName;

    public String uploadFileToBucket(String localFileName,String buckeName) throws IOException, NoSuchAlgorithmException {


        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regionValue);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);

        String hashFileName = LocalFileUtil.getFileHash(localFileName);
        String type = LocalFileUtil.getFileType(localFileName);
        String remoteFileName = hashFileName + "." + type;

        PutObjectResult putObjectResult = cosClient.putObject(buckeName, remoteFileName, new File(localFileName));

        return remoteFileName;
    }

    //上传文件至COS的公共资源桶中并返回远程文件名称
    public String uploadFileToPublicBucket(String localFileName) throws IOException, NoSuchAlgorithmException {
        return uploadFileToBucket(localFileName,publicBucketName);
    }

    //上传文件至COS的身份证资源桶中并返回远程文件名称
    public String uploadFileToIdentityBucket(String localFileName) throws IOException, NoSuchAlgorithmException {
        return uploadFileToBucket(localFileName,identityBucketName);
    }

    //获取公共资源url
    public String getPublicUrl(String remoteFileName){
        return getFileUrl(remoteFileName,publicBucketName);
    }

    //获取身份证资源url
    public String getIdentityUrl(String remoteFileName){
        return getFileUrl(remoteFileName,identityBucketName);
    }

    public String getFileUrl(String remoteFileName,String bucket){
        String baseUrl = "https://" + bucket + ".cos." + regionValue + ".myqcloud.com";
        String url = baseUrl + "/"+remoteFileName;
        return url;
    }



}
