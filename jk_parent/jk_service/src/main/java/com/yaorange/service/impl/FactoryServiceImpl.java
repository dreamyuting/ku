package com.yaorange.service.impl;

import com.yaorange.dao.impl.FactoryDaoImpl;
import com.yaorange.entity.Factory;
import com.yaorange.service.FactoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("factoryService")
public class FactoryServiceImpl implements FactoryService {
    @Resource(name = "factoryDao")
    private FactoryDaoImpl factoryDao;
    private String hql="from Factory f where f.ctype=?1";
    @Override
    public List<Factory> all(String ctype) {
        return factoryDao.findAll(hql,ctype);
    }
}
