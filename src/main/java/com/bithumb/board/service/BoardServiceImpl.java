package com.bithumb.board.service;


import com.bithumb.board.domain.Board;
import com.bithumb.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private final BoardRepository boardRepository;

//    @Override
//    public List<Board> findAll() {
//        return boardRepository.findAll();
//    }
    @Override
    public Page<Board> findAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }
    @Override
    public Optional<Board> findById(long board_no) {
        return boardRepository.findById(board_no);
    }

    @Override
    public Board getById(long board_no){ return boardRepository.getById(board_no); }
    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public boolean existsById(long id) {
        return boardRepository.existsById(id);
    }

    @Override
    public void deleteById(long board_no){
        boardRepository.deleteById(board_no);
    }

    @Override
    public Page<Board> findBoardByBoardCategory(String boardCategory, Pageable pageable){
        return boardRepository.findBoardByBoardCategory(boardCategory, pageable);
    }
//
//    @Override
//    public long count() {
//        return boardRepository.count();
//    }
//
//    @Override
//    public void deleteById(long id) {
//        boardRepository.deleteById(id);
//    }
//    @Override
//    public void deleteAll(){
//        boardRepository.deleteAll();
//    }
}
