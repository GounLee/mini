package com.study.project.service;


import com.study.project.dto.MemberDTO;
import com.study.project.entity.MemberEntity;
import com.study.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        //DTO를 바로 쓸 수 없어서 Entity로 저장하는 과정이 필요.(Entity에서 작업)
        //memberRepository.save(MemberEntity.toSaveEntity(memberDTO));
        //MemberEntity memberEntity = memberRepository.save(MemberEntity.toSaveEntity(memberDTO));
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        Long savedId = memberRepository.save(memberEntity).getId();
        return savedId;
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /**
         * login.html에서 이메일, 비번을 받아오고
         * DB로 부터 해당 이메일의 정보를 가져와서
         * 입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
         * 일치히면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (optionalMemberEntity.isPresent()){
            MemberEntity loginEntity =  optionalMemberEntity.get();
            if (loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                return memberDTO.toMemberDTO(loginEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }



    // Test를 위한 것?
    public MemberDTO findById(Long id){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            // return MemberDTO.toMemberDTO(optionalMemberEntity.get());
            // 위에 한줄로 축약가능
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        // Entity를 각각 가져와서 DTO로 변환해서 List로 반환
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity member: memberEntityList){
            //MemberDTO memberDTO = MemberDTO.toMemberDTO(member);
            //memberDTOList.add(memberDTO);
            //위에 두줄 줄여서 아래 한줄로
            memberDTOList.add(MemberDTO.toMemberDTO(member));
        }
        return memberDTOList;
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateEntity(memberDTO));
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()){
            return "ok";
        } else {
            return "no";
        }
    }
}
