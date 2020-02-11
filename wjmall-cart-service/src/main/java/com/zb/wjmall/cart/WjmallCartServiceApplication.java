package com.zb.wjmall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.zb.wjmall.cart.mapper")
@ComponentScan("com.zb.wjmall.util")
public class WjmallCartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjmallCartServiceApplication.class, args);
    }

}
