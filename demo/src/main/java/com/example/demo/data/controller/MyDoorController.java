package com.example.demo.data.controller;

import com.example.demo.basics.utils.SecurityUtil;
import com.example.demo.data.service.IPermissionService;
import com.example.demo.data.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhou/myDoor")
@Tag(name = "我的门户管理接口")
@Transactional
public class MyDoorController {
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private IUserService iUserService;


}
