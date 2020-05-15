package com.yaorange.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass//标识当前类是映射超类，让其他实体通过继承使用
public class BaseEntity {
    @Column(name = "CREATE_BY")
    private String createBy;
    @Column(name = "CREATE_DEPT")
    private String createDept;
    @Column(name = "CREATE_TIME")
    @CreationTimestamp//当新增一行数据时，自动获取系统时间赋值给被标识属性
    private Date createTime;
    @Column(name = "UPDATE_BY")
    private String updateBy;
    @Column(name = "UPDATE_TIME")
    @UpdateTimestamp//当更新一行数据时，自动获取系统时间赋值给被标识属性
    private Date updateTime;
}
