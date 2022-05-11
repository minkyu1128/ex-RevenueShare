package com.example.revenueshare.ctgy.rs.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.ctgy.rs.code.RevnSeCd;
import com.example.revenueshare.ctgy.rs.domain.ids.ChannelRsMastIds;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
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

    @Column(name = "revn_de", nullable = false, length = 8)
    private String revnDe;

    @Enumerated(EnumType.STRING)
    @Column(name = "revn_se_cd", nullable = false, length = 10)
    private RevnSeCd revnSeCd;

    @Column(name = "revn_amt", nullable = false)
    private Long revnAmt;

}
