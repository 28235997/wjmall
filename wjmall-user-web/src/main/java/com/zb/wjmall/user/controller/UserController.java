package com.zb.wjmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zb.wjmall.bean.UserInfo;
import com.zb.wjmall.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    //不能用autowired，因为在两个项目里，只能用reference，远程调用
    @Reference
    UserService userService;

    @RequestMapping("/userInfoList")
    public ResponseEntity<List<UserInfo>> userInfoList() {
        List<UserInfo> userInfoList = userService.userInfoList();
        return ResponseEntity.ok(userInfoList);
    }

}
