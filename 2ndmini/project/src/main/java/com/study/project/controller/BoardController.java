package com.study.project.controller;


import com.study.project.dto.BoardDTO;
import com.study.project.entity.Board;
import com.study.project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
public class BoardController {

//    @Autowired
    private final BoardService boardService;

//    @GetMapping("/write")
//    public String boardWriteForm() {
//
//      return "boardPages/boardwrite";
//  }
//
//
//    @PostMapping("/writepro")
//    public String boardWritePro(Board board, Model model) {
//
//        boardService.write(board);
//
//        model.addAttribute("message", "글 작성이 완료되었습니다.");
//        model.addAttribute("searchUrl", "/board/list");
//
//        return "boardPages/message";
//    }
//
//    @GetMapping("/list")
//    public String boardList(Model model, @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC)
//            Pageable pageable, String searchKeyword) {
//
//        Page<Board> list = null;
//
//        if(searchKeyword == null) {
//            list = boardService.boardList(pageable);
//        } else {
//            list = boardService.boardSearchList(searchKeyword, pageable);
//        }
//
//        int nowPage = list.getPageable().getPageNumber() +1;
//        int startPage = Math.max(nowPage - 4, 1);
//        int endPage = Math.min(nowPage + 5, list.getTotalPages());
//
//        model.addAttribute("list", list);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//        return "boardPages/boardList";
//        }
//
//    @GetMapping("/view")
//    public String boardView(Model model, Integer id) {
//
//        model.addAttribute("board", boardService.boardView(id));
//        return "boardPages/boardview";
//    }
//
//    @GetMapping("/delete")
//    public String boardDelete(Integer id) {
//
//        boardService.boardDelete(id);
//
//        return "redirect:/board/list";
//        }
//
//    @GetMapping("/modify/{id}")
//    public String boardModify(@PathVariable("id") Integer id, Model model) {
//
//        model.addAttribute("board", boardService.boardView(id));
//
//        return "boardPages/boardmodify";
//    }
//
//    @PostMapping("/update/{id}")
//    public String boardUpdate(@PathVariable("id") Integer id, Board board) {
//
//        Board boardTemp = boardService.boardView(id);
//        boardTemp.setTitle(board.getTitle());
//        boardTemp.setContent(board.getContent());
//
//        boardService.write(boardTemp);
//
//        return "redirect:/board/list";
//    }
//






    // 코딩레시피
    @GetMapping("/save-form")
    public String saveForm() {
        return "boardPages/save";
    }



    @PostMapping("/save")
    public  String save(@ModelAttribute BoardDTO boardDTO){
        Long id = boardService.save(boardDTO);
        return "redirect:/board/"+id;
    }


    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardPages/findAll";

    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/detail";
    }


    // 수정화면 요청
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "boardPages/update";
    }


    // 수정처리
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO){
        boardService.update(boardDTO);
        return "redirect:/board/" + boardDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }
}


