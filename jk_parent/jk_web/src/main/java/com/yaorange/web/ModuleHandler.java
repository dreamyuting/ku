package com.yaorange.web;

import com.yaorange.entity.Module;
import com.yaorange.entity.Role;
import com.yaorange.entity.vo.ZtreeVo;
import com.yaorange.service.ModuleService;
import com.yaorange.service.RoleService;
import com.yaorange.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/module")
public class ModuleHandler {
    @Resource(name = "moduleSerice")
    private ModuleService moduleService;
    @Resource(name = "roleService")
    private RoleService roleService;
    @GetMapping("/getZtreeNodes")
    private List<ZtreeVo> getZtreeNodes(){
        return moduleService.getZtreeNodes();
    }
    @GetMapping("/getZtreeNodes/{roleId}")
    public List<ZtreeVo> getAllModulesByRole(@PathVariable("roleId") String roleId){
        Role role = roleService.getRoleById(roleId);
        Set<Module> moduleSet = role.getModuleSet();
        System.out.println(moduleSet);
        List<Module> modules = moduleService.getAll();
        List<ZtreeVo> nodes = new ArrayList<ZtreeVo>(modules.size());
        for (Module module : modules) {
            ZtreeVo ztreeVo = new ZtreeVo();
            ztreeVo.setId(module.getModuleId());
            ztreeVo.setpId(module.getParentId()==null?"0":module.getParentId());
            ztreeVo.setName(module.getName());
            if(moduleSet.contains(module)){
                System.out.println(module);
                ztreeVo.setChecked(true);
            }else{
                ztreeVo.setChecked(false);
            }
            if (module.getParentId() == null){//一级菜单
                ztreeVo.setOpen(true);//展开
            }else{
                ztreeVo.setOpen(false);//不展开
            }
            nodes.add(ztreeVo);
        }
        return nodes;
    }
    @PostMapping("/addmodule")
    public String addmodule(@RequestBody Module module) {
        moduleService.addmodule(module);
        return "1";
    }
    @GetMapping("/getpage")
    private Pagination getpage(Integer pageSize, Integer pageNo){
       return moduleService.getpage(pageSize,pageNo);
    }
    @PutMapping("/updatemodule")
    private String updatemodule(@RequestBody Module module){
        moduleService.updatemodule(module);
        return "1";
    }
    @DeleteMapping("/deletemodule")
    private String deletemodule(@RequestBody String[] checkedIds){
        for (String checkedId : checkedIds) {
            moduleService.deletemodule(checkedId);
        }
        return "1";
    }
    }
