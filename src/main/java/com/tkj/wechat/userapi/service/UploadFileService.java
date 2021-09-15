package com.tkj.wechat.userapi.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UploadFileService {

    @Value("${cos.secretId}")
    String secretId;

    @Value("${cos.secretKey}")
    String secretKey;

    @Value("${cos.region}")
    String region;

    @Value("${cos.publicBucketName}")
    String buckeyName;


    public String getUrlType(String url) {
        String[] arr = url.split("\\.");
        System.out.println(url);
        return arr[arr.length - 1];
    }

    public List<String> readlinesFromFile(String fileName) throws IOException {
        List<String> ret = new ArrayList<String>();
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
        String line = null;

        while ((line = br.readLine()) != null) {
            ret.add(line.replace("\n", ""));
        }

        return ret;


    }

    public void uploadFile(String localFile, String remoteFile) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region reg = new Region(region);
        ClientConfig clientConfig = new ClientConfig(reg);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);

        PutObjectResult putObjectResult = cosClient.putObject(buckeyName, remoteFile, new File(localFile));
        String etag = putObjectResult.getETag();
        System.out.println(etag);


    }

    public String downLoadFileFromUrl(String fileUrl) {
        String ret = null;
        String tmpDir = "tmp_dir";

        //        RandomStringUtils random = new RandomStringUtils();

        String fileType = "." + getUrlType(fileUrl);
        String randName = RandomStringUtils.random(16) + fileType;

        try {
            URL url = new URL(fileUrl);
            File dirFile = new File(tmpDir);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            FileUtils.copyURLToFile(url, new File(tmpDir + "/" + randName));
            ret = getFileHash(tmpDir + "//" + randName) + fileType;

        } catch (MalformedURLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadFile(tmpDir + "//" + randName, ret);

        return ret;
    }

    private String getFileHash(String fileName) throws IOException, NoSuchAlgorithmException {
        FileInputStream fin = new FileInputStream(fileName);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        int BUFF_SIZE = 4096;
        byte[] buff = new byte[BUFF_SIZE];
        int len = -1;
        while ((len = fin.read(buff, 0, BUFF_SIZE)) != -1) {
            md5.update(buff, 0, len);
        }
        fin.close();

        byte[] md5Byte = md5.digest();
        BigInteger ret = new BigInteger(md5Byte);
        return ret.toString(16);
    }
}

