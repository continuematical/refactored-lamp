package com.example.demo.basics.baseVo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回主数据", notes = "泛型")
    private T result;

    @ApiModelProperty(value = "是否请求成功", notes = "true:请求成功，false:请求失败")
    private boolean success;

    @ApiModelProperty(value = "返回状态代码", notes = "默认200为成功")
    private Integer code;

    @ApiModelProperty(value = "时间戳", notes = "当前系统的时间戳")
    private long timestamp = System.currentTimeMillis();

    @ApiModelProperty(value = "提示信息", notes = "额外的提示信息")
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
