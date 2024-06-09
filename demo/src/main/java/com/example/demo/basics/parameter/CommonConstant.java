package com.example.demo.basics.parameter;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CommonConstant {
    @Schema(name = "正常")
    Integer USER_STATUS_NORMAL = 0;

    @Schema(name = "禁用")
    Integer USER_STATUS_LOCK = -1;

    @Schema(name = "顶级菜单")
    Integer PERMISSION_NAV = -1;

    @Schema(name = "普通菜单")
    Integer PERMISSION_PAGE = 0;

    @Schema(name = "按钮菜单")
    Integer PERMISSION_OPERATION = 1;

    @Schema(name = "顶级菜单")
    Integer LEVEL_ZERO = 0;

    @Schema(name = "1级菜单")
    Integer LEVEL_ONE = 1;

    @Schema(name = "2级菜单")
    Integer LEVEL_TWO = 2;

    @Schema(name = "3级菜单")
    Integer LEVEL_THREE = 3;

    @Schema(name = "总部门ID")
    String PARENT_ID = "0";

    @Schema(name = "头像URL")
    String USER_DEFAULT_AVATAR = "https://asoa-1305425069.cos.ap-shanghai.myqcloud.com/1669635627773202432.png";
}
