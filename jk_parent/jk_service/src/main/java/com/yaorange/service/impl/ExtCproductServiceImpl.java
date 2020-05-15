package com.yaorange.service.impl;

import com.yaorange.dao.impl.ContractDaoImpl;
import com.yaorange.dao.impl.ContractProductDaoImpl;
import com.yaorange.dao.impl.ExtCproductDaoImpl;
import com.yaorange.entity.Contract;
import com.yaorange.entity.ContractProduct;
import com.yaorange.entity.ExtCproduct;
import com.yaorange.service.ExtCproductService;
import com.yaorange.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("extCproductService")
public class ExtCproductServiceImpl implements ExtCproductService {
    @Autowired
    @Qualifier("extCproductDao")
    private ExtCproductDaoImpl extCproductDao;
    @Autowired
    @Qualifier("contractProductDao")
    private ContractProductDaoImpl contractProductDao;
    @Autowired
    @Qualifier("contractDao")
    private ContractDaoImpl contractDao;
    private String hql="from ExtCproduct ec where ec.contractProduct.contractProductId=?1";
    @Override
    public Pagination getPage(Integer pageSize, Integer pageNo, String contractProductId) {
        return extCproductDao.getPageByHql(hql,pageSize,pageNo,contractProductId);
    }

    @Override
    public void add(ExtCproduct extCproduct) {
        //设置附件金额
        long amout = extCproduct.getPrice() * extCproduct.getCnumber();
        extCproduct.setAmount(amout);
        //设置总金额 分散思想
        String contractProductId = extCproduct.getContractProduct().getContractProductId();
        System.out.println(extCproduct.getContractProduct());
        ContractProduct contractProduct = contractProductDao.get(contractProductId);
        String contractId = contractProduct.getContract().getContractId();
        Contract contract = contractDao.get(contractId);
        contract.setTotalAmount(contract.getTotalAmount()+amout);
        //设置附件数 分散思想
        Set<ExtCproduct> extCproductSet = contractProduct.getExtCproductSet();
        extCproductSet.add(extCproduct);
        contractProduct.setExtCproductSet(extCproductSet);
       // contract.setExtNum(contract.getExtNum());
      // contract.setNums(contract.getNums());

        //保存
        extCproductDao.add(extCproduct);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        ContractProduct contractProduct = contractProductDao.get(extCproduct.getContractProduct().getContractProductId());
        Contract contract = contractDao.get(contractProduct.getContract().getContractId());
        Long oldAmount = extCproduct.getAmount();
        long newAmout = extCproduct.getPrice() * extCproduct.getCnumber();
        extCproduct.setAmount(newAmout);
        contract.setTotalAmount(contract.getTotalAmount()-oldAmount+newAmout);
        extCproductDao.update(extCproduct);
    }

    @Override
    public void delete(String[] ids,String contactProductId) {
        for (String id : ids) {
            //更改总金额 更改附件数
            ExtCproduct extCproduct = extCproductDao.get(id);

            ContractProduct contractProduct = contractProductDao.get(contactProductId);
            Contract contract = contractDao.get(contractProduct.getContract().getContractId());
            Set<ExtCproduct> extCproductSet = contractProduct.getExtCproductSet();
            extCproductSet.remove(extCproduct);
            contractProduct.setExtCproductSet(extCproductSet);
            contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());

            extCproductDao.delete(id);
        }
    }
}
