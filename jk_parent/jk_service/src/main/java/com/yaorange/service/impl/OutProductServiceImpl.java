package com.yaorange.service.impl;

import com.yaorange.dao.impl.OutProductDaoImpl;
import com.yaorange.entity.ContractProduct;
import com.yaorange.service.OutProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("outProductService")
public class OutProductServiceImpl implements OutProductService {
    @Resource(name = "outProductDao")
    private OutProductDaoImpl outProductDao;
    private String hql="from ContractProduct cp where DATE_FORMAT(cp.contract.shipTime,'%Y-%m')=?1";
    @Override
    public List<ContractProduct> getOutProducts(String outDate) {
        return outProductDao.findAll(hql,outDate);
    }
}
