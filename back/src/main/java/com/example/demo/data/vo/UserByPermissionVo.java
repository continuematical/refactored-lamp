package com.example.demo.data.vo;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Tag(name = "用户菜单权限类")
@Data
public class UserByPermissionVo {
    private String userId;
    private String userName;
    private String roleStr;
    private String code;
    private String mobile;
}
