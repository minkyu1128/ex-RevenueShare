package com.example.revenueshare.biz.mng.chrevn.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "ChannelRevnDTO",  description = "채널수익 DTO")
public class ChannelRevnDTO {
    @Schema(required = false, title = "채널수익ID", example = " ", description = " ")
    private Long chRevnId;

    @NotNull(message = "채널ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "채널ID", example = " ", description = " ")
    private Long channelId;

    @NotBlank(message = "수익일자(은)는 필수 입력값 입니다.")
    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$", message = "수익일자의 입력패턴(yyyyMMdd)이 유효하지 않습니다.")
    @Schema(required = true, title = "수익일자", example = "20220513", description = " ")
    private String revnDe;

    @NotBlank(message = "수익구분(은)는 필수 입력값 입니다.")
    @Length(max = 10, message = "수익구분의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "수익구분", example = "HITS", description = "수익구분코드 (AD: 광고, HITS: 조회수, SP:협찬, ETC:기타)")
    private String revnSeCd;

    @NotNull(message = "수익금액(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "수익금액", example = " ", description = " ")
    private Long revnAmt;
}
