package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table ( name ="ext_cproduct_c" )
@GenericGenerator(name = "jk-uuid",strategy = "uuid")
public class ExtCproduct implements Serializable {

    private static final long serialVersionUID =  1642112230668704102L;
    @Id
    @Column(name = "EXT_CPRODUCT_ID" )
    @GeneratedValue(generator = "jk-uuid")
    private String extCproductId;

    @Column(name = "FACTORY_NAME" )
    private String factoryName;

    @Column(name = "PRODUCT_NO" )
    private String productNo;

    @Column(name = "PRODUCT_IMAGE" )
    private String productImage;

    @Column(name = "PRODUCT_DESC" )
    private String productDesc;

    @Column(name = "PACKING_UNIT" )
    private String packingUnit;

    @Column(name = "CNUMBER" )
    private Long cnumber;

    @Column(name = "PRICE" )
    private Long price;

    @Column(name = "AMOUNT" )
    private Long amount;

    @Column(name = "PRODUCT_REQUEST" )
    private String productRequest;

    @Column(name = "ORDER_NO" )
    private Long orderNo;

    //   	附件和货物关联映射
//	关系：多对一，多个附件对应一个货物
//	关联方式：外键
    @ManyToOne
    @JoinColumn(name = "CONTRACT_PRODUCT_ID")
    @JsonIgnoreProperties({"extCproductSet"})
    private ContractProduct contractProduct;

    @ManyToOne
    @JoinColumn(name = "FACTORY_ID")
    private Factory factory;
}