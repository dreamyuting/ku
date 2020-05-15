package com.yaorange.entity;

import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="factory_c" )
public class Factory extends BaseEntity implements Serializable {

    private static final long serialVersionUID =  4171929358052112036L;
    @Id
    @Column(name = "FACTORY_ID" )
    @GenericGenerator(name = "jk-uuid",strategy = "uuid")
    @GeneratedValue(generator = "jk-uuid")
    private String factoryId;

    @Column(name = "CTYPE" )
    private String ctype;

    @Column(name = "FULL_NAME" )
    private String fullName;

    @Column(name = "FACTORY_NAME" )
    private String factoryName;

    @Column(name = "CONTACTS" )
    private String contacts;

    @Column(name = "PHONE" )
    private String phone;

    @Column(name = "MOBILE" )
    private String mobile;

    @Column(name = "FAX" )
    private String fax;

    @Column(name = "ADDRESS" )
    private String address;

    @Column(name = "INSPECTOR" )
    private String inspector;

    @Column(name = "REMARK" )
    private String remark;

    @Column(name = "ORDER_NO" )
    private Long orderNo;

    @Column(name = "STATE" )
    private String state;

}
