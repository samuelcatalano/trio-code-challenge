package com.trio.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trio.challenge.configuration.ApplicationConfig;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
class ContactRESTControllerTest {

  private static final String URL = "http://localhost:8080/contacts/sync";
  private static final String WRONG_URL = "http://localhost:8080/contacts";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void contextLoads() {
    assertThat(mockMvc).isNotNull();
    assertThat(objectMapper).isNotNull();
  }

  @Test
  void shouldSyncContactsSuccess() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().isOk())
        .andExpect(content()
        .contentType((MediaType.APPLICATION_JSON)));
  }

  @Test
  void shouldSyncContactsNotFound() throws Exception {
    this.mockMvc.perform(get(WRONG_URL))
        .andExpect(status().isNotFound());
  }

  @Test
  void testCountContactsData() throws Exception {
    this.mockMvc.perform(get(URL))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.contacts", hasSize(24)));
  }
}
