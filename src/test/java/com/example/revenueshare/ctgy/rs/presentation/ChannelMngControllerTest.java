//package com.example.revenueshare.ctgy.rs.presentation;
//
//import com.example.revenueshare.core.model.ResponseVO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles({"test"})
//@WebMvcTest(ChannelMngController.class)
//@SpringBootTest
//class ChannelMngControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @Test
//    void findAllBy() throws Exception {
//        //given
//        ResponseVO responseVO = ResponseVO.okBuilder().build();
//
//        //when
//        mvc.perform(get("/mng/channel")
//                        .param("schChannelNm", "채널명"))
//                //then
//                .andExpect(status().isOk())
//                .andExpect(content().json(mapper.writeValueAsString(responseVO)));
//    }
//
//    @Test
//    void findById() {
//    }
//
//    @Test
//    void add() {
//    }
//
//    @Test
//    void modify() {
//    }
//
//    @Test
//    void remove() {
//    }
//}