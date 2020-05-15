package com.yaorange.web;

import com.yaorange.entity.Dept;
import com.yaorange.entity.User;
import com.yaorange.service.UserInfoService;
import com.yaorange.service.UserService;
import com.yaorange.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserHandler {
    @Resource(name = "userService")
    private UserService userService;
    @PostMapping("/addUser")
    private String addUser(@RequestBody User user) {
        userService.save(user);
        return "1";
    }
    @GetMapping("/getPage")
    private Pagination getPage(Integer pageSize , Integer pageNo){
        return userService.getPage(pageSize,pageNo);
    }
    @PutMapping("/update")
    private String update(@RequestBody User user){
        userService.update(user);
        return "success";
    }
    @DeleteMapping("/delete")
    private String delete(@RequestBody String[] ids){
        for (String id : ids) {
            userService.delete(id);
        }
        return "success";
    }
    @PutMapping("/updateRole/{userId}")
    private String updateRole(@RequestBody String[] roleIds,@PathVariable("userId") String userId){
        userService.updateRole(roleIds,userId);
        return "success";
    }
}
