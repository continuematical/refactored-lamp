package com.example.demo.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Tag(name = "文件存储配置VO类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OssSettingVo {

    @Schema(name = "访问")
    private String fileView;

    @Schema(name = "http")
    private String fileHttp;

    @Schema(name = "路径")
    private String filePath;
}
