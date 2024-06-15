package com.example.demo.data.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.Base64DecodeMultipartFile;
import com.example.demo.basics.utils.CommonUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.entity.File;
import com.example.demo.data.entity.Setting;
import com.example.demo.data.service.IFileService;
import com.example.demo.data.service.ISettingService;
import com.example.demo.data.utils.MyFileUtils;
import com.example.demo.data.vo.OssSettingVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@Tag(name = "文件上传接口")
@RequestMapping("/zhou/upload")
@Transactional
public class UploadController {

    @Autowired
    private MyFileUtils myFileUtils;

    @Autowired
    private ISettingService iSettingService;

    @Autowired
    private IFileService iFileService;

    @SystemLog(about = "文件上传", type = LogType.DATA_CENTER, dotype = "FILE-06")
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @Operation(summary = "文件上传")
    public Result<Object> upload(@RequestParam(required = false) MultipartFile file, @RequestParam(required = false) String base64) {
        if (StrUtil.isNotBlank(base64)) {
            file = Base64DecodeMultipartFile.base64Convert(base64);
        }
        String result = null;
        String fKey = CommonUtil.renamePic(file.getOriginalFilename());
        File f = new File();
        try {
            InputStream inputStream = file.getInputStream();
        } catch (Exception e) {
            return ResultUtil.error(e.toString());
        }
        OssSettingVo vo = getOssSettingVo();
        return ResultUtil.data(vo.getFileHttp() + vo.getFileView() + "/" + f.getId());
    }

    public OssSettingVo getOssSettingVo() {
        Setting s1 = iSettingService.getById("FILE_VIEW");
        Setting s2 = iSettingService.getById("FILE_HTTP");
        Setting s3 = iSettingService.getById("FILE_PATH");
        if (s1 == null || s1 == null || s1 == null) {
            return null;
        }
        return new OssSettingVo(s1.getValue(), s2.getValue(), s3.getValue());
    }
}
