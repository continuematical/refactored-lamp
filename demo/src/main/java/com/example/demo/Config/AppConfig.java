package com.example.demo.Config;

import com.example.demo.Bean.Cat;
import com.example.demo.Bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//属性值绑定 第三种方法
//1. 开启组件的自动绑定；2. 默认将组件装在自己容器里；3. 导入第三方组件进行属性绑定
@EnableConfigurationProperties(User.class)
//组建的名字默认是全命名
@Import(com.alibaba.druid.FastsqlException.class)
//这是配置类，替代以前的配置文件，本身也是容器中的组件
@SpringBootConfiguration
public class AppConfig {
    //可以修改方法名
    @Bean("userData")
    public User getUser() {
        return new User();
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
