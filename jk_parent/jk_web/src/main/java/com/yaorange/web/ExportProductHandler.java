package com.yaorange.web;

import com.yaorange.entity.ExportProduct;
import com.yaorange.service.ExportProductService;
import com.yaorange.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/exportProduct")
public class ExportProductHandler {
    @Resource(name = "exportProductService")
    private ExportProductService exportProductService;
    @GetMapping("/{exportId}")
    //通过指定合同id获取合同货物分页对象
    public Pagination getPage(Integer pageSize, Integer pageNo, @PathVariable("exportId") String exportId){
        return exportProductService.getPage(pageSize,pageNo,exportId);
    }
    //更新报运货物
    @PutMapping
    public String update(@RequestBody ExportProduct exportProduct){
        exportProductService.update(exportProduct);
        return "success";
    }

}
