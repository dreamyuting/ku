package com.yaorange.service;

import com.yaorange.entity.ExtCproduct;
import com.yaorange.utils.Pagination;

public interface ExtCproductService {
    Pagination getPage(Integer pageSize, Integer pageNo, String contractProductId);

    void add(ExtCproduct extCproduct);

    void update(ExtCproduct extCproduct);

    void delete(String[] ids, String contactProductId);
}
