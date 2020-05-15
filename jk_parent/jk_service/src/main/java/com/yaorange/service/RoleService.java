package com.yaorange.service;

import com.yaorange.entity.Module;
import com.yaorange.entity.Role;
import com.yaorange.utils.Pagination;

import java.util.List;

public interface RoleService {
    List<Role> getAllRole();

    Pagination getPage(Integer pageSize, Integer pageNo);

    void addRole(Role role, String ids);

    Role getRoleById(String roleId);

    void update(Role role, String ids);

    void delete(String id);

}
