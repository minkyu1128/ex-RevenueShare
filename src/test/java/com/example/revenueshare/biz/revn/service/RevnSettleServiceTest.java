package com.example.revenueshare.biz.revn.service;

import com.example.revenueshare.biz.mng.base.code.RevnSeCd;
import com.example.revenueshare.biz.mng.base.model.ChannelDTO;
import com.example.revenueshare.biz.mng.base.model.CmpnyDTO;
import com.example.revenueshare.biz.mng.base.model.CreatorDTO;
import com.example.revenueshare.biz.mng.base.service.ChannelMngService;
import com.example.revenueshare.biz.mng.base.service.CmpnyMngService;
import com.example.revenueshare.biz.mng.base.service.CreatorMngService;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnDTO;
import com.example.revenueshare.biz.mng.chrevn.service.ChannelRevnMngService;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCmpnyMngService;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCreatorMngService;
import com.example.revenueshare.biz.revn.model.RevnSettleDTO;
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
class RevnSettleServiceTest {


    @Autowired
    private RevnSettleService revnSettleService;
    @Autowired
    private CmpnyMngService cmpnyMngService;
    @Autowired
    private ChannelMngService channelMngService;
    @Autowired
    private CreatorMngService creatorMngService;
    @Autowired
    private ContractCmpnyMngService contractCmpnyMngService;
    @Autowired
    private ContractCreatorMngService contractCreatorMngService;
    @Autowired
    private ChannelRevnMngService channelRevnMngService;

    private RevnSettleDTO CREATE_DTO;

    @Test
    @DisplayName("[성공 케이스]: 수익정산 등록")
    @Order(1)
    void addSuccess() {
        /* =========================================
         * given
        ========================================= */
        //회사 등록
        CmpnyDTO cmpnyDTO = CmpnyDTO.builder()
                .cmpnyNm("수익정산 컴퍼니_" + (new Date().getTime() / 1000))
                .build();
        final Long cmpnyId = (Long) cmpnyMngService.add(cmpnyDTO).getResultInfo();
        //채널 등록
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelNm("수익정산Tube_" + (new Date().getTime() / 1000))
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();
        final Long channelId = (Long) channelMngService.add(channelDTO).getResultInfo();
        //크리에이터 등록
        CreatorDTO creatorDTO = CreatorDTO.builder()
                .creatorNm("수정이_" + (new Date().getTime() / 1000))
                .build();
        final Long creatorId = (Long) creatorMngService.add(creatorDTO).getResultInfo();
        //회사 계약정보 등록
        ContractCmpnyDTO contractCmpnyDTO = ContractCmpnyDTO.builder()
                .cmpnyId(cmpnyId)
                .channelId(channelId)
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(40)
                .build();
        contractCmpnyMngService.add(contractCmpnyDTO);
        //크리에이터 계약정보 등록
        ContractCreatorDTO contractCreatorDTO = ContractCreatorDTO.builder()
                .creatorId(creatorId)
                .channelId(channelId)
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(99)
                .build();
        contractCreatorMngService.add(contractCreatorDTO);
        //채널 수익 등록
        ChannelRevnDTO channelRevnDTO = ChannelRevnDTO.builder()
                .channelId(channelId)
                .revnDe(LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM")) + RandomUtils.randomDay().substring(0, 2))
                .revnSeCd(RevnSeCd.HITS.getCode())
                .revnAmt(RandomUtils.randomMoney())
                .build();
        channelRevnMngService.add(channelRevnDTO);
        CREATE_DTO = RevnSettleDTO.builder()
                .channelId(channelId)
                .calYm(channelRevnDTO.getRevnDe().substring(0, 6))
                .build();

        
        /* =========================================
         * when
        ========================================= */
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = revnSettleService.add(CREATE_DTO);
        } catch (RsException e) {
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        
        /* =========================================
         * then
        ========================================= */
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("수익정산 등록 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }

    @Test
    @DisplayName("[실패 케이스]: 수익정산 중복 등록")
    void addFailByDuplicate() {
        //given
        RevnSettleDTO revnSettleDTO = CREATE_DTO;

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = revnSettleService.add(revnSettleDTO);
        } catch (RsException e) {
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("중복 수익정산 등록실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[실패 케이스]: 수익정산 등록 시 파라미터 유효성 검증")
    void addFailByValidate() {
        //given
        RevnSettleDTO revnSettleDTO = RevnSettleDTO.builder().build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = revnSettleService.add(revnSettleDTO);
        } catch (RsException e) {
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("수익정산 등록 시 파라미터 유효성 검증실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[실패 케이스]: 현재월 또는 도래하지 않은 날짜의 수익정산 등록")
    void addFailByAfterYm() {
        for(int i=0; i<2; i++){
            //given
            RevnSettleDTO revnSettleDTO = RevnSettleDTO.builder()
                    .channelId(CREATE_DTO.getChannelId())
                    .calYm(LocalDateTime.now().plusMonths(i).format(DateTimeFormatter.ofPattern("yyyyMM")))
                    .build();

            //when
            ResponseVO<Long> responseVO = null;
            try {
                responseVO = revnSettleService.add(revnSettleDTO);
            } catch (RsException e) {
                responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
            }

            //then
            if (ErrCd.OK.equals(responseVO.getErrCd()))
                Assertions.fail("현재월 또는 도래하지 않은 날짜의 수익정산 등록 케이스 테스트 실패");
            else
                Assertions.assertEquals(true, true);
        }
    }

}