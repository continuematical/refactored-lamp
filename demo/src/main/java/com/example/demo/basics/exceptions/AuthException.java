package com.example.demo.basics.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

@Schema(name = "自定义异常")
public class AuthException extends InternalAuthenticationServiceException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MSG = "系统鉴权失败";

    @Schema(name = "异常信息内容")
    private String msg;

    public AuthException(String message) {
        super(message);
        this.msg = message;
    }

    public AuthException() {
        super(DEFAULT_MSG);
        this.msg = DEFAULT_MSG;
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
