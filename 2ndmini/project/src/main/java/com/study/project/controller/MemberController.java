package com.study.project.controller;

import com.study.project.dto.MemberDTO;
import com.study.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private  final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "memberPages/save";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "memberPages/login";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "memberPages/login";

    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("id", loginResult.getId());
            return "redirect:/";
            //return "memberPages/mypage";
           // return "index";
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

    // /member/3이런 형식으로 날라옴. list.html의 |주소|형식
    // /member?id=3
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        // memberservice에 있는 형식 가져다 쓴다.
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "memberPages/detail";

    }

    // ajax 상세조회
    @PostMapping("/ajax/{id}")
    public @ResponseBody MemberDTO findByIdAjax(@PathVariable Long id){
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }



    // get요청 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        memberService.delete(id);
        return "redirect:/member/";
    }

    /** 주소는 같지만 방식에 따라서 다르게 작동하도록 약속
     * /member/3:조회(get)R, 저장(post)C, 수정(put)U, 삭제(delete)D
     */

    // delete 요청 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAjax(@PathVariable Long id){
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK); //ajax 호출한 부분에 리턴으로 status code 200 응답을 줌.
    }



    // 수정화면 요청
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        Long id = (Long) session.getAttribute("id");
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("updateMember", memberDTO);
        return "memberPages/update";
    }


    // 수정처리
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/"+memberDTO.getId();
    }



    // 수정처리(put요청)
    // 스프링에서 제공하는 컨버터가 알아서 분류해준다.
    @PutMapping("/{id}")
    public ResponseEntity updateByAjax(@RequestBody MemberDTO memberDTO){
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    // 이메일 중복체크
    @PostMapping("/emailCheck")
    public @ResponseBody String emailCheck(@RequestParam String memberEmail){
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }








}
