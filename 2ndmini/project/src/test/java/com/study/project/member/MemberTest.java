package com.study.project.member;

import com.study.project.dto.MemberDTO;
import com.study.project.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i){
        MemberDTO member =
                new MemberDTO("테스트용이메일"+i, "테스트용비밀번호"+i,"테스트용이름"+i,"테스트용닉네임"+i, "테스트용전화번호"+i,
                        "테스트용성별"+i, 99+i, "테스트용지역"+i);
        return member;
    }


    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        //MemberDTO member = new MemberDTO("테스트용이메일", "테스트용비번","테스트용이름",99,"테스트용전화번호");
        //Long savedId = memberService.save(member());
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
        String memberGender = "로그인용성별";
        String memberLocation = "로그인용지역";
        String memberNickName = "로그인용닉네임";
        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword, memberName, memberNickName,
                 memberMobile,  memberGender, memberAge, memberLocation);
        Long savedId = memberService.save(memberDTO);
        //로그인 객체 생성 후 로그인
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberEmail(memberEmail);
        loginMemberDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult = memberService.login(loginMemberDTO);
        //로그인 결과가 not null이면 테스트 통과
        assertThat(loginResult).isNotNull();
    }

    @Test
    @DisplayName("회원 데이터 저장")
    public void memberSave(){
        IntStream.rangeClosed(1,20).forEach(i -> {
            memberService.save(newMember(i));
        });
    }
}
