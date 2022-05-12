package com.example.revenueshare.ctgy.rs.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Builder
@Data
@Schema(name = "CreatorDTO",  description = "크리에이터 DTO")
public class CreatorDTO {
    @Schema(required = false, title = "크리에이터ID", example = "", description = " ")
    private Long creatorId;

    @NotEmpty(message = "채널ID(은)는 필수 입력값 입니다.")
    @Length(max = 30, message = "채널ID의 길이가 유효하지 않습니다.")
    @Schema(required = true, title = "채널ID", example = "", description = " ")
    private String creatorNm;

    @Pattern(regexp = "[^0-9]", message = "나이(은)는 숫자만 입력 가능 합니다.")
    @Schema(required = false, title = "나이", example = "", description = " ")
    private Integer age;

    @Column(name = "sex", nullable = true, length = 1)
    @Pattern(regexp = "[^1-4]", message = "성별(은)는 숫자(1~4)만 입력 가능 합니다.")
    @Schema(required = false, title = "성별", example = "", description = "1:남(~1999), 2:여(~1999), 3:남(2000~), 4:여(2000~)")
    private String sex;
}
