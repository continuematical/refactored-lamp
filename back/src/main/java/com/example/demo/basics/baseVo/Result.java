package com.example.demo.basics.baseVo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(name = "返回主数据", description = "泛型")
    private T result;

    @Schema(name = "是否请求成功", description = "true:请求成功，false:请求失败")
    private boolean success;

    @Schema(name = "返回状态代码", description = "默认200为成功")
    private Integer code;

    @Schema(name = "时间戳", description = "当前系统的时间戳")
    private long timestamp = System.currentTimeMillis();

    @Schema(name = "提示信息", description = "额外的提示信息")
    private String message;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
