package com.example.revenueshare.biz.revnsett.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "RevnSettleFndSearchDTO",  description = "수익정산조회 SearchDTO")
public class RevnFndSearchDTO {


    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])$", message = "정산년월의 입력패턴(yyyyMM)이 유효하지 않습니다.")
    @Schema(required = true, title = "정산년월 시작", example = "202205", description = "년월(yyyyMM 포맷)")
    private String searchCalYmFrom;
    @Pattern(regexp = "^\\d{4}(0[1-9]|1[012])$", message = "정산년월의 입력패턴(yyyyMM)이 유효하지 않습니다.")
    @Schema(required = true, title = "정산년월 종료", example = "202205", description = "년월(yyyyMM 포맷)")
    private String searchCalYmTo;
}
