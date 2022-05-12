package com.example.revenueshare.ctgy.rs.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@Schema(name = "ChannelRsMastDTO",  description = "채널 수익배분대장 DTO")
public class ChannelRsMastDTO {
    @NotEmpty(message = "채널ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "채널ID", example = "", description = " ")
    private Long channelId;

    @NotEmpty(message = "정산년월(은)는 필수 입력값 입니다.")
    @Length(min = 6, max = 6, message = "정산년월의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "정산년월", example = "202205", description = "년월(yyyyMM 포맷)")
    private String calYm;

    @NotEmpty(message = "총수익금(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "총 수익금", example = "", description = "채널 수익 합계")
    private Long totAmt;

    @Schema(required = false, title = "채널 수익금", example = "", description = "회사와 수익배분 후 잔액")
    private Long channelAmt;

    @Schema(required = false, title = "잔액", example = "", description = "크리에이터와 수익배분 후 잔액")
    private Long balanceAmt;
}
