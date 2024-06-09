package com.example.demo.basics.log;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.*;

@Tag(name = "日志实体类")
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    @Operation(summary = "日志名称")
    public String about() default "系统日志";

    @Operation(summary = "日志类型")
    public LogType type() default LogType.DEFAULT_OPERATION;

    @Operation(summary = "操作代码")
    String dotype() default "";
}
