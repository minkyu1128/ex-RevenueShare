package com.example.revenueshare.ctgy.base.service;

import com.example.revenueshare.biz.mng.base.model.CreatorDTO;
import com.example.revenueshare.biz.mng.base.service.CreatorMngService;
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
class CreatorMngServiceTest {

    @Autowired
    private CreatorMngService creatorMngService;

    private String CREATOR_NM;
    private Long CREATOR_ID;

    @Test
    @DisplayName("[성공 케이스]: 크리에이터 등록")
    @Order(1)
    void addSuccess() {
        //given
        this.CREATOR_NM = "시온파파_"+(new Date().getTime()/1000);
        CreatorDTO creatorDTO = CreatorDTO.builder()
                .creatorNm(this.CREATOR_NM)
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = creatorMngService.add(creatorDTO);
            this.CREATOR_ID = responseVO.getResultInfo();
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("크리에이터 등록 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 크리에이터 중복 등록")
    void addFailByDuplicate() {
        //given
        CreatorDTO creatorDTO = CreatorDTO.builder()
                .creatorNm(this.CREATOR_NM)
                .age(99)
                .sex("1")
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = creatorMngService.add(creatorDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("중복 크리에이터 등록실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 크리에이터 등록 시 유효성 검증")
    void addFailByValidate() {
        //given
        CreatorDTO creatorDTO = CreatorDTO.builder().build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = creatorMngService.add(creatorDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("크리에이터 등록 유효성 검증실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 크리에이터 수정")
    @Order(2)
    void modifySuccess() {
        //given
        this.CREATOR_NM = this.CREATOR_NM.replace("시온파파", "시온파파Tube");
        CreatorDTO creatorDTO = CreatorDTO.builder()
                .creatorId(this.CREATOR_ID)
                .creatorNm(this.CREATOR_NM)
                .age(37)
                .sex("1")
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            creatorMngService.modify(creatorDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("크리에이터 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 크리에이터 수정")
    void modifyFailByNoDate() {
        //given
        CreatorDTO creatorDTO = CreatorDTO.builder()
                .creatorId(999999999999999999L)
                .creatorNm(this.CREATOR_NM)
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            creatorMngService.modify(creatorDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("크리에이터 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 크리에이터 수정 시 유효성 검증")
    void modifyFailByValidate() {
        //given
        CreatorDTO creatorDTO = CreatorDTO.builder().build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            creatorMngService.modify(creatorDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("크리에이터 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }

}