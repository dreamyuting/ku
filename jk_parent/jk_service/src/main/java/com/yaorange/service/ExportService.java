package com.yaorange.service;

import com.yaorange.entity.Export;
import com.yaorange.utils.Pagination;

public interface ExportService {
    Pagination getPage(Integer pageSize, Integer pageNo);

    void add(Export export);

    void delete(String[] ids);

    void updateState(String[] ids, long l);

    void update(Export export);

    Export getById(String exportId);
}
