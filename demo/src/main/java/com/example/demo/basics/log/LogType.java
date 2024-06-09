package com.example.demo.basics.log;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 日志枚举类
 */

@Schema(name = "日志枚举类")
public enum LogType {
    /**
     * 默认
     */
    DEFAULT_OPERATION,

    /**
     * 登录
     */
    LOGIN,

    /**
     * 系统基础模块
     */
    DATA_CENTER,

    /**
     * 更多开发模块
     */
    MORE_MODULE,

    /**
     * 图表
     */
    CHART
}
