package com.example.demo.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(name = "菜单VO类")
@Data
public class MenuVo {
    @Schema(name = "菜单标题")
    private String title;

    @Schema(name = "菜单ID")
    private String id;

    @Schema(name = "菜单名称")
    private String name;

    @Schema(name = "父菜单ID")
    private String parentId;

    @Schema(name = "菜单层级")
    private Integer level;

    @Schema(name = "是否强制显示")
    private Boolean showAlways;

    @Schema(name = "菜单类型", description = "1具体操作 -1顶部菜单 0页面")
    private Integer type;

    @Schema(name = "组件")
    private String component;

    @Schema(name = "页面路径")
    private String path;

    @Schema(name = "PC端图标")
    private String icon;

    @Schema(name = "按钮类型")
    private String buttonType;

    @Schema(name = "网页链接")
    private String url;

    @Schema(name = "子权限列表")
    private List<String> permTypes;

    @Schema(name = "子菜单列表")
    private List<MenuVo> children;


}
