package com.example.revenueshare.biz.mng.base.presentation;

import com.example.revenueshare.biz.mng.base.model.ChannelDTO;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.model.ResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(ChannelMngController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
class ChannelMngControllerTest {

    @Autowired
    private MockMvc mvc;

    private ChannelDTO CREATE_DTO;

    private final ObjectMapper mapper = new ObjectMapper();

//    @MockBean
//    private ChannelMngService channelMngService;


    @Test
    @DisplayName("[성공 케이스]: 채널 등록")
    @Order(1)
    void addSuccess() throws Exception {
        //given
        CREATE_DTO = ChannelDTO.builder()
                .channelNm("시온채널TV_" + (new Date().getTime() / 1000))
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();

        //when
        ResultActions resultActions = mvc.perform(post("/mng/base/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CREATE_DTO)))
                .andDo(print());


        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();
        ResponseVO<Long> responseVO = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseVO.class);
        if (!ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail(String.format("[%s] %s", responseVO.getErrCd(), responseVO.getErrMsg()));
        if (responseVO.getResultInfo() instanceof Long)
            CREATE_DTO.setChannelId(responseVO.getResultInfo());
        else
            CREATE_DTO.setChannelId(Long.parseLong(String.valueOf(responseVO.getResultInfo())));

    }

    @Test
    @DisplayName("[실패 케이스]: 채널 등록 - 유효성 검증 실패")
    @Order(1)
    void addFailByValidate() throws Exception {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelNm(null)
                .openDe(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();

        //when
        ResultActions resultActions = mvc.perform(post("/mng/base/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(channelDTO)))
                .andDo(print());


        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isBadRequest())
                .andReturn();
        ResponseVO<Long> responseVO = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseVO.class);
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail(String.format("[%s] %s", responseVO.getErrCd(), responseVO.getErrMsg()));
    }

    @Test
    @DisplayName("[성공 케이스]: 채널 수정")
    @Order(2)
    void modifySuccess() throws Exception {
        //given
        CREATE_DTO.setChannelNm("채널명수정_" + (new Date().getTime() / 1000));

        
        //when
        ResultActions resultActions = mvc.perform(put("/mng/base/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CREATE_DTO)))
                .andDo(print());


        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();
        ResponseVO<Long> responseVO = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseVO.class);
        if (!ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail(String.format("[%s] %s", responseVO.getErrCd(), responseVO.getErrMsg()));
    }

    @Test
    @DisplayName("[실패 케이스]: 채널 수정 - 파라미터 유효성 검증 실패")
    void modifyFailByValidate() throws Exception {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder().build();


        //when
        ResultActions resultActions = mvc.perform(put("/mng/base/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(channelDTO)))
                .andDo(print());


        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isBadRequest())
                .andReturn();
        ResponseVO<Long> responseVO = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseVO.class);
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail(String.format("[%s] %s", responseVO.getErrCd(), responseVO.getErrMsg()));
    }

    @Test
    @DisplayName("[실패 케이스]: 채널 수정 - 채널명 중복")
    void modifyFailByDuplicate() throws Exception {
        //given
        ChannelDTO channelDTO = CREATE_DTO;


        //when
        ResultActions resultActions = mvc.perform(put("/mng/base/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(channelDTO)))
                .andDo(print());


        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isBadRequest())
                .andReturn();
        ResponseVO<Long> responseVO = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseVO.class);
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail(String.format("[%s] %s", responseVO.getErrCd(), responseVO.getErrMsg()));
    }


    @Test
    @DisplayName("[성공 케이스]: 채널 삭제")
    @Order(3)
    void removeSuccess() throws Exception {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelId(CREATE_DTO.getChannelId())
                .build();


        //when
        ResultActions resultActions = mvc.perform(delete("/mng/base/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CREATE_DTO)))
                .andDo(print());


        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();
        ResponseVO<Long> responseVO = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseVO.class);
        if (!ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail(String.format("[%s] %s", responseVO.getErrCd(), responseVO.getErrMsg()));
    }
    @Test
    @DisplayName("[성공 케이스]: 채널 삭제 - 등록되지 않은 채널 삭제")
    void removeFailByNoData() throws Exception {
        //given
        ChannelDTO channelDTO = ChannelDTO.builder()
                .channelId(999999999999999999L)
                .build();


        //when
        ResultActions resultActions = mvc.perform(delete("/mng/base/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(channelDTO)))
                .andDo(print());


        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isBadRequest())
                .andReturn();
        ResponseVO<Long> responseVO = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseVO.class);
        if (ErrCd.OK.equals(responseVO.getErrCd()))
            Assertions.fail(String.format("[%s] %s", responseVO.getErrCd(), responseVO.getErrMsg()));
    }

}