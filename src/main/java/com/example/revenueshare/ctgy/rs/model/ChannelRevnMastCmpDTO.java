package com.example.revenueshare.ctgy.rs.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
@Schema(name = "ChannelRevnMastCmpDTO",  description = "회사 채널수익대장 DTO")
public class ChannelRevnMastCmpDTO {
    @NotEmpty(message = "회사계약정보ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "회사계약정보ID", example = "", description = " ")
    private Long cntrCmpId;

    @NotEmpty(message = "정산년월(은)는 필수 입력값 입니다.")
    @Length(min = 6, max = 6, message = "정산년월의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "정산년월", example = "202205", description = "년월(yyyyMM 포맷)")
    private String calYm;

    @NotEmpty(message = "정산금액(은)는 필수 입력값 입니다.")
    @Column(name = "cal_amt")
    @Schema(required = true, title = "정산금액", example = "123450000", description = "")
    private Long calAmt;
}
