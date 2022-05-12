package com.example.revenueshare.ctgy.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "ChannelSearchDTO",  description = "채널 SearchDTO")
public class ChannelSearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "채널명", example = " ", description = " ")
    private String schChannelNm;
}
