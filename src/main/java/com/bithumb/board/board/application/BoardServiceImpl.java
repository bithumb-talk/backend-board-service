package com.bithumb.board.board.application;


import com.bithumb.board.board.api.dto.RequestCountDto;
import com.bithumb.board.board.api.dto.ResponseCountDto;
import com.bithumb.board.board.api.dto.RequestBoardDto;
import com.bithumb.board.board.api.dto.ResponseBoardDto;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.common.response.ErrorCode;
import com.bithumb.board.user.domain.User;
import com.bithumb.board.board.repository.BoardRepository;
import com.bithumb.board.user.repository.UserRepository;
import com.bithumb.board.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    //생성자 주입 => @RequiredArgsConstructor
//    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository, UserService userService) {
//        this.boardRepository = boardRepository;
//        this.userRepository = userRepository;
//        this.userService = userService;
//    }

    @Override
    public Page<Board> BoardsListAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    /* 게시글 조회 */
    @Override
    public ResponseBoardDto retrieveBoard(long boardNo){
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        long boardView = board.getBoardViews();
        board.changeBoardViews(boardView+1);
        boardRepository.save(board);
        return ResponseBoardDto.of(board);
    }

    /* 게시글 등록 */
    @Override
    public ResponseBoardDto createBoard(RequestBoardDto requestBoardDto, long userNo){
        requestBoardDto.setBoardCreateDate();
        Board board = requestBoardDto.toEntity();
        User user = userRepository.findById(userNo).orElseThrow(()-> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
        board.changeUser(user);
        return ResponseBoardDto.of(boardRepository.save(board));
    }

    /* 게시글 추천 */
    @Override
    public ResponseCountDto updateRecommend(long boardNo, RequestCountDto recommend){
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        long countingRecommend = board.getBoardRecommend();
        if(recommend.getBoardRecommend().equals("true")) {
            board.changeBoardRecommend(countingRecommend + 1);
        }
        else
            board.changeBoardRecommend(Math.max(countingRecommend-1,0));
        return ResponseCountDto.from(boardRepository.save(board));
    }

    /* 게시글 수정 */
    @Override
    public ResponseBoardDto updateBoard(RequestBoardDto boardRequestDto, long boardNo, long userNo){
        User user = userRepository.findById(userNo).orElseThrow(()-> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
        Board board =  boardRepository.findBoardByBoardNoAndUser(boardNo,user).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        board.updateBoardContent(
                boardRequestDto.getNickname(), board.getBoardCategory(),boardRequestDto.getBoardTitle(),
                boardRequestDto.getBoardContent(),boardRequestDto.getBoardImg() == null ? board.getBoardImg() : boardRequestDto.setListToStringUrl(),LocalDateTime.now().withNano(0));

        Board savedBoard = boardRepository.save(board);
        return ResponseBoardDto.of(savedBoard);
    }

    @Override
    public long deleteBoard(long boardNo, long userNo){
        User user = userRepository.findById(userNo).orElseThrow(()-> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
        return boardRepository.deleteBoardByBoardNoAndUser(boardNo, user);
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
    @Override
    public Page<Board> findBoardByUser(User user, Pageable pageable){
        return boardRepository.findBoardByUser(user,pageable);
    }

    @Override
    public List<Board> boardsRanking(){
        return boardRepository.findTop4ByOrderByBoardRecommendDesc();
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
