package com.example.revenueshare.ctgy.cntrt.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.ctgy.base.domain.Channel;
import com.example.revenueshare.ctgy.base.domain.Creator;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_contract_creator", schema = "", catalog = "")
public class ContractCreator extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cntr_crt_id", nullable = false)
    private Long cntrCrtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(name = "cntr_de", nullable = false, length = 8)
    private String cntrDe;

    @Column(name = "rs_rate", nullable = false)
    private Integer rsRate;

}
