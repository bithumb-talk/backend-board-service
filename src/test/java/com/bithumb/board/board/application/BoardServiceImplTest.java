package com.bithumb.board.board.application;

import com.bithumb.board.board.api.dto.ResponseBoardDto;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.repository.BoardRepository;
import com.bithumb.board.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    //@InjectMocks
    BoardServiceImpl boardServiceImpl;
    @Mock //requ constructor(argu)
    BoardRepository boardRepository;
    @Mock
    UserRepository userRepository;

    //injectmock 안할때
    @BeforeEach
    void setUp(  ) {
        this.boardServiceImpl = new BoardServiceImpl(boardRepository, userRepository);
    }

    @Test
    @DisplayName("성공테스트 - 보드 조회")
    void retrieveBoard() {
        //given : 외부의존성
        // willreturn 안에 dto
        Board board = Board.builder()
                .boardContent("게시글 내용")
                .boardImg("sss")
                .boardTitle("title")
                .boardViews(0L)
                .boardRecommend(0L)
                .nickname("nickname")
                .boardCategory("category")
                .boardRecommend(0L)
                .build();
        given(boardRepository.findById(any())).willReturn(java.util.Optional.ofNullable(board)); //어떤값을 집어넣던 이 값을 반환시키겠다
        //서비스 메서드를 돌려서나오는 값이랑 id정도는 일치

        given(boardRepository.save(any())).willReturn(any());
        // 반환없으면 dto 없어도되는데 any해준다

        //when 얘 찐값 넣고 찐으로 반환 확인
        ResponseBoardDto responseBoardDto = boardServiceImpl.retrieveBoard(1L);

        // responsem
        ResponseBoardDto responseBoardDto1 = ResponseBoardDto.of(board);

        //then  비교하는애
        assertThat(responseBoardDto, is(responseBoardDto1)); //dto,기댓값(dto)
    }

    @Test
    @DisplayName("실패테스트 - 보드 조회")
    void retrieveBoardFailure(){

    }

}