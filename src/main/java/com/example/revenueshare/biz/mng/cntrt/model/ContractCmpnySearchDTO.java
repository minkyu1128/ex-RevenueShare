package com.example.revenueshare.biz.mng.cntrt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "ContractCmpSearchDTO",  description = "회사 계약정보 SearchDTO")
public class ContractCmpnySearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "회사명", example = " ", description = " ")
    private String schCmpnyNm;
    @Schema(required = false, title = "채널명", example = " ", description = " ")
    private String schChannelNm;



}
