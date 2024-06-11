package com.iamjunhyeok.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.dto.CampaignApplyRequest;
import com.iamjunhyeok.review.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationService;

    @Test
    void 캠페인신청_유효함_신청성공() throws Exception {
        CampaignApplyRequest request = new CampaignApplyRequest();
        request.setName("전준혁");
        request.setPhoneNumber("01076782457");

        Application application = new Application();
        application.setId(1L);

        when(applicationService.apply(any(), any())).thenReturn(application);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/campaigns/{id}/apply", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/campaigns/applications/1"))
                .andDo(print());
    }

    @Test
    void 신청조회_유효함_조회성공() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setName("전준혁");
        application.setPhoneNumber("01076782457");

        when(applicationService.findById(any())).thenReturn(application);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/campaigns/applications/{id}", 1L);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(application.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(application.getPhoneNumber()))
                .andDo(print());
    }

    @Test
    void 신청취소_유효함_취소성공() throws Exception {
        Application application = new Application();
        application.setId(1L);
        application.setName("전준혁");
        application.setPhoneNumber("01076782457");

        when(applicationService.findById(any())).thenReturn(application);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/campaigns/applications/{id}/cancel", 1L);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }
}