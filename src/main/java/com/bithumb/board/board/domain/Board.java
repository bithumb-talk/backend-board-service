package com.bithumb.board.board.domain;

import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="board")
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    @Column(name="board_no")
    private long boardNo;

    @Column(name = "user_nickname")
    private String nickname;

    @NotNull
    @Column(name="board_title")
    private String boardTitle;

    @Column(name="board_views")
    private long boardViews;

    @Column(name="board_category")
    private String boardCategory;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String boardContent;

    @Column(name="board_created_date")
    private LocalDateTime boardCreatedDate;

    @Column(name="board_modify_date")
    private LocalDateTime boardModifyDate;

    @Column(name="board_recommend")
    private long boardRecommend;

    //String parsing
    @Column(name= "board_img")
    private String boardImg;

    @Builder
    public Board(String nickname,String boardTitle,Long boardViews, String boardCategory,String boardContent,
                 LocalDateTime boardCreatedDate,Long boardRecommend, String boardImg ){
        this.nickname = nickname;
        this.boardTitle =boardTitle;
        this.boardCategory = boardCategory;
        this.boardViews = boardViews;
        this.boardContent = boardContent;
        this.boardCreatedDate = boardCreatedDate;
        this.boardRecommend = boardRecommend;
        this.boardImg = boardImg;
    }

    public void updateBoardContent(String nickname, String boardTitle, String boardContent, String boardImg, LocalDateTime boardModifyDate ){
        this.nickname = nickname;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardImg = boardImg;
        this.boardModifyDate = boardModifyDate;
    }


    public void changeBoardViews(long boardViews) {
        this.boardViews = boardViews;
    }
    public void changeBoardRecommend(long boardRecommend){
        this.boardRecommend = boardRecommend;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_no")
    private User user;

    public void changeUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy="board", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;
}
