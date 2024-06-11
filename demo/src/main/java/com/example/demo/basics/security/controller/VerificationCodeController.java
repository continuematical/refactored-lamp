package com.example.demo.basics.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.basics.security.SecurityUserDetails;
import com.example.demo.basics.security.utils.NoticeUtils;
import com.example.demo.basics.security.utils.WeChatUtils;
import com.example.demo.basics.utils.CommonUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.basics.utils.SecurityUtil;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    @RequestMapping(value = "/sendVerificationCode",method = RequestMethod.GET)
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
        NoticeUtils.sendTuWenMessage("zhoujin", "OA登录验证", "验证码 "
                        + verification + "，1分钟后失效", "https://gitee.com/yyzwz", "https://bkimg.cdn.bcebos.com/pic/37d12f2eb9389b503a80d4b38b35e5dde6116ed7",
                WeChatUtils.getToken());
        redisTemplateHelper.set("qwsms:" + number, verification, 60, TimeUnit.SECONDS);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/verificationCodeLogin",method = RequestMethod.GET)
    @Operation(summary = "企业微信验证码登录")
    public Result<Object> verificationCodeLogin(@RequestParam String number, @RequestParam String code) {
        String codeAns = redisTemplateHelper.get("qwsms:" + number);
        if(codeAns == null) {
            return ResultUtil.error("验证码已过期");
        }
        if(codeAns.equals(code)) {
            QueryWrapper<User> userQw = new QueryWrapper<>();
            userQw.eq("username",number);
            List<User> users = iUserService.list(userQw);
            if(users.size() == 0) {
                return ResultUtil.error(number + "账户不存在");
            }
            String accessToken = securityUtil.getToken(number, false);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new SecurityUserDetails(users.get(0)), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResultUtil.data(accessToken);
        }
        return ResultUtil.error("验证码错误");
    }
}
