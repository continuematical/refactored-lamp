package com.example.demo.data.controller;

import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.utils.CreateVerifyCode;
import com.example.demo.basics.utils.IPInfoUtil;
import com.example.demo.basics.utils.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/zhou/security")
@Tag(name = "公共接口")
@Transactional
public class SecurityController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IPInfoUtil ipInfoUtil;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @Operation(summary = "初始化验证码")
    public Result<Object> init() {
        String codeId = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(codeId, new CreateVerifyCode().randomStr(4), 2L, TimeUnit.MINUTES);
        return ResultUtil.data(codeId);
    }

    @RequestMapping(value = "/draw/{captchaId}", method = RequestMethod.GET)
    @Operation(summary = "根据验证码获取图片")
    public void draw(@PathVariable("captchaId") String captchaId, HttpServletResponse response) throws IOException {
        String codeStr = redisTemplate.opsForValue().get(captchaId);
        CreateVerifyCode createVerifyCode = new CreateVerifyCode(116,36,4,10, codeStr);
        response.setContentType("image/png");
        createVerifyCode.write(response.getOutputStream());
    }

    @RequestMapping(value = "/needLogin", method = RequestMethod.GET)
    @Operation(summary = "未登录返回的数据")
    public Result<Object> needLogin() {
        return ResultUtil.error(401, "登录失效");
    }
}
