package com.example.revenueshare.core.aop;

import com.example.revenueshare.core.aop.code.AccessStatusCd;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "tb_access_log", schema = "", catalog = "")
@Schema(name = "AccessLog", description = "접근 로그")
public class AccessLog {


    /* ====================================
     * @GeneratedValue 어노테이션 전략 4가지
     *  -.AUTO(default): JPA 구현체가 데이터베이스에 따라서 IDENTITIY/SEQUENCE/TABLE 중 자동으로 생성 전략 결정.
     *  -.IDENTITY: 기본키 생성을 데이터베이스에 위임. ex) MySQL의 경우 AUTO INCREMENT를 사용하여 기본키 생성.(MySQL, PostgresSQL, Server, DB2)
     *  -.SEQUENCE: 데이터베이스의 특별한 오브젝트 시퀀스를 사용하여 기본키를 생성.(Oracle, PostgresSQL, Db2, H2)
     *  -.TABLE: 데이터베이스에 키 생성 전용 테이블을 하나 만들고 이를 사용하여 기본키를 생성.
     ==================================== */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long logId;            //로그ID


    private String sessionId;      //세션 ID

    private String ip;             //접근자 IP

    private String httpMethod;     //요청 Method

    private String url;            //요청 URL

    private String uri;            //요청 URI

    @Lob
    private String param;          //요청 parameter(json format String)
    @Lob
    private String response;       //응답

    @Enumerated(EnumType.STRING)
    private AccessStatusCd status;   //상태

    @Lob
    private String errorMsg;       //에러 메시지

    @CreationTimestamp
    private LocalDateTime startDt; //시작 일시

    @UpdateTimestamp
    private LocalDateTime endDt;   //종료 일지


    @Builder(builderClassName = "reqBuilder", builderMethodName = "reqBuilder")
    public AccessLog(String sessionId, String ip, String httpMethod, String url, String uri, String param) {
        this.sessionId = sessionId;
        this.ip = ip;
        this.httpMethod = httpMethod;
        this.url = url;
        this.uri = uri;
        this.param = param;
        this.status = AccessStatusCd.req;
    }

    public void setResponseCompleted(String response) {
        this.status = AccessStatusCd.cmplt;
        this.response = response;
    }

    public void setResponseFail(String errorMsg) {
        this.status = AccessStatusCd.fail;
        this.errorMsg = errorMsg;
    }

    public void setResponseNoAuth(String errorMsg) {
        this.status = AccessStatusCd.noAuth;
        this.errorMsg = errorMsg;
    }

}
