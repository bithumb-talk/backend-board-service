package com.bithumb.board.board.api;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest //  테ㅡㅅ트하겟다
@Transactional // db적용한값을 ㅓ킴ㅅ하지않게ㅅ다
class BoardControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {

    }

    @Test
    void retrieveBoard() throws Exception {
        //DTO


        //String json = objectMapper.writeValueAsString(modifyNicknameRequest);

        mockMvc.perform(get("/boards/1"))//URL
                        //.header("Authorization",accessToken) //HEADER W\지움
                        //.contentType(MediaType.APPLICATION_JSON)
                        //.content(json))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(jsonPath("message").value(SuccessCode.USER_UPDATE_PASSWORD_SUCESS.getMessage()));
    }

    @Test
    void countingBoardRecommend() {
    }

    @Test
    void createBoard() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void deleteBoard() {
    }
}