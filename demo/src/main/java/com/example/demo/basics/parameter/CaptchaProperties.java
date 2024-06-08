package com.example.demo.basics.parameter;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@ApiOperation(value = "验证码接口配置")
@Data
@Configuration
@ConfigurationProperties(prefix = "intercept")
public class CaptchaProperties {
    private static final Long serialVersionUID = 1L;

    @ApiModelProperty(value = "需要图片验证码的接口")
    private List<String> verfication = new ArrayList<>();

    @ApiModelProperty(value = "需要七位验证码验证的接口")
    private List<String> wechat = new ArrayList<>();
}
