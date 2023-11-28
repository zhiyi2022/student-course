package com.example.controller;

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/22 23:19
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + "/files";
    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload (MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();   //获取到原始的文件名
        long flag = System.currentTimeMillis();  //用时间戳来给上传的文件做唯一标识，防止后面上传相同的文件覆盖掉前面的
        String fileName = flag + "_" + originalFilename;  //1562365464_aaa.png

        File finalFile = new File(ROOT_PATH + "/" + fileName);   // 最终存在磁盘的文件对象
        if (!finalFile.getParentFile().exists()) {   // 如果腹肌路径不存在，就得创建
            finalFile.getParentFile().mkdirs();
        }
        file.transferTo(finalFile);
        //返回文件的URL
        String url = "http://"+ip+":"+port+"/files/download?fileName=" + fileName;
        return Result.success(url);
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download (String fileName, HttpServletResponse response) throws IOException {
        File file = new File(ROOT_PATH + "/" + fileName);  //文件在磁盘存储的对象
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        //os.write(FileUtil.readBytes(file));
        FileUtil.writeToStream(file, os);
        os.flush();
        os.close();
    }
}
