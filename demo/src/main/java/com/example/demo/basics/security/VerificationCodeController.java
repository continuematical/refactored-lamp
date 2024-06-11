package com.example.demo.basics.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.basics.utils.CommonUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.basics.utils.SecurityUtil;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController()
@RequestMapping("/verificationCode")
@Tag(name = "企业微信验证码登录接口")
@Transactional
public class VerificationCodeController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    @Autowired
    private SecurityUtil securityUtil;

    @Operation(summary = "发送企业微信验证码")
    public Result<Object> sendVerificationCode(@RequestParam String number) {
        if (!Objects.equals("zhoujin", number)) {
            return ResultUtil.error("请配置你的工号");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        queryWrapper.eq("username", number);
        Long userCount = iUserService.count(queryWrapper);
        if (userCount < 1L) {
            return new ResultUtil<Object>().setErrorMsg("无权限登入");
        }
        String verification = CommonUtil.getRandomTwoNum();
        //发送消息
        return ResultUtil.success();
    }
}
