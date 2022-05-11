package com.example.revenueshare.ctgy.rs.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.ctgy.rs.code.RevnSeCd;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_channel_revenue", schema = "", catalog = "")
public class ChannelRevenue extends BaseEntity {
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

}
