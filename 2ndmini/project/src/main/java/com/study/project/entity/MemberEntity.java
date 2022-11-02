package com.study.project.entity;

import com.study.project.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(length = 20)
    private String memberNickName;

    @Column(length = 30)
    private String memberMobile;

    @Column(length = 20)
    private String memberGender;

    @Column
    private int memberAge;

    @Column(length = 20)
    private String memberLocation;

    @Column(length = 20)
    private LocalDateTime join_date;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberNickName(memberDTO.getMemberNickName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberGender(memberDTO.getMemberGender());
        memberEntity.setMemberAge(memberDTO.getMemberAge());
        memberEntity.setMemberLocation(memberDTO.getMemberLocation());
        return memberEntity;
    }

    @PrePersist
    public void Join_date() {
        this.join_date = LocalDateTime.now();
    }
}
