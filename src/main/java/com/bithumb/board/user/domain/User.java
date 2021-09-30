package com.bithumb.board.user.domain;

import com.bithumb.board.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_authority")
    private Authority authority;

    @Column(name = "user_profile_url")
    private String profileUrl;

    @OneToMany(mappedBy="user")
    private List<Board> boards;
}
