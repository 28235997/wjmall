package com.zb.wjmall.service;

import com.zb.wjmall.bean.UserAddress;
import com.zb.wjmall.bean.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserInfo> userInfoList();

    UserInfo login(UserInfo userInfo);

    List<UserAddress> getUserAddressList(String userId);
}
