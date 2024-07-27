package com.example.demo.data.utils;

import com.example.demo.data.vo.OssSettingVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Tag(name = "文件配置接口类")
public interface FileManage {
    @Operation(summary = "删除文件")
    void deleteFile(String key);

    @Operation(summary = "重命名文件")
    String renameFile(String fromKey, String toKey);

    @Operation(summary = "获取配置")
    OssSettingVo getOssSetting();

    @Operation(summary = "拷贝文件")
    String copyFile(String fromKey, String toKey);

    @Operation(summary = "文件流上传")
    String inputStreamUpload(InputStream inputStream, String key, MultipartFile file);
}
