package com.example.revenueshare.core.exception;


import com.example.revenueshare.core.code.CodeMapperType;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드
 */
public enum ErrCd implements CodeMapperType {

    OK("정상", HttpStatus.OK)


    , ERR400("Client 요청 오류", HttpStatus.BAD_REQUEST)
    , ERR401("파라미터 유효성 검증 오류", HttpStatus.BAD_REQUEST)
//    , ERR402("잘못된 요청", HttpStatus.BAD_REQUEST)


    , ERR500("Server 오류", HttpStatus.INTERNAL_SERVER_ERROR)
    , ERR501("처리 불가", HttpStatus.INTERNAL_SERVER_ERROR)


    , ERR600("API 오류", HttpStatus.BAD_GATEWAY)


    , ERR999("기타 오류", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;     //코드
    private final String codeNm;   //코드명

    private final HttpStatus status;

    ErrCd(String codeNm, HttpStatus status) {
        this.code = this.name();
        this.codeNm = codeNm;
        this.status = status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getCodeNm() {
        return this.codeNm;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
