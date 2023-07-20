package com.szuse.bkguidance.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Log4j2
@SpringBootTest
public class UserControllerTest {

    @Value("${server.port}")
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @PostConstruct
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private String buildRequestUrl(String url) {
        return "http://localhost:" + port + url;
    }

    @Test
    public void testListUser() throws Exception {
        String url = "/user/list";
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(buildRequestUrl(url))).andReturn();
        MockHttpServletResponse response = result.getResponse();
        JSONObject responseJson = JSON.parseObject(response.getContentAsString());
        log.info("request {} ,get response {}", url, responseJson);
    }
}
