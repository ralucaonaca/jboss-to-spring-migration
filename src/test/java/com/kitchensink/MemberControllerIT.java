package com.kitchensink;

import com.kitchensink.dto.MemberDTO;
import com.kitchensink.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class MemberControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addCustomer_TestOk() throws Exception {

        MemberDTO customerDTO = MemberDTO.builder()
                .name("Ralu")
                .email("raluca@gmail.com")
                .phoneNumber("1234678912")
                .build();

        when(memberService.saveNewCustomer(customerDTO))
                .thenReturn(customerDTO);

        MvcResult result = mockMvc.perform(
                    post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MemberDTO createdMember = objectMapper.readValue(result.getResponse().getContentAsString(), MemberDTO.class);

        // Assertions
        assertEquals(customerDTO.getPhoneNumber(), createdMember.getPhoneNumber());
        assertEquals(customerDTO.getEmail(), createdMember.getEmail());
        assertEquals(customerDTO.getName(), createdMember.getName());

    }

    @Test
    void addMember_TestDuplicateEmail() throws Exception {

        MemberDTO customerDTO = MemberDTO.builder()
                .name("Ralu")
                .email("raluca@gmail.com")
                .phoneNumber("1234678912")
                .build();

        when(memberService.saveNewCustomer(customerDTO))
                .thenReturn(customerDTO);

        mockMvc.perform(
                        post("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerDTO))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        when(memberService.saveNewCustomer(customerDTO))
                .thenThrow(new DataIntegrityViolationException("Duplicated"));

        MvcResult result = mockMvc.perform(
                        post("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerDTO))
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    void addMember_TestEmptyValueName() throws Exception {

        MemberDTO customerDTO = MemberDTO.builder()
                .name("")
                .email("raluca@gmail.com")
                .phoneNumber("1234678912")
                .build();

        when(memberService.saveNewCustomer(customerDTO))
                .thenReturn(customerDTO);

        mockMvc.perform(
                        post("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerDTO))
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    void addMember_TestEmptyValueEmail() throws Exception {

        MemberDTO customerDTO = MemberDTO.builder()
                .name("Raluca")
                .email("")
                .phoneNumber("1234678912")
                .build();

        when(memberService.saveNewCustomer(customerDTO))
                .thenReturn(customerDTO);

        mockMvc.perform(
                        post("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerDTO))
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    void addMember_TestEmptyValuePhoneNumber() throws Exception {

        MemberDTO customerDTO = MemberDTO.builder()
                .name("Raluca")
                .email("raluca@gmail.com")
                .phoneNumber("")
                .build();

        when(memberService.saveNewCustomer(customerDTO))
                .thenReturn(customerDTO);

        mockMvc.perform(
                        post("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerDTO))
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();
    }
}
