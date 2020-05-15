package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @company: yaorange
 * @author: Mr.Huang
 * @version: 1.0
 * @create: 11-04-21 11:12:28
 */
//lombok注解
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="user_info_p" )
@GenericGenerator(name = "jk-uuid",strategy = "uuid")
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID =  4974752335487827622L;
    @Id
    @Column(name = "USER_INFO_ID" )
    @GeneratedValue(generator = "jk-uuid")
    private String userInfoId;

    @Column(name = "NAME" )
    private String name;

    @Column(name = "JOIN_DATE" )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone ="GMT+8")
    private Date joinDate;//入职时间

    @Column(name = "SALARY" )
    private Double salary;//工资

    @Column(name = "BIRTHDAY" )
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;//生日

    @Column(name = "GENDER" )
    private String gender;//性别

    @Column(name = "STATION" )
    private String station;//岗位

    @Column(name = "TELEPHONE" )
    private String telephone;//电话

    @Column(name = "DEGREE" )
    private Long degree;//级别

    @Column(name = "REMARK" )
    private String remark;//备注

    @Column(name = "ORDER_NO" )
    private Long orderNo;//序号

    @Column(name = "EMAIL" )
    private String email;//邮箱

    @Column(name = "MANAGER_ID" )
    //不配置关联映射，而是基于普通字段映射
    private String managerId;//领导
}

