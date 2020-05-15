package com.yaorange.service;

import com.yaorange.entity.Contract;
import com.yaorange.utils.Pagination;

public interface ContractService {
    Pagination getPage(Integer pageSize, Integer pageNo);

    void add(Contract contract);

    void update(Contract contract);

    void deleteContract(String s);

    void submit(String s);

    void cancel(String s);

    void updateState(String[] ids, Long state);

    Contract getById(String contractId);

    Pagination getPageBy(Integer pageSize, Integer pageNo, Long state,String contractNo);
}
