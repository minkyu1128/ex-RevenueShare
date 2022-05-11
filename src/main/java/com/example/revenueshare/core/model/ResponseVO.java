package com.example.revenueshare.core.model;

import com.example.revenueshare.core.code.ErrCd;
import lombok.Builder;


public class ResponseVO<T> {

    private ErrCd errCd;

    private String errMsg;

    private T resultInfo;

    @Builder(builderClassName = "okBuilder", buildMethodName = "okBuilder")
    ResponseVO(T resultInfo){
        this.errCd = ErrCd.OK;
        this.errMsg = ErrCd.OK.getCodeNm();
        this.resultInfo = resultInfo;
    }
    @Builder(builderClassName = "errBuilder", buildMethodName = "errBuilder")
    ResponseVO(ErrCd errCd, String errMsg, T resultInfo){
        this.errCd = errCd;
        this.errMsg = errMsg;
        this.resultInfo = resultInfo;
    }
}
