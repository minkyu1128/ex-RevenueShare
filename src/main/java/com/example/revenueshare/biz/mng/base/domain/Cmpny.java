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
@Table(name = "tb_cmpny", schema = "", catalog = "")
public class Cmpny extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cmpny_id", nullable = false)
    private Long cmpnyId;

    @Column(name = "cmpny_nm", nullable = false, length = 30)
    private String cmpnyNm;
}
