package com.kitchensink;

import com.kitchensink.dto.MemberDTO;
import com.kitchensink.mappers.MemberMapper;
import com.kitchensink.repository.MemberRepository;
import com.kitchensink.service.MemberService;
import com.kitchensink.util.FileLoadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper memberMapper;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private MemberDTO customerDTO;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void listMembers_TestOk() throws Exception {

        MemberDTO customerDTO = MemberDTO.builder()
                .name("Ralu")
                .email("raluca@gmail.com")
                .phoneNumber("1234678912")
                .build();

        when(memberService.saveNewCustomer(customerDTO))
                .thenReturn(customerDTO);

        MvcResult result = mockMvc.perform(
                        get("/api/v1/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ArrayList<MemberDTO> listCreatedMembers =
                objectMapper.readValue(result.getResponse().getContentAsString(), ArrayList.class);

        // Assertions
        assertEquals(1, listCreatedMembers.size());

    }

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
}
