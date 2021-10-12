package com.bithumb.board.user.domain;

import com.bithumb.board.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "topic_user")//
public class User {
    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_authority", nullable = false)
    private Authority authority;

    @Column(name = "user_profile_url")
    private String profileUrl;

    @Column(name = "device_token")
    private String deviceToken;

    @OneToMany(mappedBy="user")
    private List<Board> boards;
}
