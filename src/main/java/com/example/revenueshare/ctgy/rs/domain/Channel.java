package com.example.revenueshare.ctgy.rs.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_channel", schema = "", catalog = "")
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "channel_id", nullable = false)
    private Long channelId;

    @Column(name = "channel_nm", nullable = false, length = 30)
    private String channelNm;

    @Column(name = "open_de", nullable = false, length = 8)
    private String openDe;

//    @CreationTimestamp
//    @Column(name = "regist_dt")
//    private LocalDateTime registDt;
//
//    @UpdateTimestamp
//    @Column(name = "last_updt_dt")
//    private LocalDateTime lastUpdtDt;
}
