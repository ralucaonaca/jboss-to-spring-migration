package com.kitchensink.service;

import com.kitchensink.dto.MemberDTO;
import com.kitchensink.mappers.MemberMapper;
import com.kitchensink.model.Member;
import com.kitchensink.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    private MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper){
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }
    public MemberDTO saveNewCustomer(MemberDTO customerDTO){
        Member member = memberRepository.save(memberMapper.toModel(customerDTO));
        return memberMapper.toDTO(member);
    }

    public List<MemberDTO> getAllMembers(){
        List<MemberDTO> customerDTOS = new ArrayList<>();
        List<Member> members = memberRepository.findAll();
        for (Member c : members){
            customerDTOS.add(memberMapper.toDTO(c));
        }
        return customerDTOS;
    }
}
