package com.yaorange.web;

import com.yaorange.entity.Export;
import com.yaorange.service.ExportService;
import com.yaorange.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/export")
public class ExportHandler {
    @Resource(name = "exportService")
    private ExportService exportService;
    @GetMapping("/getPage")
    public Pagination getPage(Integer pageSize, Integer pageNo){
        return exportService.getPage(pageSize,pageNo);
    }
    @GetMapping("/{exportId}")
    public Export getPage(@PathVariable("exportId") String exportId){
        return exportService.getById(exportId);
    }
    @PutMapping("/update")
    public String update(@RequestBody Export export){
      //  System.out.println(export);
        exportService.update(export);
        return "success";
    }
    @PostMapping("/add")
    public String add(@RequestBody Export export){
        exportService.add(export);
        return "success";
    }
    @DeleteMapping("delete")
    public String delete(@RequestBody String[] ids){
        exportService.delete(ids);
        return "success";
    }
    @PutMapping("/submit")
    public String submit(@RequestBody String[] ids) {
        exportService.updateState(ids, 1L);
        return "success";
    }
    @PutMapping("/cancel")
    public String cancel(@RequestBody String[] ids){
        exportService.updateState(ids,0L);
        return "success";
    }

}
