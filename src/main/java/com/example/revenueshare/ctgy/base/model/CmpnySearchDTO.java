package com.example.revenueshare.ctgy.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "CmpnySearchDTO",  description = "회사 SearchDTO")
public class CmpnySearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "회사명", example = " ", description = " ")
    private String schCmpnyNm;
}
