package com.example.revenueshare.core.model;

import com.example.revenueshare.core.exception.ErrCd;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
public class ResponseVO<T> {

    private ErrCd errCd;

    private String errMsg;

    private T resultInfo;

    @Builder(builderClassName = "okBuilder", builderMethodName = "okBuilder")
    ResponseVO(T resultInfo){
        this.errCd = ErrCd.OK;
        this.errMsg = ErrCd.OK.getCodeNm();
        this.resultInfo = resultInfo;
    }
    @Builder(builderClassName = "errBuilder", builderMethodName = "errBuilder")
    ResponseVO(ErrCd errCd, String errMsg, T resultInfo){
        this.errCd = errCd;
        this.errMsg = errMsg;
        this.resultInfo = resultInfo;
    }
}
