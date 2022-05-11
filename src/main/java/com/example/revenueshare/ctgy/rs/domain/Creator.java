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
@Table(name = "tb_creator", schema = "", catalog = "")
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "creator_nm", nullable = false, length = 30)
    private String creatorNm;

    @Column(name = "age", nullable = true)
    private Integer age;

    @Column(name = "sex", nullable = true, length = 1)
    private String sex;

    @CreationTimestamp
    @Column(name = "regist_dt")
    private LocalDateTime registDt;

    @UpdateTimestamp
    @Column(name = "last_updt_dt")
    private LocalDateTime lastUpdtDt;
}
