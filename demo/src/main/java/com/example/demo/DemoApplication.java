package com.example.demo;

import com.example.demo.Bean.Cat;
import com.example.demo.Bean.Dog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        //获取容器中所有组件的名字
//        String[] names = run.getBeanDefinitionNames();
//        for (String name : names) {
//            System.out.println(name);
//        }
        Cat cat = run.getBean(Cat.class);
        System.out.println(cat);

        Dog dog = run.getBean(Dog.class);
        System.out.println(dog);

        Object user = run.getBean("userData");
        System.out.println(user);
    }
}
