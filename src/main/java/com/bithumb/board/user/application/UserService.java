package com.bithumb.board.user.application;

import com.bithumb.board.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User findUser(long user_no);
}
