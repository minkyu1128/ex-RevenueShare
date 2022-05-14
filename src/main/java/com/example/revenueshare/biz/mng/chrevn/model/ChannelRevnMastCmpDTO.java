package com.example.revenueshare.biz.mng.chrevn.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "ChannelRevnMastCmpDTO",  description = "회사 채널수익대장 DTO")
public class ChannelRevnMastCmpDTO {
    @NotNull(message = "회사계약정보ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "회사계약정보ID", example = " ", description = " ")
    private Long cntrCmpId;

    @NotBlank(message = "정산년월(은)는 필수 입력값 입니다.")
    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])$", message = "정산년월의 입력패턴(yyyyMM)이 유효하지 않습니다.")
    @Schema(required = true, title = "정산년월", example = "202205", description = "년월(yyyyMM 포맷)")
    private String calYm;

    @NotNull(message = "정산금액(은)는 필수 입력값 입니다.")
    @Column(name = "cal_amt")
    @Schema(required = true, title = "정산금액", example = "123450000", description = "")
    private Long calAmt;
}
