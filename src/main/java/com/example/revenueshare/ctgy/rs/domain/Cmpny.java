package com.example.revenueshare.ctgy.rs.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_cmpny", schema = "", catalog = "")
public class Cmpny {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cmpny_id", nullable = false)
    private Long cmpnyId;

    @Column(name = "cmpny_nm", nullable = false, length = 30)
    private String cmpnyNm;

    @CreationTimestamp
    @Column(name = "regist_dt")
    private LocalDateTime registDt;

    @UpdateTimestamp
    @Column(name = "last_updt_dt")
    private LocalDateTime lastUpdtDt;
}
