package com.study.project.repository;


import com.study.project.entity.Board;
import com.study.project.entity.BoardEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    //Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);

    //jpql(java persistence query language)
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits + 1 where b.id=:id")
    void boardHits(@Param("id") Long id);
}
