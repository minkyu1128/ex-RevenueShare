package com.example.revenueshare.biz.mng.cntrt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "ContractCrtDTO",  description = "크리에이터 계약정보 DTO")
public class ContractCreatorDTO {

    @Schema(required = false, title = "크리에이터계약정보ID", example = " ", description = " ")
    private Long cntrCrtId;

    @NotNull(message = "크리에이터ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "크리에이터ID", example = " ", description = " ")
    private Long creatorId;

    @NotNull(message = "채널ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "채널ID", example = " ", description = " ")
    private Long channelId;

    @NotBlank(message = "계약일자(은)는 필수 입력값 입니다.")
    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$", message = "계약일자의 입력패턴(yyyyMMdd)이 유효하지 않습니다.")
    @Schema(required = true, title = "계약일자", example = "20220513", description = " ")
    private String cntrDe;

    @NotNull(message = "분배수익비율(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "분배수익비율", example = " ", description = "채널과 회사간 분배수익 비율(%)")
    private Integer rsRate;
}
