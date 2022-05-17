package com.example.revenueshare.biz.mng.chrevn.service;

import com.example.revenueshare.biz.mng.base.code.RevnSeCd;
import com.example.revenueshare.biz.mng.base.model.ChannelDTO;
import com.example.revenueshare.biz.mng.base.service.ChannelMngService;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnDTO;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.utils.RandomUtils;
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
class ChannelRevnMngServiceTest {

    @Autowired
    private ChannelRevnMngService channelRevnMngService;
    @Autowired
    private ChannelMngService channelMngService;

    private ChannelRevnDTO CREATE_DTO;

    @Test
    @DisplayName("[성공 케이스]: 채널수익 등록")
    @Order(1)
    void addSuccess() {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelNm("시온파파TV_"+(new Date().getTime()/1000))
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();
        Long channelId = (Long) channelMngService.add(channelDTO).getResultInfo();
        CREATE_DTO = ChannelRevnDTO.builder()
                .channelId(channelId)
                .revnDe(LocalDateTime.now().getYear()+RandomUtils.randomDay())
                .revnSeCd(RevnSeCd.HITS.getCode())
                .revnAmt(RandomUtils.randomMoney())
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = channelRevnMngService.add(CREATE_DTO);
            this.CREATE_DTO.setChRevnId(responseVO.getResultInfo());
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("채널수익 등록 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 채널수익 등록")
    void addFailByNotFoundChannel() {
        //given
        ChannelRevnDTO channelRevnDTO = ChannelRevnDTO.builder()
                .channelId(999999999999999999L)
                .revnDe(LocalDateTime.now().getYear()+RandomUtils.randomDay())
                .revnSeCd(RevnSeCd.HITS.getCode())
                .revnAmt(RandomUtils.randomMoney())
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = channelRevnMngService.add(channelRevnDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("등록되지 않은 채널수익 등록 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 채널수익 등록 시 파라미터 유효성 검증")
    void addFailByValidate() {
        //given
        ChannelRevnDTO channelRevnDTO = ChannelRevnDTO.builder().build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = channelRevnMngService.add(channelRevnDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널수익 등록 시 파라미터 유효성 검증실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 채널수익 수정")
    @Order(2)
    void modifySuccess() {
        //given
        ChannelRevnDTO channelRevnDTO = ChannelRevnDTO.builder()
                .chRevnId(this.CREATE_DTO.getChRevnId())
                .channelId(this.CREATE_DTO.getChannelId())
                .revnDe(LocalDateTime.now().getYear()+RandomUtils.randomDay())
                .revnSeCd(RevnSeCd.AD.getCode())
                .revnAmt(RandomUtils.randomMoney())
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelRevnMngService.modify(channelRevnDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("채널수익 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 채널수익 수정")
    void modifyFailByNoDate() {
        //given
        ChannelRevnDTO channelRevnDTO = ChannelRevnDTO.builder()
                .chRevnId(999999999999999999L)
                .channelId(this.CREATE_DTO.getChannelId())
                .revnDe(LocalDateTime.now().getYear()+RandomUtils.randomDay())
                .revnSeCd(RevnSeCd.AD.getCode())
                .revnAmt(RandomUtils.randomMoney())
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelRevnMngService.modify(channelRevnDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널수익 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 채널수익 수정 시 파라미터 유효성 검증")
    void modifyFailByValidate() {
        //given
        ChannelRevnDTO channelRevnDTO = ChannelRevnDTO.builder().build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelRevnMngService.modify(channelRevnDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널수익 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 채널수익 삭제")
    @Order(3)
    void removeSuccess() {
        //given
        Long chRevnId = this.CREATE_DTO.getChRevnId();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelRevnMngService.removeById(chRevnId);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("채널수익 삭제 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }

    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 채널수익 삭제")
    void removeNodata() {
        //given
        Long channelId = 999999999999999999L;

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelRevnMngService.removeById(channelId);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널수익 삭제 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }
}