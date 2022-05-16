package com.example.revenueshare.ctgy.base.service;

import com.example.revenueshare.biz.mng.base.model.ChannelDTO;
import com.example.revenueshare.biz.mng.base.service.ChannelMngService;
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
class ChannelMngServiceTest {

    @Autowired
    private ChannelMngService channelMngService;

    private String CHANNEL_NM;
    private Long CHANNEL_ID;

    @Test
    @DisplayName("[성공 케이스]: 채널 등록")
    @Order(1)
    void addSuccess() {
        //given
        this.CHANNEL_NM = "시온파파TV_"+(new Date().getTime()/1000);
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelNm(this.CHANNEL_NM)
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = channelMngService.add(channelDTO);
            this.CHANNEL_ID = responseVO.getResultInfo();
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("채널 등록 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 채널 중복 등록")
    void addFailByDuplicate() {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelNm(this.CHANNEL_NM)
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = channelMngService.add(channelDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("중복 채널 등록실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 채널 등록 시 유효성 검증")
    void addFailByValidate() {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder().build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = channelMngService.add(channelDTO);
        } catch (RsException e){
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널 등록 유효성 검증실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 채널 수정")
    @Order(2)
    void modifySuccess() {
        //given
        this.CHANNEL_NM = this.CHANNEL_NM.replace("시온파파TV", "시온파파Tube");
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelId(this.CHANNEL_ID)
                .channelNm(this.CHANNEL_NM)
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelMngService.modify(channelDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("채널 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }
    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 채널 수정")
    void modifyFailByNoDate() {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelId(999999999999999999L)
                .channelNm(this.CHANNEL_NM)
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelMngService.modify(channelDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }
    @Test
    @DisplayName("[실패 케이스]: 채널 수정 시 유효성 검증")
    void modifyFailByValidate() {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder().build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelMngService.modify(channelDTO);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 채널 삭제")
    @Order(3)
    void removeSuccess() {
        //given
        Long channelId = this.CHANNEL_ID;

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelMngService.removeById(channelId);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("채널 삭제 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }

    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 채널 삭제")
    void removeNodata() {
        //given
        Long channelId = 999999999999999999L;

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            channelMngService.removeById(channelId);
        } catch (RsException e){
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if(ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("채널 삭제 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }
}