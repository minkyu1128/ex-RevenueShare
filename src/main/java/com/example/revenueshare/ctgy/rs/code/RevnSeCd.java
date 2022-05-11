package com.example.revenueshare.ctgy.rs.code;


import com.example.revenueshare.core.code.CodeMapperType;

/**
 * 수익구분 코드
 */
public enum RevnSeCd implements CodeMapperType {

    AD("광고")
    ,HITS("조회수")
    ,SP("협찬")
    ,ETC("기타")
    ;

    private final String code;     //코드
    private final String codeNm;   //코드명
    RevnSeCd(String codeNm) {
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
