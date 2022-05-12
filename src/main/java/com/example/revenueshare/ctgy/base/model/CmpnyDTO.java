package com.example.revenueshare.ctgy.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@Schema(name = "CmpnyDTO",  description = "회사 DTO")
public class CmpnyDTO {

    @Schema(required = false, title = "회사ID", example = " ", description = " ")
    private Long cmpnyId;

    @NotEmpty(message = "회사명(은)는 필수 입력값 입니다.")
    @Length(max = 30, message = "회사명의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "회사명", example = " ", description = " ")
    private String cmpnyNm;
}
