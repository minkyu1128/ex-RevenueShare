package com.example.revenueshare.ctgy.rs.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import com.example.revenueshare.ctgy.rs.domain.ids.ChannelRevenueMastCrtIds;
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
@Table(name = "tb_channel_revenue_mast_crt", schema = "", catalog = "")
@IdClass(ChannelRevenueMastCrtIds.class)
public class ChannelRevenueMastCrt extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cntr_cmp_id")
    private ContractCreator contractCreator;

    @Id
    @Column(name = "cal_ym")
    private String calYm;

    @Column(name = "cal_amt")
    private Long calAmt;

}
