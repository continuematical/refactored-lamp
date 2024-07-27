package com.example.demo.basics.security.jwt;

import com.example.demo.basics.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Tag(name = "自定义权限文案")
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private static final boolean RESPONSE_FAIL_FLAG = false;

    private static final int RESPONSE_NO_SELF_ROLE_CODE = 403;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG, RESPONSE_NO_SELF_ROLE_CODE, "您无权限访问该菜单，谢谢！"));
    }
}
