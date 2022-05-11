package com.example.revenueshare.core.code;


/**
 * 수익구분 코드
 */
public enum ErrCd implements CodeMapperType {

    OK("정상")


    , ERR400("Client 요청 오류")


    , ERR500("Server 오류")


    , ERR600("API 오류")


    , ERR999("기타 오류");

    private final String code;     //코드
    private final String codeNm;   //코드명

    ErrCd(String codeNm) {
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
