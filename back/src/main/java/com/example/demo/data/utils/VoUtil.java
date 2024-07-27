package com.example.demo.data.utils;

import cn.hutool.core.bean.BeanUtil;
import com.example.demo.data.entity.Permission;
import com.example.demo.data.vo.MenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "菜单转化为VO类")
public class VoUtil {
    @Operation(summary = "菜单转化为VO类")
    public static MenuVo permissionToMenuVo(Permission permission) {
        MenuVo vo = new MenuVo();
        BeanUtil.copyProperties(permission, vo);
        return vo;
    }
}
