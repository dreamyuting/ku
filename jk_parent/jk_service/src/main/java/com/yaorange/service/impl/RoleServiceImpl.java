package com.yaorange.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.yaorange.dao.impl.RoleDaoImpl;
import com.yaorange.entity.Module;
import com.yaorange.entity.Role;
import com.yaorange.service.RoleService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource(name="roleDao")
    private RoleDaoImpl roleDao;
    private String hql="from Role";
    @Override
    public List<Role> getAllRole() {

        return roleDao.findAll(hql);
    }
    @Override
    public Pagination getPage(Integer pageSize, Integer pageNo) {
        return roleDao.getPageByHql(hql,pageSize,pageNo);
    }
    @Override
    public void addRole(Role role, String ids) {
        //将字符串ids（node1，node2 ，node3）拆分成字符串数组，再将其赋值给role中的属性Set<Module>，在调用role中的add方法，增加role
        //怎样将ids 变成set<Module>,方法如下：
        //1.ids变成数组
        //2.遍历数组得到每个id
        //3.构建set<Module>集合
        //4.构建module对象
        //5.调用module中的setModuleId(id)
        //6.把每个module对象加入到set<Module>中
        //7.调用role中的set方法，将Set<Module>放入role
        //8.增加role
        if (ids!=null&&!(ids.trim().isEmpty())){
            String[] splits = ids.split(",");//字符串转数组

            Set<Module>  modules =new HashSet<Module>();//集合 role的属性，ids赋值给role集合
           for (String split:splits){
               Module module = new Module();
               module.setModuleId(split);
               modules.add(module);
           }
           role.setModuleSet(modules);
           roleDao.add(role);
        }
 }

    @Override
    public Role getRoleById(String roleId) {
        return roleDao.get(roleId);
    }

    @Override
    public void update(Role role, String ids) {
        if (ids!=null&&!(ids.trim().isEmpty())){
            String[] splits = ids.split(",");//字符串转数组

            Set<Module>  modules =new HashSet<Module>();//集合 role的属性，ids赋值给role集合
            for (String split:splits){
                Module module = new Module();
                module.setModuleId(split);
                modules.add(module);
            }
            role.setModuleSet(modules);
            roleDao.update(role);
        }
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }


}
