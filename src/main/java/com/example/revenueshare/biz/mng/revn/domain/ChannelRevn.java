package com.example.revenueshare.biz.mng.revn.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.biz.mng.base.code.RevnSeCd;
import com.example.revenueshare.biz.mng.base.domain.Channel;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_channel_revenue", schema = "", catalog = "")
public class ChannelRevn extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ch_revn_id", nullable = false)
    private Long chRevnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column(name = "revn_de", nullable = false, length = 8)
    private String revnDe;

    @Enumerated(EnumType.STRING)
    @Column(name = "revn_se_cd", nullable = false, length = 10)
    private RevnSeCd revnSeCd;

    @Column(name = "revn_amt", nullable = false)
    private Long revnAmt;

    @Column(name = "rs_yn", nullable = false, length = 1)
    private String rsYn;

    @Column(name = "del_yn", nullable = false, length = 1)
    private String delYn;

    @Column(name = "del_dt", nullable = true)
    private LocalDateTime delDt;

}
