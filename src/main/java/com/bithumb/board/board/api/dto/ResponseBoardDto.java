package com.bithumb.board.board.api.dto;


import com.bithumb.board.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//게시글 조회
@NoArgsConstructor
@Getter
public class ResponseBoardDto {
    private long boardNo;
    private String boardTitle;
    private long boardViews;
    private String boardCategory;
    private String boardContent;
    private String nickname;
    private LocalDateTime boardCreatedDate;
    private LocalDateTime boardModifyDate;
    private long boardRecommend;
    private List<String> boardImg;

    public List<String> StringToList(String boardImg) {
        String[] tempString = boardImg.split("\n");
        List<String> imageList = new ArrayList<>();
        for (String url : tempString) {
            imageList.add(url);
        }
        return imageList;
    }



    public ResponseBoardDto(long boardNo, String boardTitle, long boardViews, String boardCategory,
                            String boardContent, String nickname, LocalDateTime boardCreatedDate,
                            LocalDateTime boardModifyDate, long boardRecommend, String boardImg) {
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
        this.boardViews = boardViews;
        this.boardCategory = boardCategory;
        this.boardContent = boardContent;
        this.nickname = nickname;
        this.boardCreatedDate = boardCreatedDate;
        this.boardModifyDate = boardModifyDate;
        this.boardRecommend = boardRecommend;
        if(boardImg.length() > 0){
            this.boardImg = StringToList(boardImg);
        }else{
            this.boardImg = null;
        }
    }

    public static ResponseBoardDto of(Board board) {
        return new ResponseBoardDto(board.getBoardNo(), board.getBoardTitle(), board.getBoardViews(), board.getBoardCategory(), board.getBoardContent(), board.getNickname(),
                board.getBoardCreatedDate(), board.getBoardModifyDate(), board.getBoardRecommend(),board.getBoardImg());
    }
//    public static ResponseBoardDto ValueOf(Board board){
//        return new ResponseBoardDto(board.getBoardNo(),board.getBoardTitle(), board.getBoardViews(), board.getBoardCategory(), board.getBoardContent(), board.getNickname(), board.getBoardCreatedDate(), board.getBoardModifyDate(), board.getBoardRecommend());
//    }
}
