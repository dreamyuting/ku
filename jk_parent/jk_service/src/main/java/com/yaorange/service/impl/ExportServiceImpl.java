package com.yaorange.service.impl;

import com.yaorange.dao.impl.ExportDaoImpl;
import com.yaorange.entity.*;
import com.yaorange.service.ContractProductService;
import com.yaorange.service.ContractService;
import com.yaorange.service.ExportService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("exportService")
public class ExportServiceImpl implements ExportService {
    @Resource(name = "exportDao")
    private ExportDaoImpl exportDao;
    @Resource(name = "contractProductService")
    private ContractProductService contractProductService;
    @Resource(name = "contractService")
    private ContractService contractService;
    private String hql="from Export";
    @Override
    public Pagination getPage(Integer pageSize, Integer pageNo) {
        return exportDao.getPageByHql(hql,pageSize,pageNo) ;
    }

    @Override
    public void add(Export export) {
        String contractIds = export.getContractIds();
        List<ContractProduct> contractProductList = contractProductService.getListByIds(contractIds);

        Set<ExportProduct> exportProductSet = new HashSet<ExportProduct>();
        for (ContractProduct contractProduct : contractProductList) {
            ExportProduct exportProduct = new ExportProduct();
            exportProduct.setProductNo(contractProduct.getProductNo());
            exportProduct.setPackingUnit(contractProduct.getPackingUnit());
            exportProduct.setCnumber(contractProduct.getCnumber());
            exportProduct.setBoxNum(contractProduct.getBoxNum());
            exportProduct.setPrice(contractProduct.getPrice());
            exportProduct.setOrderNo(contractProduct.getOrderNo());
            exportProduct.setFactory(contractProduct.getFactory());
            exportProduct.setExport(export);
            exportProductSet.add(exportProduct);
            Set<ExtCproduct> extCproductSet = contractProduct.getExtCproductSet();
            for (ExtCproduct extCproduct : extCproductSet) {
                ExtEproduct extEproduct = new ExtEproduct();
                extEproduct.setProductNo(extCproduct.getProductNo());
                extEproduct.setProductImage(extCproduct.getProductImage());
                extEproduct.setProductDesc(extCproduct.getProductDesc());
                extEproduct.setCnumber(extCproduct.getCnumber());
                extEproduct.setPackingUnit(extCproduct.getPackingUnit());
                extEproduct.setPrice(extCproduct.getPrice());
                extEproduct.setAmount(extCproduct.getAmount());
                extEproduct.setProductRequest(extCproduct.getProductRequest());
                extEproduct.setOrderNo(extCproduct.getOrderNo());
                extEproduct.setFactory(extCproduct.getFactory());
                extEproduct.setExportProduct(exportProduct);
                exportProduct.getExtEproductSet().add(extEproduct);
            }
        }
        export.setExportProductSet(exportProductSet);
        export.setInputDate(new Date());
        exportDao.add(export);
        contractService.updateState(export.getContractIds().split(","),2L);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            exportDao.delete(id);
        }
    }

    @Override
    public void updateState(String[] ids, long l) {
        for (String id : ids) {
            Export export = exportDao.get(id);
            export.setState(l);
        }
    }

    @Override
    public void update(Export export) {
        exportDao.update(export);
    }

    @Override
    public Export getById(String exportId) {
        return exportDao.get(exportId);
    }

}
