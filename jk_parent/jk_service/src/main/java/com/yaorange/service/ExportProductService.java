package com.yaorange.service;

import com.yaorange.entity.ExportProduct;
import com.yaorange.utils.Pagination;

public interface ExportProductService {
    Pagination getPage(Integer pageSize, Integer pageNo, String exportId);

    void update(ExportProduct exportProduct);
}
