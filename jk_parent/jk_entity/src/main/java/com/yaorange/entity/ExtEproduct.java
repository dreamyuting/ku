package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EXT_EPRODUCT_C")
@GenericGenerator(name = "jpa-uuid",strategy = "uuid")
public class ExtEproduct {
    @Id
    @Column(name = "EXT_EPRODUCT_ID")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    @Column(name = "PRODUCT_NO")
    private String productNo;
    @Column(name = "PRODUCT_IMAGE")
    private String productImage;
    @Column(name = "PRODUCT_DESC")
    private String productDesc;
    @Column(name = "CNUMBER")
    private Long cnumber;
    @Column(name = "PACKING_UNIT")
    private String packingUnit;
    @Column(name = "PRICE")
    private Long price;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "PRODUCT_REQUEST")
    private String productRequest;
    @Column(name = "ORDER_NO")
    private Long orderNo;
    /*多对一*/
    @ManyToOne
    @JoinColumn(name = "FACTORY_ID")
    private Factory factory;

    @ManyToOne
    @JoinColumn(name = "EXPORT_PRODUCT_ID")
    @JsonIgnore
    private ExportProduct exportProduct;
}
