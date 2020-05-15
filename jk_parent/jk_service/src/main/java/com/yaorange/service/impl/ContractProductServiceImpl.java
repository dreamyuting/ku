package com.yaorange.service.impl;

import com.yaorange.dao.impl.ContractDaoImpl;
import com.yaorange.dao.impl.ContractProductDaoImpl;
import com.yaorange.entity.Contract;
import com.yaorange.entity.ContractProduct;
import com.yaorange.service.ContractProductService;
import com.yaorange.service.ContractService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contractProductService")
public class ContractProductServiceImpl implements ContractProductService {
    @Resource(name = "contractProductDao")
    private ContractProductDaoImpl contractProductDao;
    @Resource(name = "contractDao")
    private ContractDaoImpl contractDao;
    private String hql="from ContractProduct cp  where cp.contract.contractId=?1";

    @Override
    public Pagination getPage(Integer pageSize, Integer pageNo, String contractId) {
        return contractProductDao.getPageByHql(hql,pageSize,pageNo,contractId);
    }

    @Override
    public void add(ContractProduct contractProduct) {
        //分散计算思考：在每次新增货物或新增附件时，都需要计算本次新增操作的总金额
        //将总金额设置到合同货物中，并且完成分散思考，
        //将本次货物的总金额更新到合同中（方便查询合同时直接获取总金额，不需要遍历相加计算）
        long amount = contractProduct.getPrice() * contractProduct.getCnumber();
        //将总金额设置给当前货物
        contractProduct.setAmount(amount);
        //分散计算思想：更新合同的总金额
        //查询合同对象
        Contract contract = contractDao.get(contractProduct.getContract().getContractId());
        contract.setTotalAmount(contract.getTotalAmount()+amount);
        contractProductDao.add(contractProduct);

    }

    @Override
    public void update(ContractProduct contractProduct) {
        ContractProduct oldProduct = contractProductDao.get(contractProduct.getContractProductId());
        Long oldAmount = oldProduct.getAmount();
        long newAmount = contractProduct.getPrice() * contractProduct.getCnumber();
        contractProduct.setAmount(newAmount);
        if (newAmount!=oldAmount){
            Contract contract = contractDao.get(contractProduct.getContract().getContractId());
            long amount = newAmount - oldAmount;
            contract.setTotalAmount(contract.getTotalAmount()+amount);
        }
        contractProductDao.update(contractProduct);
    }

    @Override
    public void delete(String s) {
        ContractProduct contractProduct = contractProductDao.get(s);
        Long amount = contractProduct.getAmount();
        Contract contract = contractDao.get(contractProduct.getContract().getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-amount);
        contractProduct.setContract(null);//取消外键关联
        contractProduct.setFactory(null);//取消外键关联
        contractProductDao.delete(s);
    }
    @Override
    //查询指定船期的货物集合
    public List<ContractProduct> getOutProducts(String outDateString) {
        //船期是在合同表中的SHIP_TIME列，数据是"2018-12-12 00:00:00"
        //但是参数条件：2020-04，所以在查询语句中必须使用数据库内置方法处理数据库数据后进行判断
        return contractProductDao.findAll("from ContractProduct cp where DATE_FORMAT(cp.contract.shipTime,'%Y-%m')=?1",outDateString);
    }

    @Override
    public List<ContractProduct> getListByIds(String contractIds) {
//        SELECT * from contract_product_c where CONTRACT_ID in("402880e771bedc3d0171bedec1bd0000","402880e771bef8250171bef9cd780000")
        //将前端传入的123,456  ---> '123','456'
        contractIds = "\'"+contractIds.replaceAll(",","','")+"\'";
        return contractProductDao.findAll("from ContractProduct cp where cp.contract.contractId in("+contractIds+")");
    }

    @Override
    public List<Object[]> findFactorySale() {
        return  contractProductDao.findAll("select cp.factoryName, sum(cp.cnumber) from ContractProduct cp group by cp.factoryName");
    }

    @Override
    public List<Object[]> findProductSale() {
        return contractProductDao.findAll("select cp.productNo, sum(cp.cnumber) from ContractProduct cp group by cp.productNo");
    }

    public static void main(String[] args) {
        String contractIds = "123,456,789";
        System.out.println("\'"+contractIds.replaceAll(",","','")+"\'");
    }

}
