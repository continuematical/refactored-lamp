package com.example.demo.basics.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.data.service.IUserService;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.data.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Tag(name = "登陆判断类")
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserService iUserService;

    private static final String LOGIN_FAIL_DISABLED_PRE = "userLoginDisableFlag:";


    @Override
    @Operation(summary = "根据账号/手机号查询用户所有信息")
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String loginFailFlag = LOGIN_FAIL_DISABLED_PRE + username;
        String value = redisTemplate.opsForValue().get(loginFailFlag);
        Long timeRest = redisTemplate.getExpire(loginFailFlag, TimeUnit.MINUTES);
        if (!NullUtils.isNull(value)) {
            throw new UsernameNotFoundException("试错超限，请您在" + timeRest + "分钟后登录");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(userQueryWrapper -> userQueryWrapper.eq("username", username));
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        User superUser = iUserService.getOne(queryWrapper);
        if(superUser == null) {
            throw new UsernameNotFoundException(username + "不存在");
        }
        return new SecurityUserDetails(superUser);
    }
}
