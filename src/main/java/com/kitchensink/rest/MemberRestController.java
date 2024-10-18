package com.kitchensink.rest;

import com.kitchensink.dto.MemberDTO;
import com.kitchensink.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MemberRestController {

    @Autowired
    MemberService memberService;

    @PostMapping(value = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> addCustomer(@Valid @RequestBody MemberDTO customerDto) {
        MemberDTO memberDTO = memberService.saveNewCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberDTO>>listCustomers() {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }
}
