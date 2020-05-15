package com.yaorange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


//lombok注解
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="role_p" )
@GenericGenerator(name = "jk-uuid",strategy = "uuid")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID =  7416382256428616578L;
    @Id
    @Column(name = "ROLE_ID" )
    @GeneratedValue(generator = "jk-uuid")
    private String roleId;

    @Column(name = "NAME" )
    private String name;

    @Column(name = "REMARK" )
    private String remark;

    @Column(name = "ORDER_NO" )
    private Long orderNo;

    public String getRoleId() {
        return this.roleId;
    }

    //===========以上都是普通字段映射==========
    //-------------------配置关联映射----------------------
    /*配置角色和模块：多对多关系
        关联方式：基于中间表
        单还是双向：目前使用单向
    */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_module_p",joinColumns = @JoinColumn(name = "ROLE_ID"),inverseJoinColumns = @JoinColumn(name = "MODULE_ID"))
    private Set<Module> moduleSet = new HashSet<Module>();

    //角色和用户是单向配置（只能通过用户获取用户的角色，而不能通过角色获取当前角色的所有用户），所以不配置用户关联
}
