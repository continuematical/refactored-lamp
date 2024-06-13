package com.example.demo.basics.security.controller;

import com.example.demo.basics.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhou/qwVerificationCode")
public class QwVerificationCodeController {
    @Autowired
    private SecurityUtil securityUtil;
}
