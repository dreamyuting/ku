package com.yaorange.service.impl;

import com.yaorange.dao.impl.ExportProductDaoImpl;
import com.yaorange.entity.ExportProduct;
import com.yaorange.service.ExportProductService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("exportProductService")
public class ExportProductServiceImpl implements ExportProductService {
    @Resource(name = "exportProductDao")
    private ExportProductDaoImpl exportProductDao;
    private String hql="from ExportProduct ep where ep.export.id=?1";

    @Override
    public Pagination getPage(Integer pageSize, Integer pageNo, String exportId) {
        return exportProductDao.getPageByHql(hql,pageSize,pageNo,exportId);
    }

    @Override
    public void update(ExportProduct exportProduct) {
        exportProductDao.update(exportProduct);
    }
}
