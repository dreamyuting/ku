package com.yaorange.web;

import com.yaorange.entity.Dept;
import com.yaorange.service.DeptService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static java.lang.System.out;


@RestController
@RequestMapping("/dept")
public class DeptHandler {
    @Resource(name = "deptService")
    private DeptService deptService;

    @RequestMapping("/getAllDept")
//    @ResponseBody
    private List<Dept> getAllDept() {
        return deptService.getAllDept();
    }

    @GetMapping("/getPage")
    public Pagination getPage(Integer pageSize, Integer pageNo) {
        return deptService.getPage(pageSize, pageNo);
    }

    @PostMapping("/addDept")
    private String addDept(@RequestBody Dept dept) {
        deptService.save(dept);
        return "1";
    }

    @PutMapping("/update")
    private String update(@RequestBody Dept dept){
        deptService.update(dept);
        return "success";
    }
    @DeleteMapping("/delete")
    private String delete(@RequestBody String[] ids){
        for (String id : ids) {
            deptService.delete(id);
            System.out.println(id);
        }
        return "success";

    }

}