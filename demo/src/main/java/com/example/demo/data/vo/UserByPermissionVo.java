package com.example.demo.data.vo;

import io.swagger.annotations.Api;

@Api(tags = "用户菜单权限类")
public class UserByPermissionVo {
    private String userId;
    private String userName;
    private String roleStr;
    private String code;
    private String mobile;
}
