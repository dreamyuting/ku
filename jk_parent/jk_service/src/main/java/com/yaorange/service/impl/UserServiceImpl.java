package com.yaorange.service.impl;

import com.yaorange.dao.impl.UserDaoImpl;
import com.yaorange.dao.impl.UserInfoDaoImpl;
import com.yaorange.entity.Role;
import com.yaorange.entity.User;
import com.yaorange.entity.UserInfo;
import com.yaorange.service.UserService;
import com.yaorange.utils.Pagination;
import com.yaorange.utils.util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource(name = "userDao")
    private UserDaoImpl userDaoImpl;
    private String hql="from User";

    @Override
    public void save(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = user.getUserInfo();
                String email = userInfo.getEmail();//收件地址
                String sendAdress="17828169429@sina.com";
                String title="welcome you to join us!";
                String context=user.getUsername()+"：欢迎加入我们";
                try {
                    boolean b = util.sendMail(sendAdress,email, title, context);
                    if (b){
                        System.out.println("ok");
                    }
                } catch (MessagingException e) {
                    System.out.println("error");
                }
            }
        }).start();

        userDaoImpl.add(user);
    }
    @Override
    public Pagination getPage(Integer pageSize, Integer pageNo) {

        return userDaoImpl.getPageByHql(hql,pageSize,pageNo);
    }
    @Override
    public void update(User user) {
        userDaoImpl.update(user);
    }
    @Override
    public void delete(String id) {
        userDaoImpl.delete(id);
    }
    @Override
    public void updateRole(String[] roleIds, String userId) {
        //获取当前用户
        User user = (User)userDaoImpl.get(userId);
        if(roleIds != null && roleIds.length > 0){
            Set<Role> roles = new HashSet<Role>();
            //遍历数组获取角色对象
            for (String roleId : roleIds) {
                //可以不用查询,因为只需要角色对象有id值就可以了
                //静态构造
                Role role = new Role();
                role.setRoleId(roleId);
                roles.add(role);
            }
            //讲角色集合赋值给用户对象，让对象能够自动向中间表中添加关联数据
            //原因是User具有维护权
            user.setRoles(roles);
        }
        //更新用户的同时触发关联维护
        userDaoImpl.update(user);

    }
}
