package com.example.revenueshare.ctgy.rs.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.ctgy.rs.domain.ids.ChannelRevenueMastCmpIds;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_channel_revenue_mast_cmp", schema = "", catalog = "")
@IdClass(ChannelRevenueMastCmpIds.class)
public class ChannelRevnMastCmp extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cntr_cmp_id")
    private ContractCmpny contractCmpny;

    @Id
    @Column(name = "cal_ym", length = 6)
    private String calYm;

    @Column(name = "cal_amt")
    private Long calAmt;

}
