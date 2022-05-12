package com.example.revenueshare.ctgy.revn.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "ChannelRsMastDTO",  description = "채널 수익배분대장 DTO")
public class ChannelRsMastDTO {
    @NotEmpty(message = "채널ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "채널ID", example = " ", description = " ")
    private Long channelId;

    @NotEmpty(message = "정산년월(은)는 필수 입력값 입니다.")
    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])$", message = "정산년월의 입력패턴(yyyyMM)이 유효하지 않습니다.")
    @Schema(required = true, title = "정산년월", example = "202205", description = "년월(yyyyMM 포맷)")
    private String calYm;

    @NotEmpty(message = "총수익금(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "총 수익금", example = " ", description = "채널 수익 합계")
    private Long totAmt;

    @Schema(required = false, title = "채널 수익금", example = " ", description = "회사와 수익배분 후 잔액")
    private Long channelAmt;

    @Schema(required = false, title = "잔액", example = " ", description = "크리에이터와 수익배분 후 잔액")
    private Long balanceAmt;
}
