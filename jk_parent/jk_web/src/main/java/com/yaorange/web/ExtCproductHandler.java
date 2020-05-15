package com.yaorange.web;

import com.yaorange.entity.ExtCproduct;
import com.yaorange.service.ExtCproductService;
import com.yaorange.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extCproduct")
public class ExtCproductHandler {
    @Autowired
    @Qualifier("extCproductService")
    private ExtCproductService extCproductService;
    @GetMapping("/getPage")
    private Pagination getPage(Integer pageSize,Integer pageNo,String contractProductId){
        return extCproductService.getPage(pageSize,pageNo,contractProductId);
    }
    @PostMapping("/add")
    private String add(@RequestBody ExtCproduct extCproduct){
        extCproductService.add(extCproduct);
        return "1";
    }
    @PutMapping("/update")
    private String update(@RequestBody ExtCproduct extCproduct){
        extCproductService.update(extCproduct);
        return "1";
    }
    @DeleteMapping("/delete/{contactProductId}")
    public String delete(@RequestBody String[] ids,@PathVariable("contactProductId") String contactProductId){
        extCproductService.delete(ids,contactProductId);
        return "1";
    }
}
