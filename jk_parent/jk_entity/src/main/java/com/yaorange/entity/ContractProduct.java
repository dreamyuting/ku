package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table( name ="contract_product_c" )
@GenericGenerator(name = "jk-uuid",strategy = "uuid")
public class ContractProduct extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4366886490987474672L;
    @Id
    @Column(name = "CONTRACT_PRODUCT_ID")
    @GeneratedValue(generator = "jk-uuid")
    private String contractProductId;

    @Column(name = "FACTORY_NAME")
    private String factoryName;

    @Column(name = "PRODUCT_NO")
    private String productNo;

    @Column(name = "PRODUCT_IMAGE")
    private String productImage;

    @Column(name = "PRODUCT_DESC")
    private String productDesc;

    @Column(name = "LOADING_RATE")
    private String loadingRate;

    @Column(name = "BOX_NUM")
    private Long boxNum;

    @Column(name = "PACKING_UNIT")
    private String packingUnit;

    @Column(name = "CNUMBER")
    private Long cnumber;

    @Column(name = "OUT_NUMBER")
    private Long outNumber;

    @Column(name = "FINISHED")
    private Long finished;

    @Column(name = "PRODUCT_REQUEST")
    private String productRequest;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "ORDER_NO")
    private Long orderNo;
    @ManyToOne
    @JoinColumn(name = "CONTRACT_ID")
    @JsonIgnoreProperties(value = {"contractProductSet"})
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "FACTORY_ID")
    private Factory factory;
    @OneToMany(mappedBy = "contractProduct",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"contractProduct"})
    private Set<ExtCproduct> extCproductSet = new HashSet<ExtCproduct>();

}
