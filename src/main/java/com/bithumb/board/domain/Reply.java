package com.bithumb.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="reply")
public class Reply {
    @Id
    @GeneratedValue
    @Column(name="reply_no")
    private Long replyNo;

    @NotNull
    @Column(name="reply_content")
    private String replyContent;

    @Column(name="reply_created_date")
    private LocalDateTime replyCreatedDate;

    @Column(name="reply_modify_date")
    private LocalDateTime replyModifyDate;

    @Column(name="user_id")
    private String userId;

    @PrePersist     //insert 연산할때 같이실행
    public void prePersist(){
        this.replyCreatedDate = LocalDateTime.now().withNano(0);
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_no")
    @JsonIgnore
    private Comment comment;

}
