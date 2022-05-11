package com.example.revenueshare.ctgy.rs.model;

import lombok.Builder;
import lombok.Data;

/**
 * 채널 관리 DTO
 */
@Builder
@Data
public class ChannelDTO {

    /* =========================================
    * Entity Field...
    ========================================= */
    private Long channelId;

    private String channelNm;

    private String openDe;

    private String closeDe;


    /* =========================================
    * Search Field...
    ========================================= */
    private String schChannelNm;
}
