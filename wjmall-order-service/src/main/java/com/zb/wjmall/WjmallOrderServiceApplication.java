package com.zb.wjmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.zb.wjmall.order.mapper")
public class WjmallOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjmallOrderServiceApplication.class, args);
    }

}
