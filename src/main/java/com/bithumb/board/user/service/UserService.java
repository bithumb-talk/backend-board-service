package com.bithumb.board.user.service;

import com.bithumb.board.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User getById(long user_no);
}
