package com.study.project.entity;

import com.study.project.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private  Long id;

    @Column(length = 50, unique = true)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    @Column(length = 20)
    private String memberName;

    @Column
    private int memberAge;

    @Column(length = 30)
    private String memberMobile;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberEntity.getMemberName());
        memberEntity.setMemberAge(memberEntity.getMemberAge());
        memberEntity.setMemberMobile(memberEntity.getMemberMobile());
        return memberEntity;
    }
}
