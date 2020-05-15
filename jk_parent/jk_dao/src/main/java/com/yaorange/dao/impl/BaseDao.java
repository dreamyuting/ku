package com.yaorange.dao.impl;

import com.yaorange.entity.Dept;
import com.yaorange.utils.Pagination;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDao<T,ID extends Serializable> {
    @PersistenceContext
    private EntityManager entityManager;
    //定义泛型字节码属性
    private Class<T> entityClass;

    public BaseDao(){
        //获取当前泛型对象的字节码
        Type superclass = getClass().getGenericSuperclass();//获取当前实体的超类
        //将超类类型转换为参数化类型
        ParameterizedType parameterizedType = (ParameterizedType)superclass;//参数化类型才有获取泛型对象字节码方法
        //获取实际泛型对象的字节码
        entityClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
    }
    public List findAll(String hql,Object...objs) {
        //String hql = "from Dept";
        Query query = entityManager.createQuery(hql);
        if(objs!= null){
            for (int i=0;i < objs.length;i++) {
                query.setParameter((i+1),objs[i]);
            }
        }
        return query.getResultList();
    }

    public Pagination getPageByHql( String hql ,Integer pageSize, Integer pageNo,Object ...objs ) {
        //分页查询
        //获取总条数
        String countHql = "select count(*) "+hql ;
        Query query = entityManager.createQuery(countHql);
        Query query1 = entityManager.createQuery(hql);
        if(objs!= null){
            for (int i=0;i < objs.length;i++) {
                query.setParameter((i+1),objs[i]);
                query1.setParameter((i+1),objs[i]);
            }
        }
        Long totalCount = (Long)query.getSingleResult();
        //创建分页对象
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount.intValue());

        //获取当前页面的分页数据
        //String hql = "from Dept";

        //设置分页条件
        //开始位置
        //(pageNo - 1) * pageSize;
        query1.setFirstResult(pagination.getFirstResult());
        //数据长度
        query1.setMaxResults(pageSize);
        //调用方法获取数据
        List<T>  t = query1.getResultList();
        //将数据添加到分页对象中
        pagination.setList(t);
        return pagination;
    }

    public void add(T t) {
        entityManager.persist(t);
    }

    public void update(T t) {
        entityManager.merge(t);
    }

    public void delete(ID id) {
    //    Dept dept = entityManager.find(Dept.class, id);
        T t=entityManager.find(entityClass ,id);

        entityManager.remove(t);

    }
    public T get(ID id){
        return entityManager.find(entityClass ,id);
    }


}
