package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @company: yaorange
 * @author: Mr.Huang
 * @version: 1.0
 * @create: 11-04-21 11:10:47
 */
//lombok注解
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="user_p" )
@GenericGenerator(name = "jk-uuid",strategy = "uuid")
public class User extends BaseEntity implements Serializable {
    @Id
    @Column(name = "USER_ID" )
    @GeneratedValue(generator = "jk-uuid")
    private String userId;
    @Column(name = "USER_NAME" )
    private String username;
    @Column(name = "PASSWORD" )
    private String password;
    @Column(name = "STATE" )
    private Long state;

    //===========以上都是普通字段映射==========
    //-------------------配置关联映射----------------------
    /*配置用户和部门关联映射
        用户和部门关系：多对一关系，那么使用注解@ManyToOne，使用对象属性
        关联方式：外键方式，@JoinColumn
        单还是双：目前根据需求单向配置
        是否需要配置级联（级联是对应更新操作）：Cascade，不需要，不可能出现操作用户表的同时去操作到部门表
        是否修改加载策略：fetch，默认是及时加载就不需要设置，默认是延迟加载就需要(?)
    */
    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Dept dept;

    //配置用户和用户信息：一对一
    //基于外键单向配置，级联所有操作，及时加载
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_INFO_ID")
    private UserInfo userInfo;

    //配置用户和角色：多对多
    //关联方式：基于中间表
    //基于中间表配置，目前单向，不级联，加载策略默认是延迟的，使用时会出错，需要定义
//    @ManyToMany
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user_p",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @JsonIgnoreProperties(value = {"moduleSet"})//指点被标识对象中不进行json转化为属性
    private Set<Role> roles = new HashSet<Role>();

}
