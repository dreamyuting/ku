package com.yaorange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "EXPORT_C")
@GenericGenerator(name = "jpa-uuid",strategy = "uuid")
public class Export extends BaseEntity{
    @Id
    @Column(name = "EXPORT_ID")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    @Column(name = "INPUT_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inputDate;
    @Column(name = "CONTRACT_IDS")
    private String contractIds;

    @Column(name = "CUSTOMER_CONTRACT")
    private String customerContract;
    @Column(name = "LCNO")
    private String lcno;
    @Column(name = "CONSIGNEE")
    private String consignee;
    @Column(name = "MARKS")
    private String marks;
    @Column(name = "SHIPMENT_PORT")
    private String shipmentPort;
    @Column(name = "DESTINATION_PORT")
    private String destinationPort;
    @Column(name = "TRANSPORT_MODE")
    private String transportMode;
    @Column(name = "PRICE_CONDITION")
    private String priceCondition;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "BOX_NUMS")
    private Long boxNums;
    @Column(name = "GROSS_WEIGHTS")
    private Long grossWeights;
    @Column(name = "MEASUREMENTS")
    private Long measurements;
    @Column(name = "STATE")
    private Long state;

    @OneToMany(mappedBy="export",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ExportProduct> exportProductSet = new HashSet<ExportProduct>(0);
    @Transient
    private String  nums;

    public String getNums() {
        Integer  epNums = getExportProductSet().size();
        Integer  eeNums = 0;
        for(ExportProduct ep :getExportProductSet())
        {
            eeNums+=ep.getExtEproductSet().size();
        }
        nums = epNums+"/"+eeNums;
        return nums;
    }
}
