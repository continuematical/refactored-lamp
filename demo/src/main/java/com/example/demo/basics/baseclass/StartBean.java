package com.example.demo.basics.baseclass;

import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Tag(name = "启动执行类")
public class StartBean implements ApplicationRunner {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    private static final String REDIS_USER_PRE = "USER:";

    @Override
    @Operation(summary = "启动执行方法", description = "用于日志记录用户姓名")
    public void run(ApplicationArguments args) {
        List<User> userList = iUserService.list();
        for (User user : userList) {
            if (user.getNickname() != null && user.getUsername() != null) {
                redisTemplate.set(REDIS_USER_PRE + user.getUsername(), user.getNickname());
            }
        }
    }
}