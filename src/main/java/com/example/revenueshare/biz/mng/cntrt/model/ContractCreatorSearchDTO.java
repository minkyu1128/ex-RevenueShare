package com.example.revenueshare.biz.mng.cntrt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "ContractCmpSearchDTO",  description = "회사 계약정보 SearchDTO")
public class ContractCreatorSearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "크리에이터명", example = " ", description = " ")
    private String schCreatorNm;
    @Schema(required = false, title = "채널명", example = " ", description = " ")
    private String schChannelNm;



}
