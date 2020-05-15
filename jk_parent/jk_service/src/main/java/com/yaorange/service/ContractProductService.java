package com.yaorange.service;

import com.yaorange.entity.ContractProduct;
import com.yaorange.utils.Pagination;

import java.util.List;

public interface ContractProductService {
    Pagination getPage(Integer pageSize, Integer pageNo, String contractId);

    void add(ContractProduct contractProduct);

    void update(ContractProduct contractProduct);

    void delete(String s);

    List<ContractProduct> getOutProducts(String outDateString);

    List<ContractProduct> getListByIds(String contractIds);

    List<Object[]> findFactorySale();

    List<Object[]> findProductSale();
}
