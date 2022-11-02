package com.study.project.dto;


import com.study.project.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberNickName;
    private String memberMobile;
    private String memberGender;
    private int memberAge;
    private String memberLocation;
    private LocalDateTime join_date;

    public MemberDTO(String memberEmail, String memberPassword, String memberName,String memberNickName,
                     String memberMobile, String memberGender, int memberAge, String memberLocation) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberNickName = memberNickName;
        this.memberMobile = memberMobile;
        this.memberGender = memberGender;
        this.memberAge = memberAge;
        this.memberLocation = memberLocation;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberNickName(memberEntity.getMemberNickName());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberAge(memberEntity.getMemberAge());
        memberDTO.setMemberLocation(memberEntity.getMemberLocation());
        return memberDTO;
    }

}
