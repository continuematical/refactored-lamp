package com.example.demo.Config;

import com.example.demo.Bean.Cat;
import com.example.demo.Bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//组建的名字默认是全命名
@Import(com.alibaba.druid.FastsqlException.class)
//这是配置类，替代以前的配置文件，本身也是容器中的组件
@SpringBootConfiguration
public class AppConfig {
    //可以修改方法名
    @Bean("userData")
    public User getUser() {
        User user = new User();
        user.setId(1L);
        user.setAge(10);
        user.setName("张三");
        return user;
    }

    //条件注解
    //存在这个类
    @ConditionalOnClass(name = "com.alibaba.druid.FastsqlException")
    @Bean
    public Cat getCat(){
        return new Cat();
    }

    //不存在这个类
    @ConditionalOnMissingClass("com.alibaba.druid.FastsqlException")
    @Bean
    public User user(){
        return new User();
    }
}
