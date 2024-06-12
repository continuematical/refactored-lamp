package com.example.demo.basics.security.jwt;

import com.example.demo.basics.exceptions.AuthException;
import com.example.demo.basics.parameter.LoginProperties;
import com.example.demo.basics.utils.ResponseUtil;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Tag(name = "登录失败回调")
@Slf4j
@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private LoginProperties loginProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOGIN_FAIL_TIMES_PRE = "LOGIN_FAIL_TIMES_PRE:";

    private static final String REQUEST_PARAMETER_USERNAME = "username:";

    private static final boolean RESPONSE_FAIL_FLAG = false;

    private static final int RESPONSE_FAIL_CODE = 500;

    @Operation(summary = "查询登陆失败的次数")
    public boolean recordLoginTimes(String username) {
        String loginFailTimeStr = stringRedisTemplate.opsForValue().get(LOGIN_FAIL_TIMES_PRE + username);
        int loginFailTimes = 0;
        //已错误次数
        if (!NullUtils.isNull(loginFailTimeStr)) {
            loginFailTimes = Integer.parseInt(loginFailTimeStr) + 1;
        }
        stringRedisTemplate.opsForValue().set(LOGIN_FAIL_TIMES_PRE + username, loginFailTimes + "", loginProperties.getLoginFailMaxThenLockTimes(), TimeUnit.MINUTES);
        if (loginFailTimes >= loginProperties.getMaxLoginFailTimes()) {
            stringRedisTemplate.opsForValue().set("userLoginDisableFlag:" + username, "fail", loginProperties.getLoginFailMaxThenLockTimes(), TimeUnit.MINUTES);
            return false;
        }
        return true;
    }

    @Override
    @Operation(summary = "登陆失败回调")
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            recordLoginTimes(request.getParameter(REQUEST_PARAMETER_USERNAME));
            String failTimesStr = stringRedisTemplate.opsForValue().get(LOGIN_FAIL_TIMES_PRE + request.getParameter(REQUEST_PARAMETER_USERNAME));
            //已错误的次数
            int userFailTimes = 0;
            if(!NullUtils.isNull(failTimesStr)){
                userFailTimes = Integer.parseInt(failTimesStr);
            }
            int restLoginTime = loginProperties.getMaxLoginFailTimes() - userFailTimes;
            if(restLoginTime < 5 && restLoginTime > 0){
                ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"账号密码不正确，还能尝试登录" + restLoginTime + "次"));
            } else if(restLoginTime < 1) {
                ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"重试超限，请您" + loginProperties.getLoginFailMaxThenLockTimes() + "分后再登录"));
            } else {
                ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"账号密码不正确"));
            }
        } else if (exception instanceof AuthException){
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,((AuthException) exception).getMsg()));
        } else if (exception instanceof DisabledException) {
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"账户处于禁用状态，无法登录"));
        } else {
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"系统当前不能登录，请稍后再试"));
        }
    }
}
