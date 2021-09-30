package com.bithumb.board.board.application;


import com.bithumb.board.board.api.dto.CountDto;
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

    //    @Override
//    public List<Board> findAll() {
//        return boardRepository.findAll();
//    }
    @Override
    public Page<Board> findAll(Pageable pageable){
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

        //Board savedBoard = boardRepository.save(board);
        return ResponseBoardDto.of(boardRepository.save(board));
    }

    /* 게시글 추천 */
    @Override
    public CountDto updateRecommend(long boardNo){
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        long boardRecommend = board.getBoardRecommend();
        board.changeBoardRecommend(boardRecommend+1);
        return CountDto.from(boardRepository.save(board));
    }

    /* 게시글 수정 */
    @Override
    public ResponseBoardDto updateBoard(RequestBoardDto boardRequestDto, long boardNo, long userNo){
        User user = userRepository.findById(userNo).orElseThrow(()-> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
        Board board =  boardRepository.findBoardByBoardNoAndUser(boardNo,user).orElseThrow(() -> new NullPointerException(ErrorCode.BOARD_NOT_EXIST.getMessage()));
        board.updateBoardContent(
                boardRequestDto.getNickname(), boardRequestDto.getBoardTitle(),
                boardRequestDto.getBoardContent(),boardRequestDto.getBoardImg() == null ? board.getBoardImg() : boardRequestDto.setListToStringUrl(),LocalDateTime.now().withNano(0));
//        board.builder().nickname( boardRequestDto.getNickname() == null ? board.getNickname() : boardRequestDto.getNickname())
//                        .boardTitle(boardRequestDto.getBoardTitle() == null ? board.getBoardTitle() : boardRequestDto.getBoardTitle())
//                                .boardContent(boardRequestDto.getBoardContent() == null ? board.getBoardContent() : boardRequestDto.getBoardContent())
//                                        .boardImg(boardRequestDto.getBoardImg() == null ? board.getBoardImg(): boardRequestDto.setListToStringUrl())
//                                                .boardModifyDate(LocalDateTime.now().withNano(0))
//                                                        .build();
//        board.builder().nickname(boardRequestDto.getNickname())
//                  .boardTitle(boardRequestDto.getBoardTitle())
//                          .boardContent(boardRequestDto.getBoardContent())
//                                  .boardImg(boardRequestDto.setListToStringUrl() == null ? "" : boardRequestDto.setListToStringUrl())
//                                          .boardModifyDate(LocalDateTime.now().withNano(0))
//                                                  .build();
        Board savedBoard = boardRepository.save(board);
        return ResponseBoardDto.of(savedBoard);
    }

    @Override
    public Board getById(long board_no){ return boardRepository.getById(board_no); }


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
