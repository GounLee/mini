package com.study.project.service;

import com.study.project.dto.BoardDTO;
import com.study.project.entity.BaseEntity;
import com.study.project.entity.Board;
import com.study.project.entity.BoardEntity;
import com.study.project.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;

//    // 작성
//    public void write(Board board) {
//
//        boardRepository.save(board);
//    }
//
//    // 게시물 리스트
//    public Page<Board> boardList(Pageable pageable) {
//
//        return boardRepository.findAll(pageable);
//    }
//
//    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
//
//        return boardRepository.findByTitleContaining(searchKeyword, pageable);
//    }
//
//    // 게시물 조회
//    public Board boardView(Integer id) {
//
//        return boardRepository.findById(id).get();
//    }
//
//    // 게시글 삭제
//    public void boardDelete(Integer id) {
//
//        boardRepository.deleteById(id);
//    }





    // 코딩레시피

    public Long save(BoardDTO boardDTO){
        Long savedId = boardRepository.save(BoardEntity.toSaveEntity(boardDTO)).getId();
        return savedId;
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    public BoardDTO findById(Long id) {
        // 조회수 처리
        // native sql:update board_table set boardHits=boardRepository+1 where id=?
        boardRepository.boardHits(id);
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()){
            return  BoardDTO.toBoardDTO(optionalBoardEntity.get());
        } else {
            return null;
        }
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.toUpdateEntity(boardDTO));
    }

    public void delete(Long id){
        boardRepository.deleteById(id);
    }
}
