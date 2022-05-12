package com.example.revenueshare.biz.mng.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "ChannelDTO",  description = "채널 DTO")
public class ChannelDTO {

    /* =========================================
    * Entity Field...
    ========================================= */
    @Schema(required = false, title = "채널ID", example = " ", description = " ")
    private Long channelId;

    @NotEmpty(message = "채널명(은)는 필수 입력값 입니다.")
    @Length(max = 30, message = "채널명의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "채널명", example = "홍길동TV", description = " ")
    private String channelNm;

    @NotEmpty(message = "채널 개설일자(은)는 필수 입력값 입니다.")
    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$", message = "채널 개설일자의 입력패턴(yyyyMMdd)이 유효하지 않습니다.")
    @Schema(required = true, title = "채널 개설일자", example = "20220512", description = " ")
    private String openDe;

    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$", message = "채널 폐쇄일자의 입력패턴(yyyyMMdd)이 유효하지 않습니다.")
    @Schema(required = false, title = "채널 패쇄일자", example = " ", description = " ")
    private String closeDe;

}
