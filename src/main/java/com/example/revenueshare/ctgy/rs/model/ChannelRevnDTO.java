package com.example.revenueshare.ctgy.rs.model;

import com.example.revenueshare.ctgy.rs.code.RevnSeCd;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@Schema(name = "ChannelRevnDTO",  description = "채널수익 DTO")
public class ChannelRevnDTO {
    @Schema(required = false, title = "채널수익ID", example = "", description = " ")
    private Long chRevnId;

    @NotEmpty(message = "채널ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "채널ID", example = "", description = " ")
    private Long channelId;

    @NotEmpty(message = "수익일자(은)는 필수 입력값 입니다.")
    @Length(min = 8, max = 8, message = "수익일자의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "수익일자", example = "", description = " ")
    private String revnDe;

    @NotEmpty(message = "수익구분(은)는 필수 입력값 입니다.")
    @Length(max = 10, message = "수익구분의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "수익구분", example = "hits", description = "수익구분코드 (ad: 광고, hits: 조회수, sp:협찬, etc:기타)")
    private RevnSeCd revnSeCd;

    @NotEmpty(message = "수익금액(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "수익금액", example = "", description = " ")
    private Long revnAmt;
}
