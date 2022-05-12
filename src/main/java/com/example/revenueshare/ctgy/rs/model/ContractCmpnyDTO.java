package com.example.revenueshare.ctgy.rs.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@Schema(name = "ContractCmpDTO",  description = "회사 계약정보 DTO")
public class ContractCmpnyDTO {

    @Schema(required = false, title = "회사계약정보ID", example = "", description = " ")
    private Long cntrCmpId;

    @NotEmpty(message = "회사ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "회사ID", example = "", description = " ")
    private Long cmpnyId;

    @NotEmpty(message = "채널ID(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "채널ID", example = "", description = " ")
    private Long channelId;

    @NotEmpty(message = "계약일자(은)는 필수 입력값 입니다.")
    @Length(min = 8, max = 8, message = "계약일자의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "계약일자", example = "", description = " ")
    private String cntrDe;

    @NotEmpty(message = "분배수익비율(은)는 필수 입력값 입니다.")
    @Schema(required = true, title = "분배수익비율", example = "", description = "채널과 회사간 분배수익 비율(%)")
    private Integer rsRate;
}
