package com.yaorange.service.impl;

import com.yaorange.dao.impl.DeptDaoImpl;
import com.yaorange.dao.impl.UserInfoDaoImpl;
import com.yaorange.entity.Dept;
import com.yaorange.entity.UserInfo;
import com.yaorange.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Resource(name = "userInfoDao")
    private UserInfoDaoImpl userInfoDao;
    private String hql="from UserInfo";
    @Override
    public List<UserInfo> getAllUserInfo() {
        return userInfoDao.findAll(hql);
    }

}
