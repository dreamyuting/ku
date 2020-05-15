package com.yaorange.service;

import com.yaorange.entity.ContractProduct;

import java.util.List;

public interface OutProductService {
    List<ContractProduct> getOutProducts(String outDate);
}
