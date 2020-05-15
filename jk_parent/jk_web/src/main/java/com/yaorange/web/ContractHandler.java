package com.yaorange.web;

import com.yaorange.entity.Contract;
import com.yaorange.service.ContractService;
import com.yaorange.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/contract")
public class ContractHandler {
    @Resource(name="contractService")
    private ContractService contractService;
    @GetMapping("/getPage")
    private Pagination getPage(Integer pageSize,Integer pageNo){
        return contractService.getPage(pageSize,pageNo);
    }
    @GetMapping("/{state}")
    //用于保运合同管理，查询状态为1的合同
    public Pagination getPageBy(Integer pageSize,Integer pageNo,@PathVariable("state") Long state,String contractNo){
        return contractService.getPageBy(pageSize,pageNo,state,contractNo);
    }

    @PostMapping("/add")
    private String add(@RequestBody Contract contract){
        contractService.add(contract);
        return "1";
    }
    @PutMapping("/update")
    private String update(@RequestBody Contract contract){
       contractService.update(contract);
        return "success";
    }
    @DeleteMapping("/deleteContract")
    private String deleteContract(@RequestBody String[] groupId){
        for (String s : groupId) {
            contractService.deleteContract(s);
        }
        return "success";
    }
    @PutMapping("/submit")
    private String submit(@RequestBody String[] groupId){
        for (String s : groupId) {
            contractService.submit(s);
        }
        return "success";
    }
    @PutMapping("/cancel")
    private String cancel(@RequestBody String[] groupId){
        for (String s : groupId) {
            contractService.cancel(s);
        }
        return "success";
    }


}
