package com.example.demo.basics.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.example.demo.basics.baseVo.TokenUser;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Tag(name = "登陆成功回调")
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private LoginProperties loginProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final boolean RESPONSE_SUCCESS_FLAG = true;

    private static final int RESPONSE_SUCCESS_CODE = 200;

    private static final String TOKEN_REPLACE_STR_FRONT = "-";

    private static final String TOKEN_REPLACE_STR_BACK = "";

    @Override
    @Operation(summary = "登陆成功回调")
    @SystemLog(about = "登陆系统", type = LogType.LOGIN)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String saveLogin = request.getParameter(LoginProperties.SAVE_LOGIN_PRE);
        Boolean saveLoginFlag = false;
        if(!NullUtils.isNull(saveLogin) && Objects.equals(saveLogin,"true")){
            saveLoginFlag = true;
        }
        List<String> permissionsList = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
        for(GrantedAuthority g : authorities){
            permissionsList.add(g.getAuthority());
        }
        String token = UUID.randomUUID().toString().replace(TOKEN_REPLACE_STR_FRONT, TOKEN_REPLACE_STR_BACK);
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        TokenUser user = new TokenUser(username, permissionsList, saveLoginFlag);
        // 判断是否存储菜单权限
        if(!loginProperties.getSaveRoleFlag()){
            user.setPermissions(null);
        }
        // 单点登录判断
        if(loginProperties.getSsoFlag()){
            String oldToken = redisTemplate.opsForValue().get(LoginProperties.USER_TOKEN_PRE + username);
            if(StrUtil.isNotBlank(oldToken)){
                redisTemplate.delete(LoginProperties.HTTP_TOKEN_PRE + oldToken);
            }
        }
        if(saveLoginFlag){
            redisTemplate.opsForValue().set(LoginProperties.USER_TOKEN_PRE + username, token, loginProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
            redisTemplate.opsForValue().set(LoginProperties.HTTP_TOKEN_PRE + token, JSON.toJSONString(user), loginProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
        }else{
            redisTemplate.opsForValue().set(LoginProperties.USER_TOKEN_PRE + username, token, loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(LoginProperties.HTTP_TOKEN_PRE + token, JSON.toJSONString(user), loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
        }
        ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_SUCCESS_FLAG,RESPONSE_SUCCESS_CODE,"登录成功", token));
    }
}
