package com.study.project.repository;

import com.study.project.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 로그인을 하기 위한 조회방법
    // select * from member_table where memberEmail = ?
    // 리턴타입:MemberEntity
    // 매개변수:memberEmail(String)
    // 추상메소드?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

}
