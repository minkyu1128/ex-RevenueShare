package com.example.revenueshare.biz.mng.chrevn.domain;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRevenueMastCrtIds;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_channel_revenue_mast_crt", schema = "", catalog = "")
@IdClass(ChannelRevenueMastCrtIds.class)
public class ChannelRevnMastCrt extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cntr_crt_id")
    private ContractCreator contractCreator;

    @Id
    @Column(name = "cal_ym")
    private String calYm;

    @Column(name = "cal_amt")
    private Long calAmt;

}
