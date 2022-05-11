package com.example.revenueshare.ctgy.rs.domain;

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
@Table(name = "tb_contract_creator", schema = "", catalog = "")
public class ContractCreator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cntr_cmp_id", nullable = false)
    private Long cntrCmpId;

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

    @CreationTimestamp
    @Column(name = "regist_dt")
    private LocalDateTime registDt;

}
