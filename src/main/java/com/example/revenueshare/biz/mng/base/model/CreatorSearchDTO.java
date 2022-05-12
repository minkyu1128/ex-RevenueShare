
package com.example.revenueshare.biz.mng.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "CreatorSearchDTO",  description = "크리에이터 SearchDTO")
public class CreatorSearchDTO {

    /* =========================================
    * Search Field...
    ========================================= */
    @Schema(required = false, title = "크리에이터명", example = " ", description = " ")
    private String schCreatorNm;
}
