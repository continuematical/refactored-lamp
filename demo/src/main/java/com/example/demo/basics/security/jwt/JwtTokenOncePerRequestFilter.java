package com.example.demo.basics.security.jwt;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.basics.baseVo.TokenUser;
import com.example.demo.basics.parameter.LoginProperties;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.basics.utils.ResponseUtil;
import com.example.demo.basics.utils.SecurityUtil;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Tag(name = "自定义权限过滤")
@Slf4j
@Component
public class JwtTokenOncePerRequestFilter extends OncePerRequestFilter {

    private SecurityUtil securityUtil;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    private LoginProperties loginProperties;

    private static final boolean RESPONSE_FAIL_FLAG = false;

    private static final int RESPONSE_NO_ROLE_CODE = 401;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader(LoginProperties.HTTP_HEADER);
        if (NullUtils.isNull(tokenHeader)) {
            tokenHeader = request.getParameter(LoginProperties.HTTP_HEADER);
        }
        if (NullUtils.isNull(tokenHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken token = getUsernamePasswordAuthenticationToken(tokenHeader, response);
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (Exception e) {
            log.warn("自定义权限过滤失败" + e);
        }
        filterChain.doFilter(request, response);
    }

    @Operation(summary = "判断登陆是否失效")
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String header, HttpServletResponse response) {
        String userName = null;
        String tokenInRedis = redisTemplate.get(LoginProperties.HTTP_TOKEN_PRE + header);
        if (NullUtils.isNull(tokenInRedis)) {
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG, RESPONSE_NO_ROLE_CODE, "登录状态失效，需要重登！"));
            return null;
        }

        TokenUser tokenUser = JSONObject.parseObject(tokenInRedis, TokenUser.class);
        userName = tokenUser.getUsername();
        List<GrantedAuthority> permissionList = new ArrayList<>();
        if (loginProperties.getSaveRoleFlag()) {
            for (String permission : tokenUser.getPermissions()) {
                permissionList.add(new SimpleGrantedAuthority(permission));
            }
        } else {
            permissionList = securityUtil.getCurrUserPerms(userName);
        }
        if (!tokenUser.getSaveLogin()) {
            redisTemplate.set(LoginProperties.USER_TOKEN_PRE + userName, header, loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
            redisTemplate.set(LoginProperties.HTTP_TOKEN_PRE + header, tokenInRedis, loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
        }
        if (!NullUtils.isNull(userName)) {
            User user = new User(userName, "", permissionList);
            return new UsernamePasswordAuthenticationToken(user, null, permissionList);
        }
        return null;
    }

    public JwtTokenOncePerRequestFilter(RedisTemplateHelper redis, SecurityUtil securityUtil, LoginProperties loginProperties) {
        this.redisTemplate = redis;
        this.securityUtil = securityUtil;
        this.loginProperties = loginProperties;
    }
}
