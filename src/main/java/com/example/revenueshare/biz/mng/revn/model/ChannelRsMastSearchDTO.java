package com.example.revenueshare.biz.mng.revn.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "ChannelRsMastSearchDTO",  description = "채널 수익배분대장 SearchDTO")
public class ChannelRsMastSearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "채널명", example = " ", description = " ")
    private String schChannelNm;
    @Schema(required = false, title = "정산년월", example = " ", description = " ")
    private String schCalYm;
}
