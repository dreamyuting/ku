package com.yaorange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dept_p")
@GenericGenerator(name = "my_uuid",strategy = "uuid")
public class Dept extends BaseEntity {
    @Id
    @Column(name = "DEPT_ID")
    @GeneratedValue(generator = "my_uuid")
    private String deptId;
    @Column(name = "DEPT_NAME")
    private String deptName;
    @Column(name = "STATE")
    private Long state;
    @Column(name = "PARENT_ID")
    private String parentId;


}
