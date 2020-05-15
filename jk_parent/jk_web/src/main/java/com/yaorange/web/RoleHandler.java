package com.yaorange.web;

import com.yaorange.entity.Role;
import com.yaorange.service.DeptService;
import com.yaorange.service.RoleService;
import com.yaorange.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleHandler {
    @Resource(name = "roleService")
    private RoleService roleService;

    @GetMapping("/getAllRole")
    private List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    @GetMapping("/getPage")
    private Pagination getPage(Integer pageSize, Integer pageNo) {
        return roleService.getPage(pageSize, pageNo);
    }

    @PostMapping("/addRole/{ids}")
    public String addRole(@RequestBody Role role, @PathVariable("ids") String ids) {
        roleService.addRole(role, ids);
        return "1";
    }

    @PutMapping("/update/{ids}")
    public String update(@RequestBody Role role, @PathVariable("ids") String ids) {
        roleService.update(role, ids);
        return "success";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody String[] ids) {
        for (String id : ids) {

            roleService.delete(id);
        }
        return "success";
    }

}
