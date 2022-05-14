package com.example.revenueshare.biz.mng.chrevn.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "ChannelRevnMastCrtSearchDTO",  description = "크리에이터 채널수익대장 SearchDTO")
public class ChannelRevnMastCrtSearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "채널명", example = " ", description = " ")
    private String schChannelNm;

    @Schema(required = false, title = "정산년월", example = " ", description = " ")
    private String schCalYm;



}
