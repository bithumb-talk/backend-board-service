package com.bithumb.board.reply.domain;

import com.bithumb.board.board.domain.Board;
import com.bithumb.board.comment.domain.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="reply")
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue
    @Column(name="reply_no")
    private Long replyNo;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name="reply_content")
    private String replyContent;

    @Column(name="reply_created_date")
    private LocalDateTime replyCreatedDate;

    @Column(name="reply_modify_date")
    private LocalDateTime replyModifyDate;


    @Builder
    public Reply(String nickname, String replyContent, LocalDateTime replyCreatedDate, LocalDateTime replyModifyDate){
        this.nickname = nickname;
        this.replyContent = replyContent;
        this.replyCreatedDate = replyCreatedDate;
        this.replyModifyDate = replyModifyDate;
    }
    public void changeReply(String nickname, String replyContent,  LocalDateTime replyModifyDate){
        this.nickname = nickname;
        this.replyContent = replyContent;
        this.replyModifyDate = replyModifyDate;
    }
    public void changeComment(Comment comment) { this.comment = comment; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_no")
    @JsonIgnore
    private Comment comment;
}
