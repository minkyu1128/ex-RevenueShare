package com.example.revenueshare.biz.mng.cntrt.service;

import com.example.revenueshare.biz.mng.base.model.ChannelDTO;
import com.example.revenueshare.biz.mng.base.model.CmpnyDTO;
import com.example.revenueshare.biz.mng.base.service.ChannelMngService;
import com.example.revenueshare.biz.mng.base.service.CmpnyMngService;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
class ContractCmpnyMngServiceTest {

    @Autowired
    private ContractCmpnyMngService contractCmpnyMngService;
    @Autowired
    private CmpnyMngService cmpnyMngService;
    @Autowired
    private ChannelMngService channelMngService;





    private ContractCmpnyDTO CREATE_DTO;

    @Test
    @DisplayName("[성공 케이스]: 회사 계약정보 등록")
    @Order(1)
    void addSuccess() {
        //given
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder()
                .cmpnyNm("계약Cmpny_"+(new Date().getTime()/1000))
                .build();
        Long cmpnyId = (Long) cmpnyMngService.add(cmpnyDTO).getResultInfo();
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelNm("계약Cmpny Tube_"+(new Date().getTime()/1000))
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();
        Long channelId = (Long) channelMngService.add(channelDTO).getResultInfo();
        this.CREATE_DTO = ContractCmpnyDTO.builder()
                .cmpnyId(cmpnyId)
                .channelId(channelId)
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(77)
                .build();


        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = contractCmpnyMngService.add(this.CREATE_DTO);
            this.CREATE_DTO.setCntrCmpId(responseVO.getResultInfo());
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("회사 계약정보 등록 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 회사 계약정보 중복 등록")
    void addFailByDuplicate() {
        //given
        ContractCmpnyDTO contractCmpnyDTO = this.CREATE_DTO;

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = contractCmpnyMngService.add(contractCmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("중복 회사 계약정보 등록실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 회사 계약정보 등록 시 파라미터 유효성 검증")
    void addFailByValidate() {
        //given
        ContractCmpnyDTO contractCmpnyDTO = ContractCmpnyDTO.builder().build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = contractCmpnyMngService.add(contractCmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("회사 계약정보 등록 시 파라미터 유효성 검증실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 회사 계약정보 등록 시 채널과 계약된 모든 회사 Rs요율 합계 100% 초과")
    void addFailByRsRate() {
        //given
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder()
                .cmpnyNm("시온엔터_"+(new Date().getTime()/1000))
                .build();
        Long cmpnyId = (Long) cmpnyMngService.add(cmpnyDTO).getResultInfo();
        ContractCmpnyDTO contractCmpnyDTO = ContractCmpnyDTO.builder()
                .cmpnyId(cmpnyId)
                .channelId(this.CREATE_DTO.getChannelId())
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(99)
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = contractCmpnyMngService.add(contractCmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("회사 계약정보 등록 RS요율합 100%초과 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 회사 계약정보 수정")
    @Order(2)
    void modifySuccess() {
        //given
        ContractCmpnyDTO contractCmpnyDTO = ContractCmpnyDTO.builder()
                .cntrCmpId(CREATE_DTO.getCntrCmpId())
                .cmpnyId(CREATE_DTO.getCmpnyId())
                .channelId(CREATE_DTO.getChannelId())
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(44)
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            contractCmpnyMngService.modify(contractCmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("회사 계약정보 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 회사 계약정보 수정")
    void modifyFailByNoDate() {
        //given
        ContractCmpnyDTO contractCmpnyDTO = ContractCmpnyDTO.builder()
                .cntrCmpId(999999999999999999L)
                .cmpnyId(CREATE_DTO.getCmpnyId())
                .channelId(CREATE_DTO.getChannelId())
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(44)
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            contractCmpnyMngService.modify(contractCmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("회사 계약정보 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 회사 계약정보 수정 시 파라미터 유효성 검증")
    void modifyFailByValidate() {
        //given
        ContractCmpnyDTO contractCmpnyDTO = ContractCmpnyDTO.builder().build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            contractCmpnyMngService.modify(contractCmpnyDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("회사 계약정보 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }

}