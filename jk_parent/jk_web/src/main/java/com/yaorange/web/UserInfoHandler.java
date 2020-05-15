package com.yaorange.web;

import com.yaorange.entity.Dept;
import com.yaorange.entity.UserInfo;
import com.yaorange.service.DeptService;
import com.yaorange.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/userInfo")
public class UserInfoHandler {
    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @RequestMapping("/getAllUserInfo")
//    @ResponseBody
    private List<UserInfo> getAllUserInfo() {
        return userInfoService.getAllUserInfo();
    }

}
