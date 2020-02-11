package com.zb.wjmall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.zb.wjmall.manage.mapper")
@ComponentScan("com.zb.wjmall.util")
public class WjmallManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjmallManageServiceApplication.class, args);
    }

}
