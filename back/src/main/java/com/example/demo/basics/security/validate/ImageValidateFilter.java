package com.example.demo.basics.security.validate;

import com.example.demo.basics.parameter.CaptchaProperties;
import com.example.demo.basics.utils.ResponseUtil;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Tag(name = "验证码过滤类")
@Configuration
public class ImageValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PathMatcher pathMatcher;

    private static final boolean RESPONSE_FAIL_FLAG = false;

    private static final int RESPONSE_CODE_FAIL_CODE = 500;

    @Override
    @Operation(summary = "验证码过滤")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Boolean filterFlag = false;
        for(String requestURI : captchaProperties.getVerification()){
            if(pathMatcher.match(requestURI, request.getRequestURI())){
                filterFlag = true;
                break;
            }
        }
        if(!filterFlag) {
            filterChain.doFilter(request, response);
            return;
        }
        String verificationCodeId = request.getParameter("captchaId");
        String userInputCode = request.getParameter("code");
        if(NullUtils.isNull(userInputCode) || NullUtils.isNull(verificationCodeId)){
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_CODE_FAIL_CODE,"验证码为空"));
            return;
        }
        String codeAnsInRedis = redisTemplate.opsForValue().get(verificationCodeId);
        if(NullUtils.isNull(codeAnsInRedis)){
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_CODE_FAIL_CODE,"已过期的验证码，需要重新填写"));
            return;
        }
        if(!Objects.equals(codeAnsInRedis.toLowerCase(),userInputCode.toLowerCase())) {
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_CODE_FAIL_CODE,"验证码不正确"));
            return;
        }
        redisTemplate.delete(verificationCodeId);
        filterChain.doFilter(request, response);
    }
}