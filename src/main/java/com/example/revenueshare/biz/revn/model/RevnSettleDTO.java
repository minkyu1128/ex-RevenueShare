package com.example.revenueshare.biz.revn.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "RevnSettleDTO",  description = "수익정산 DTO")
public class RevnSettleDTO {

    @NotNull
    @Schema(required = true, title = "채널ID", example = " ", description = " ")
    private Long channelId;

    @NotBlank(message = "정산년월(은)는 필수 입력값 입니다.")
    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])$", message = "정산년월의 입력패턴(yyyyMM)이 유효하지 않습니다.")
    @Schema(required = true, title = "정산년월", example = "202205", description = "년월(yyyyMM 포맷)")
    private String calYm;
}
