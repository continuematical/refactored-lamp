package com.example.demo.basics.baseclass;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Configuration
@Slf4j
@Tag(name = "SpringBoot启动配置类")
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class BeansConfiguration {
    @Bean
    @Operation(summary = "初始化PathMatcher")
    public PathMatcher pathMatcher(){
        log.info("初始化PathMatcher成功");
        return new AntPathMatcher();
    }

    @Primary
    @Bean
    @Operation(summary = "初始化线程池")
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        log.info("初始化线程池成功");
        return executor;
    }

    @Bean
    @Operation(summary = "初始化Redis锁")
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "zwz-lock");
    }
}
