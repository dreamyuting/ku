package com.yaorange.service;

import com.yaorange.entity.Module;
import com.yaorange.entity.vo.ZtreeVo;
import com.yaorange.utils.Pagination;

import java.util.List;

public interface ModuleService {
    List<ZtreeVo> getZtreeNodes();

    List<Module> getAll();

    void addmodule(Module module);

    Pagination getpage(Integer pageSize, Integer pageNo);

    void updatemodule(Module module);

    void deletemodule(String checkedId);
}
