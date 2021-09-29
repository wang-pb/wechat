package com.tkj.wechat.util;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName UploadFile
 * @Description TODO
 * @Author 幸运的大树
 * @Date 2021/9/3 11:49
 * @Version 1.0
 **/
@RestController
@RequestMapping("/upload")
public class UploadFile {

    @Value("${menu.img.uploadPath}")
    private String uploadPath;

    @Value("${menu.img.uploadUrl}")
    private String uploadUrl;

    @Value("${menu.img.zipPath}")
    private String zipPath;

    @Value("${menu.img.zipUrl}")
    private String zipUrl;

    @RequestMapping("/img")
    public Map<String, String> imgUpload(@RequestParam MultipartFile file){
        String oldName = file.getOriginalFilename();
        String imgType = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        String name = UUID.randomUUID().toString()+imgType; // 图片名
        //String realpath = uploadPath;
        String fileName = writeUploadFile(file, uploadPath, name);
        String url = uploadUrl + fileName;
        Map<String, String> result = new HashMap<>();
        result.put("url", name);
        return result;
    }

    public static String writeUploadFile(MultipartFile file, String realpath, String fileName) {
        File fileDir = new File(realpath);
        if (!fileDir.exists())
            fileDir.mkdirs();

        InputStream input = null;
        FileOutputStream fos = null;
        try {
            input = file.getInputStream();
            fos = new FileOutputStream(realpath + "/" + fileName);
            IOUtils.copy(input, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fos);
        }
        return fileName;
    }

}
