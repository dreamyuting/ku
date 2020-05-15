package com.yaorange.service.impl;
import com.yaorange.dao.impl.DeptDaoImpl;
import com.yaorange.entity.Dept;
import com.yaorange.service.DeptService;
import com.yaorange.utils.Pagination;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
    @Resource(name = "deptDao")
    private DeptDaoImpl deptDao;
    private String hql="from Dept";
    @Override
    public List<Dept> getAllDept() {
        return deptDao.findAll(hql);
    }
    @Override
    public Pagination getPage( Integer pageSize, Integer pageNo) {
        return  deptDao.getPageByHql(hql ,pageSize,pageNo);
    }
    @Override
    public void save(Dept dept) {
        deptDao.add(dept);
    }
    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }
    @Override
    public void delete(String id) {
        deptDao.delete(id);
    }
}
