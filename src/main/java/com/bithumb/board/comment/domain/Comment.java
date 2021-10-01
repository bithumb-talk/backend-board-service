package com.bithumb.board.comment.domain;

import com.bithumb.board.board.domain.Board;
import com.bithumb.board.reply.domain.Reply;
import com.bithumb.board.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    @Column(name="comment_no")
    private long commentNo;

    @NotNull
    @Column(name="comment_content")
    private String commentContent;

    @Column(name="comment_created_date")
    private LocalDateTime commentCreatedDate;

    @Column(name="comment_modify_date")
    private LocalDateTime commentModifyDate;

    @Column(name="comment_recommend")
    private long commentRecommend;

    @Column(name = "user_nickname")
    private String nickname;

    @Builder
    public Comment(String commentContent, LocalDateTime commentCreatedDate, LocalDateTime commentModifyDate
        ,long commentRecommend, String nickname){
        this.commentContent = commentContent;
        this.commentCreatedDate = commentCreatedDate;
        this.commentModifyDate = commentModifyDate;
        this.commentRecommend = commentRecommend;
        this.nickname = nickname;
    }

    public void changeComment(String nickname, String commentContent, LocalDateTime commentModifyDate){
        this.nickname = nickname;
        this.commentContent = commentContent;
        this.commentModifyDate = commentModifyDate;
    }
    public void changeBoard(Board board) { this.board = board; }
    public void changeRecommend(){
        this.commentRecommend += 1;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_no")
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy="comment",cascade = CascadeType.REMOVE)

    private List<Reply> reply;

}
