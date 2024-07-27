package com.example.demo.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Tag(name = "菜单临时VO类")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {

    @Schema(name = "页面路径")
    private String path;

    @Schema(name = "菜单标题")
    private String title;
}
