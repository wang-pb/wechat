package com.tkj.wechat.util;

import com.google.common.io.Files;
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
public class LocalFileUtil {



    public static List<String> readlinesFromFile(String fileName) throws IOException {
        List<String> ret = new ArrayList<String>();
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
        String line = null;

        while ((line = br.readLine()) != null) {
            ret.add(line.replace("\n", ""));
        }

        return ret;


    }
    public static String getFileHash(String fileName) throws IOException, NoSuchAlgorithmException {
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

    public static String getFileType(String fileName) throws IOException {
        if(null == fileName){
            return null;
        }
        File file = new File(fileName);
        if(!file.exists()){
            System.out.println("FILE NOT EXIST!");
            return null;
        }
        return Files.getFileExtension(file.getCanonicalPath());
    }

}

