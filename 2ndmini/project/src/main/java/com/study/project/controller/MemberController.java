package com.study.project.controller;

import com.study.project.dto.MemberDTO;
import com.study.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private  final MemberService memberService;

    //회원가입 페이지
    @GetMapping("/join-form")
    public String saveForm(){
        return "memberPages/join";
    }

    //회원가입 처리
    @PostMapping("/join")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "memberPages/login";
    }

    //로그인 페이지
    @GetMapping("/login-form")
    public String loginForm(){
        return "memberPages/login";
    }

    //로그인 결과 페이지
    @GetMapping("/login/result")
    public String LoginResult(){
        return "/loginSuccess";
    }

    //로그아웃 결과 페이지
    @GetMapping("/logout/result")
    public String Logout(){
        return "/logout";
    }

    //접근 거부 페이지
    @GetMapping("/denied")
    public String Denied(){
        return "/denied";
    }

    //내 정보 페이지
    @GetMapping("/info")
    public String MyInfo(){
        return "/myinfo";
    }

    //어드민 페이지
    @GetMapping("/admin")
    public String Admin(){
        return "/admin";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult !=null) {
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("id", loginResult.getId());
            return "memberPages/main";
        } else {
            return "memberPages/login";
        }
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/list";
    }
}
