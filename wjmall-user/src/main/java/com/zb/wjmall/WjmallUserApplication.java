package com.zb.wjmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.zb.wjmall.user.mapper")
public class WjmallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjmallUserApplication.class, args);
    }

}
