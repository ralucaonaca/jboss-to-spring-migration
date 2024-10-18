package com.kitchensink.controller;

import com.kitchensink.dto.MemberDTO;
import com.kitchensink.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allMemberList", memberService.getAllMembers());
        model.addAttribute("memberInfo", new MemberDTO());
        return "index";
    }

    @PostMapping("/addMember/")
    public String addNewMember(Model model, @Valid @ModelAttribute MemberDTO memberDTO,
                               BindingResult theBindingResult) {

        if (theBindingResult.hasErrors()) {
            model.addAttribute("allMemberList", memberService.getAllMembers());
            model.addAttribute("memberInfo", memberDTO);
            return "index";
        }
        memberService.saveNewCustomer(memberDTO);
        return "redirect:/";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
