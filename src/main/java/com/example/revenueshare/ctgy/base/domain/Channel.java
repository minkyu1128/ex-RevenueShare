package com.example.revenueshare.ctgy.base.domain;

import com.example.revenueshare.core.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
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

    @Column(name = "close_de", nullable = true, length = 8)
    private String closeDe;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;
}
