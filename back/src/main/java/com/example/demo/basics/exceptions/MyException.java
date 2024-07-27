package com.example.demo.basics.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

public class MyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE = "系统出现错误";

    @Schema(name = "异常消息内容")
    private String msg;

    public MyException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MyException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public MyException(){
        super(DEFAULT_MESSAGE);
        this.msg = DEFAULT_MESSAGE;
    }
}
