package com.yaorange.service.impl;

import com.yaorange.dao.impl.ContractDaoImpl;
import com.yaorange.entity.Contract;
import com.yaorange.service.ContractService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("contractService")
public class ContractServiceImpl implements ContractService {
   @Resource(name = "contractDao")
   private ContractDaoImpl contractDao;
   private String hql="from Contract";

   @Override
   public Pagination getPage(Integer pageSize, Integer pageNo) {
      return contractDao.getPageByHql(hql,pageSize,pageNo);
   }

   @Override
   public void add(Contract contract) {
      contractDao.add(contract);
   }

   @Override
   public void update(Contract contract) {
      contractDao.update(contract);
   }

   @Override
   public void deleteContract(String s) {
      contractDao.delete(s);
   }

   @Override
   public void submit(String s) {

      Contract contract = contractDao.get(s);
      contract.setState(1L);
      contractDao.update(contract);
   }

   @Override
   public void cancel(String s) {
      Contract contract = contractDao.get(s);
      contract.setState(0L);
      contractDao.update(contract);
   }
   @Override
   public void updateState(String[] ids, Long state) {
      for (String id : ids) {
         //获取当前id的合同对象
         Contract contract = contractDao.get(id);
         //持久化对象的自动更新功能（快照机制）
         contract.setState(state);
         //更新合同对象
//            contractDao.update(contract);
      }
   }

   @Override
   //通过id获取合同对象
   public Contract getById(String contractId) {
      return contractDao.get(contractId);
   }

   @Override
   public Pagination getPageBy(Integer pageSize, Integer pageNo, Long state,String contractNo) {
      if (contractNo==null||contractNo==""){
         return contractDao.getPageByHql("from Contract c where c.state=?1 order by createTime desc",pageSize,pageNo,state);
      }
      else {
         return contractDao.getPageByHql("from Contract c where c.state=?1 and c.contractNo=?2 order by createTime desc",pageSize,pageNo,state,contractNo);
      }

   }

/*   @Override
   public Pagination getPageBy(Integer pageSize, Integer pageNo, Long state) {
      return contractDao.getPageByHql("from Contract c where c.state=?1 order by createTime desc",pageSize,pageNo,state);
   }*/

}
