package com.yaorange.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="module_p" )
@GenericGenerator(name = "jk-uuid",strategy = "uuid")
@EqualsAndHashCode
public class Module extends BaseEntity implements Serializable {

    private static final long serialVersionUID =  781032928721095718L;
    @Id
    @Column(name = "MODULE_ID" )
    @GeneratedValue(generator = "jk-uuid")
    private String moduleId;

    @Column(name = "PARENT_ID" )
    private String parentId;

    @Column(name = "PARENT_NAME" )
    private String parentName;

    @Column(name = "NAME" )
    private String name;

    @Column(name = "LAYER_NUM" )
    private Long layerNum;

    @Column(name = "IS_LEAF" )
    private Long isLeaf;

    @Column(name = "ICO" )
    private String ico;

    @Column(name = "CPERMISSION" )
    private String cpermission;

    @Column(name = "CURL" )
    private String curl;

    @Column(name = "CTYPE" )
    private Long ctype;

    @Column(name = "STATE" )
    private Long state;

    @Column(name = "BELONG" )
    private String belong;

    @Column(name = "CWHICH" )
    private String cwhich;

    @Column(name = "QUOTE_NUM" )
    private Long quoteNum;

    @Column(name = "REMARK" )
    private String remark;

    @Column(name = "ORDER_NO" )
    private Long orderNo;

}

