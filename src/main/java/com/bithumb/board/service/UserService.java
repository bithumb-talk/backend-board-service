package com.bithumb.board.service;

import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public interface UserService {
    User getById(long user_no);
}
