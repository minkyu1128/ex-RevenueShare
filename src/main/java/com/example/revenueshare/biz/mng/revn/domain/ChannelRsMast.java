package com.example.revenueshare.biz.mng.revn.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRsMastIds;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_channel_rs_mast", schema = "", catalog = "")
@IdClass(ChannelRsMastIds.class)
public class ChannelRsMast extends BaseEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Id
    @Column(name = "cal_ym", length = 6)
    private String calYm;

    @Column(name = "tot_amt", nullable = false)
    private Long totAmt;

    @Column(name = "channel_amt")
    private Long channelAmt;

    @Column(name = "balance_amt")
    private Long balanceAmt;

}
