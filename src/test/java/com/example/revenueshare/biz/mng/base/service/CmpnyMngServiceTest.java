package com.example.revenueshare.biz.mng.base.service;

import com.example.revenueshare.biz.mng.base.model.CmpnyDTO;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
class CmpnyMngServiceTest {

    @Autowired
    private CmpnyMngService cmpnyMngService;

    private String CMPNY_NM;
    private Long CMPNY_ID;

    @Test
    @DisplayName("[성공 케이스]: 회사 등록")
    @Order(1)
    void addSuccess() {
        //given
        this.CMPNY_NM = "시온파파 컴퍼니_"+(new Date().getTime()/1000);
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder()
                .cmpnyNm(this.CMPNY_NM)
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = cmpnyMngService.add(cmpnyDTO);
            this.CMPNY_ID = responseVO.getResultInfo();
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("회사 등록 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 회사 중복 등록")
    void addFailByDuplicate() {
        //given
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder()
                .cmpnyNm(this.CMPNY_NM)
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = cmpnyMngService.add(cmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("중복 회사 등록실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 회사 등록 시 파라미터 유효성 검증")
    void addFailByValidate() {
        //given
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder().build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = cmpnyMngService.add(cmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("회사 등록 시 파라미터 유효성 검증실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 회사 수정")
    @Order(2)
    void modifySuccess() {
        //given
        this.CMPNY_NM = this.CMPNY_NM.replace("시온파파 컴퍼니", "시온파파 Enter");
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder()
                .cmpnyId(this.CMPNY_ID)
                .cmpnyNm(this.CMPNY_NM)
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            cmpnyMngService.modify(cmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("회사 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 회사 수정")
    void modifyFailByNoDate() {
        //given
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder()
                .cmpnyId(999999999999999999L)
                .cmpnyNm(this.CMPNY_NM)
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            cmpnyMngService.modify(cmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("회사 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 회사 수정 시 파라미터 유효성 검증")
    void modifyFailByValidate() {
        //given
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder().build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            cmpnyMngService.modify(cmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("회사 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }

}