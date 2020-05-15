package com.yaorange.dao.impl;

import com.yaorange.entity.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDao<User,String> {
}
