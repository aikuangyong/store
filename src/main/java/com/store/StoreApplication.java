package com.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.Repeatable;

@EnableSwagger2
@SpringBootApplication
@Configuration
@EnableScheduling
@MapperScan(basePackages = "com.store.dao")
//public class StoreApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(StoreApplication.class, args);
//    }
//}
public class StoreApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StoreApplication.class);
    }
}