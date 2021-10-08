package com.tkj.wechat.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class DownLoadFile {
    /**
     * 用输入流读取图片，并用输出流返回给前端
     * @param url
     * @param response
     */
    @GetMapping("download_img")
    public void downloadImg(String url, HttpServletResponse response) {
        try (OutputStream out = response.getOutputStream();
             FileInputStream in = new FileInputStream(url)) {
            byte[] buf = new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        } catch (IOException e) {
            System.out.println("图片找不到");
            e.printStackTrace();
        }
    }
}
