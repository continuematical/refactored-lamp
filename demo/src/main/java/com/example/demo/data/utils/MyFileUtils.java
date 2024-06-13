package com.example.demo.data.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.example.demo.basics.exceptions.MyException;
import com.example.demo.data.entity.Setting;
import com.example.demo.data.service.ISettingService;
import com.example.demo.data.vo.OssSettingVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Tag(name = "本地文件工具类")
@Component
public class MyFileUtils implements FileManage {

    @Autowired
    private ISettingService iSettingService;

    private static final String LOCAL_FILE_PATH_STEP = "/";


    //查看文件
    public static void view(String url, HttpServletResponse response){
        File viewFile = new File(url);
        if (!viewFile.exists()) {
            throw new MyException("没有文件");
        }
        try (FileInputStream is = new FileInputStream(viewFile); BufferedInputStream bis = new BufferedInputStream(is)) {
            OutputStream out = response.getOutputStream();
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buf)) > 0) {
                out.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new MyException("读取下载文件出错" + e);
        }
    }

    @Override
    public void deleteFile(String key) {
        FileUtil.del(new File(key));
    }

    @Override
    public String renameFile(String fromKey, String toKey) {
        File renameFile = new File(fromKey);
        FileUtil.rename(renameFile, toKey, false, true);
        return renameFile.getParentFile() + LOCAL_FILE_PATH_STEP + toKey;
    }

    //获取配置
    @Override
    public OssSettingVo getOssSetting() {
        Setting s1 = iSettingService.getById("FILE_VIEW");
        Setting s2 = iSettingService.getById("FILE_HTTP");
        Setting s3 = iSettingService.getById("FILE_PATH");
        if (s1 == null || s1 == null || s1 == null) {
            return null;
        }
        return new OssSettingVo(s1.getValue(), s2.getValue(), s3.getValue());
    }

    @Override
    public String copyFile(String fromKey, String toKey) {
        File copyFile = new File(fromKey);
        String newUrl = copyFile.getParentFile() + LOCAL_FILE_PATH_STEP + toKey;
        FileUtil.copy(copyFile, new File(newUrl), true);
        return newUrl;
    }

    //文件流上传
    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {
        OssSettingVo os = getOssSetting();
        String day = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        String path = os.getFilePath() + LOCAL_FILE_PATH_STEP + day;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(path + LOCAL_FILE_PATH_STEP + key);
        if (f.exists()) {
            throw new MyException("文件名称重复");
        }
        try {
            file.transferTo(f);
            return path + LOCAL_FILE_PATH_STEP + key;
        } catch (IOException e) {
            throw new MyException("上传文件出错 " + e);
        }
    }
}
