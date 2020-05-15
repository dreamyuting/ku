package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="contract_c" )
@GenericGenerator(name = "jk-uuid",strategy = "uuid")
public class Contract extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5237455248247035293L;
    @Id
    @Column(name = "CONTRACT_ID")
    @GeneratedValue(generator = "jk-uuid")
    private String contractId;

    @Column(name = "OFFEROR")
    private String offeror;

    @Column(name = "CONTRACT_NO")
    private String contractNo;

    @Column(name = "SIGNING_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")//指定返回前端的日期数据格式，不指定默认返回全数字
    private Date signingDate;

    @Column(name = "INPUT_BY")
    private String inputBy;

    @Column(name = "CHECK_BY")
    private String checkBy;

    @Column(name = "INSPECTOR")
    private String inspector;

    @Column(name = "TOTAL_AMOUNT")
    private Long totalAmount;

    @Column(name = "CREQUEST")
    private String crequest;

    @Column(name = "CUSTOM_NAME")
    private String customName;

    @Column(name = "SHIP_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date shipTime;

    @Column(name = "IMPORT_NUM")
    private Long importNum;

    @Column(name = "DELIVERY_PERIOD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryPeriod;//交货日期

    @Column(name = "OLD_STATE")
    private Long oldState;

    @Column(name = "OUT_STATE")
    private Long outState;

    @Column(name = "TRADE_TERMS")
    private String tradeTerms;

    @Column(name = "PRINT_STYLE")
    private String printStyle;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATE")
    //0表示草稿合同，1表示已上报待报运，2表示已报运
    private Long state;

    //解决前端个别特殊数据获取（不存在于数据库表，而是前端页面需求显示）
//    1：定义vo（view object）
//    2.在当前实体直接定义普通属性（不映射数据库的字段的属性）
    @JsonIgnore//忽略JSON返回
    @Transient//忽略映射
    private Integer productNum;
    @JsonIgnore
    @Transient//忽略映射
    private Integer extNum;
    @Transient//忽略映射
    private String nums;//给前端返回展示:货物/附件数

    //配置合同和货物关联映射
//    映射关系：一对多
//    关联方式：在多方使用外键方式
//    思考：是否级联，加载策略
    @OneToMany(mappedBy = "contract",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"contract","factory","extCproductSet"})
    private Set<ContractProduct> contractProductSet = new HashSet<ContractProduct>();


    private Integer getProductNum() {//获取合同货物数量
        return getContractProductSet().size();
    }

    private Integer getExtNum() {//获取附件数量
        int num = 0;
        for (ContractProduct contractProduct : contractProductSet) {
            num += contractProduct.getExtCproductSet().size();
        }
        return num;
    }

    public String getNums() {//用于前端返回的格式数据
        return getProductNum()+"/"+getExtNum();
    }
}
