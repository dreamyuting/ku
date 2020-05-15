package com.yaorange.service.impl;

import com.yaorange.dao.impl.ModuleDaoImpl;
import com.yaorange.entity.Module;
import com.yaorange.entity.vo.ZtreeVo;
import com.yaorange.service.ModuleService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("moduleSerice")
public class ModuleServiceImpl implements ModuleService {
    @Resource(name = "moduleDao")
    private ModuleDaoImpl moduleDao;
    private String hql="from Module";
    @Override
    public List<ZtreeVo> getZtreeNodes() {
        List<Module> all = moduleDao.findAll(hql);
        List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
        for (Module module : all) {
            ZtreeVo ztreeVo = new ZtreeVo();
            ztreeVo.setId(module.getModuleId());
            ztreeVo.setName(module.getName());
            ztreeVo.setpId(module.getParentId()==null?"0":module.getParentId());
            ztreeVo.setChecked(false);
            ztreeVo.setOpen(true);
            ztreeVos.add(ztreeVo);
   }
        return ztreeVos;
    }

    @Override
    public List<Module> getAll() {
        return moduleDao.findAll(hql);
    }

    @Override
    public void addmodule(Module module) {
            moduleDao.add(module);
    }

    @Override
    public Pagination getpage(Integer pageSize, Integer pageNo) {
        return moduleDao.getPageByHql(hql,pageSize,pageNo);
    }

    @Override
    public void updatemodule(Module module) {
       moduleDao.update(module);
    }

    @Override
    public void deletemodule(String checkedId) {
        moduleDao.delete(checkedId);
    }


}
