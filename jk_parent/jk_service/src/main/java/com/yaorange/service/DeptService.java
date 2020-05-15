package com.yaorange.service;

import com.yaorange.entity.Dept;
import com.yaorange.utils.Pagination;

import java.util.List;

public interface DeptService {
    List<Dept> getAllDept();

    Pagination getPage(Integer pageSize, Integer pageNo);

    void save(Dept dept);

    void update(Dept dept);

    void delete(String id);
}
