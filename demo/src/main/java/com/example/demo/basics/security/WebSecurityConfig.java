package com.example.demo.basics.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Tag(name = "SpringSecurity配置类")
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

}
