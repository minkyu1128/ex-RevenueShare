package com.example.revenueshare.ctgy.rs.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_contract_cmpny", schema = "", catalog = "")
public class ContractCmpny extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cntr_cmp_id", nullable = false)
    private Long cntrCmpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cmpny_id", nullable = false)
    private Cmpny cmpny;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(name = "cntr_de", nullable = false, length = 8)
    private String cntrDe;

    @Column(name = "rs_rate", nullable = false)
    private Integer rsRate;

}
