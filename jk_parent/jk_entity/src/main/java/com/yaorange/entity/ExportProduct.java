package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EXPORT_PRODUCT_C")
@GenericGenerator(name = "jpa-uuid",strategy = "uuid")
public class ExportProduct {
    @Id
    @Column(name = "EXPORT_PRODUCT_ID")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    @Column(name = "PRODUCT_NO")
    private String productNo;
    @Column(name = "PACKING_UNIT")
    private String packingUnit;
    @Column(name = "CNUMBER")
    private Long cnumber;
    @Column(name = "BOX_NUM")
    private Long boxNum;
    @Column(name = "GROSS_WEIGHT")
    private Long grossWeight;
    @Column(name = "NET_WEIGHT")
    private Long netWeight;
    @Column(name = "SIZE_LENGTH")
    private Long sizeLength;
    @Column(name = "SIZE_WIDTH")
    private Long sizeWidth;
    @Column(name = "SIZE_HEIGHT")
    private Long sizeHeight;
    @Column(name = "EX_PRICE")
    private Long exPrice;
    @Column(name = "PRICE")
    private Long price;
    @Column(name = "TAX")
    private Long tax;
    @Column(name = "ORDER_NO")
    private Long orderNo;
    /*多对一*/
    @ManyToOne
    @JoinColumn(name = "FACTORY_ID")
    private Factory factory;
    /*多对一*/
    @ManyToOne
    @JoinColumn(name = "EXPORT_ID")
    @JsonIgnoreProperties({"exportProductSet"})
    private Export export;

    /*一个报运商品下有多个报运商品附件*/
    @OneToMany(mappedBy = "exportProduct",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"exportProduct"})
    private Set<ExtEproduct> extEproductSet = new HashSet<ExtEproduct>(0);

}
