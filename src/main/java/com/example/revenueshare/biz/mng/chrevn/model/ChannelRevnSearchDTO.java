package com.example.revenueshare.biz.mng.chrevn.model;

import com.example.revenueshare.biz.mng.base.code.RevnSeCd;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "ChannelRevnSearchDTO",  description = "채널수익 SearchDTO")
public class ChannelRevnSearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "채널명", example = " ", description = " ")
    private String schChannelNm;

    @Schema(required = false, title = "수익구분", example = " ", description = " ")
    private RevnSeCd schRevnSeCd;



}
