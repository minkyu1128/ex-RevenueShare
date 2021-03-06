package com.example.revenueshare.biz.mng.base.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_creator", schema = "", catalog = "")
public class Creator extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "creator_nm", nullable = false, length = 30)
    private String creatorNm;

    @Column(name = "age", nullable = true)
    private Integer age;

    @Column(name = "sex", nullable = true, length = 1)
    private String sex;
}
