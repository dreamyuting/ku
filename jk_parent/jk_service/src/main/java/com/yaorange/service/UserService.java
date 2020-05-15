package com.yaorange.service;

import com.yaorange.entity.User;
import com.yaorange.utils.Pagination;

import java.util.List;

public interface UserService {

    void save(User user);

    Pagination getPage(Integer pageSize, Integer pageNo);

    void update(User user);

    void delete(String id);

    void updateRole(String[] roleIds, String userId);
}
