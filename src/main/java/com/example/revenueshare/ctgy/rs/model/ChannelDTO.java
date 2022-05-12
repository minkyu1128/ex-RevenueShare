package com.example.revenueshare.ctgy.rs.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@Schema(name = "ChannelDTO",  description = "채널 DTO")
public class ChannelDTO {

    /* =========================================
    * Entity Field...
    ========================================= */
    @Schema(required = false, title = "채널ID", example = "", description = " ")
    private Long channelId;

    @NotEmpty(message = "채널명(은)는 필수 입력값 입니다.")
    @Length(max = 30, message = "채널명의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "채널명", example = "", description = " ")
    private String channelNm;

    @NotEmpty(message = "채널 개설일자(은)는 필수 입력값 입니다.")
    @Length(min = 8, max = 8, message = "채널 개설일자의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "채널 개설일자", example = "", description = " ")
    private String openDe;

    @Length(min = 8, max = 8, message = "채널 폐쇄일자의 길이가 유효하지 않습니다.")
    @Schema(required = false, title = "채널 패쇄일자", example = "", description = " ")
    private String closeDe;

}
