package com.example.revenueshare.biz.mng.cntrt.service;

import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Creator;
import com.example.revenueshare.biz.mng.base.domain.repository.ChannelRepository;
import com.example.revenueshare.biz.mng.base.domain.repository.CreatorRepository;
import com.example.revenueshare.biz.mng.base.model.ChannelSearchDTO;
import com.example.revenueshare.biz.mng.base.model.CreatorSearchDTO;
import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCreatorRepository;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
class ContractCreatorMngServiceTest {

    @Autowired
    private ContractCreatorMngService contractCreatorMngService;

    @Autowired
    private CreatorRepository creatorRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ContractCreatorRepository contractCreatorRepository;


    private ContractCreatorDTO CREATE_DTO;

    @Test
    @DisplayName("[성공 케이스]: 크리에이터 계약정보 등록")
    @Order(1)
    void addSuccess() {
        //given
        List<Creator> creators = creatorRepository.findFetchAllByDto(CreatorSearchDTO.builder().build());
        List<Channel> channels = channelRepository.findFetchAllByDto(ChannelSearchDTO.builder().build());
        ContractCreator contractCreator = null;
        Long creatorId = null;
        Long channelId = null;
        LOOP_BREAK:
        for (Creator creator : creators) {
            for (Channel channel : channels) {
                contractCreator = contractCreatorRepository.findByCreatorAndChannel(creator, channel).orElse(null);
                if (contractCreator == null){
                    creatorId = creator.getCreatorId();
                    channelId = channel.getChannelId();
                    break LOOP_BREAK;
                }

            }
            if(contractCreator != null)
                break;
        }
        this.CREATE_DTO = ContractCreatorDTO.builder()
                .creatorId(creatorId)
                .channelId(channelId)
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(77)
                .build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = contractCreatorMngService.add(this.CREATE_DTO);
            this.CREATE_DTO.setCntrCrtId(responseVO.getResultInfo());
        } catch (RsException e) {
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }


        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("크리에이터 계약정보 등록 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }

    @Test
    @DisplayName("[실패 케이스]: 크리에이터 계약정보 중복 등록")
    void addFailByDuplicate() {
        //given
        ContractCreatorDTO contractCreatorDTO = this.CREATE_DTO;

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = contractCreatorMngService.add(contractCreatorDTO);
        } catch (RsException e) {
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("중복 크리에이터 계약정보 등록실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[실패 케이스]: 크리에이터 계약정보 등록 시 유효성 검증")
    void addFailByValidate() {
        //given
        ContractCreatorDTO contractCreatorDTO = ContractCreatorDTO.builder().build();

        //when
        ResponseVO<Long> responseVO = null;
        try {
            responseVO = contractCreatorMngService.add(contractCreatorDTO);
        } catch (RsException e) {
            responseVO = ResponseVO.<Long>errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build();
        }

        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("크리에이터 계약정보 등록 유효성 검증실패 케이스 테스트 실패");
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[성공 케이스]: 크리에이터 계약정보 수정")
    @Order(2)
    void modifySuccess() {
        //given
        ContractCreatorDTO contractCreatorDTO = ContractCreatorDTO.builder()
                .cntrCrtId(CREATE_DTO.getCntrCrtId())
                .creatorId(CREATE_DTO.getCreatorId())
                .channelId(CREATE_DTO.getChannelId())
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(44)
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            contractCreatorMngService.modify(contractCreatorDTO);
        } catch (RsException e) {
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.assertEquals(true, true);
        else
            Assertions.fail("크리에이터 계약정보 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
    }

    @Test
    @DisplayName("[실패 케이스]: 등록되지 않은 크리에이터 계약정보 수정")
    void modifyFailByNoDate() {
        //given
        ContractCreatorDTO contractCreatorDTO = ContractCreatorDTO.builder()
                .cntrCrtId(999999999999999999L)
                .creatorId(CREATE_DTO.getCreatorId())
                .channelId(CREATE_DTO.getChannelId())
                .cntrDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .rsRate(44)
                .build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            contractCreatorMngService.modify(contractCreatorDTO);
        } catch (RsException e) {
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("크리에이터 계약정보 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }

    @Test
    @DisplayName("[실패 케이스]: 크리에이터 계약정보 수정 시 유효성 검증")
    void modifyFailByValidate() {
        //given
        ContractCreatorDTO contractCreatorDTO = ContractCreatorDTO.builder().build();

        //when
        ResponseVO responseVO = ResponseVO.okBuilder().build();
        try {
            contractCreatorMngService.modify(contractCreatorDTO);
        } catch (RsException e) {
            responseVO = ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build();
        }


        //then
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail("크리에이터 계약정보 수정 실패. [실패사유]: " + responseVO.getErrMsg() + " " + responseVO.getResultInfo());
        else
            Assertions.assertEquals(true, true);
    }

}