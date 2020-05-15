package com.yaorange.web;

import com.yaorange.entity.ContractProduct;
import com.yaorange.service.ContractProductService;
import com.yaorange.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/contractProduct")
public class ContractProductHandler {
    @Resource(name = "contractProductService")
    private ContractProductService contractProductService;
    @GetMapping("/getPage")
    private Pagination getPage(Integer pageSize,Integer pageNo,String contractId){
        return contractProductService.getPage(pageSize,pageNo,contractId);
    }
    @PostMapping("/add")
    private String add(@RequestBody ContractProduct contractProduct){
        contractProductService.add(contractProduct);
        return "success";
    }
    @PutMapping("/update")
    private String update(@RequestBody ContractProduct contractProduct){
        contractProductService.update(contractProduct);
        return "success";
    }
    @DeleteMapping("/remove")
    private String delete(@RequestBody String[] groupId){
        for (String s : groupId) {
            contractProductService.delete(s);
        }
        return "success";
    }

}
