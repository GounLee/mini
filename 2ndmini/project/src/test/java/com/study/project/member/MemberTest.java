package com.study.project.member;

import com.study.project.dto.MemberDTO;
import com.study.project.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;
    public MemberDTO newMember(int i){
        MemberDTO member =
                new MemberDTO("테스트용이메일"+i, "테스트용비밀번호"+i,"테스트용이름"+i,99+i,"테스트용전화번호"+i);
        return member;
    }
//  위는 회원데이터 자동 생성하기 위한 폼, 아래는 기본형식
//    public MemberDTO newMember(){
//        MemberDTO member = new MemberDTO("테스트용이메일", "테스트용비밀번호","테스트용이름",99,"테스트용전화번호");
//        return member;
//    }



    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        // MemberDTO member = new MemberDTO("테스트용이메일", "테스트용비번","테스트용이름",99,"테스트용전화번호");
        // Long savedId = memberService.save(member());
        // 위에 두 줄 합쳐서 아래 한 줄로 표현함.
        Long savedId = memberService.save(newMember(1));
        MemberDTO memberDTO = memberService.findById(savedId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void loginTest(){
        final String memberEmail = "로그인용이메일";
        final String memberPassword = "로그인용비번";
        String memberName = "로그인용이름";
        int memberAge = 99;
        String memberMobile = "로그인용전화번호";
        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword, memberName, memberAge, memberMobile);
        Long savedId = memberService.save(memberDTO);
        //로그인 객체 생성 후 로그인
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberEmail(memberEmail);
        loginMemberDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult = memberService.login(loginMemberDTO);
        //로그인 결과가 not null이면 테스트 통과
        assertThat(loginResult).isNotNull();
    }

    //회원 데이터 자동생성
    @Test
    @DisplayName("회원 데이터 저장")
    public void memberSave(){
        IntStream.rangeClosed(1,20).forEach(i -> {
            memberService.save(newMember(i));
        });
    }


    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 삭제 테스트")
    public void memberDeleteTest(){
        /**
         * 신규 회원 등록
         * 삭제 처리
         * 해당 회원으로 조회시 null이면 통과
         */
        Long savedId = memberService.save(newMember(999));
        memberService.delete(savedId);
        assertThat(memberService.findById(savedId)).isNull();
    }
}
