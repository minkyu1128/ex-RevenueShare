package com.example.revenueshare.core.service;

import com.example.revenueshare.core.code.CodeMapperType;

public enum ValidateType implements CodeMapperType {

    C("등록")
    , U("수정");

    private final String code;     //코드
    private final String codeNm;   //코드명

    ValidateType(String codeNm) {
        this.code = this.name();
        this.codeNm = codeNm;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getCodeNm() {
        return this.codeNm;
    }
}